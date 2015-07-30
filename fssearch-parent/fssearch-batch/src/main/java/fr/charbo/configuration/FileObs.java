package fr.charbo.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.Attributes;

import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.isolateAggregation;
import org.springframework.util.CollectionUtils;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class FileObs {

	/**
	 * Returns an {@link Observable} that uses NIO WatchService (and a dedicated
	 * thread) to push modify events to an observable that reads and reports new
	 * lines to a subscriber. The NIO WatchService events are sampled according
	 * to <code>sampleTimeMs</code> so that lots of discrete activity on a file
	 * (for example a log file with very frequent entries) does not prompt an
	 * inordinate number of file reads to pick up changes.
	 *
	 * @param file
	 * @param startPosition
	 * @param sampleTimeMs
	 * @return
	 */
	public final static Observable<String> tailFile(File file,
			long startPosition, long sampleTimeMs) {
		return from(file, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.OVERFLOW)
		// don't care about the event details, just that there is one
				.cast(Object.class)
				
				// get lines once on subscription so we tail the lines in the
				// file at startup
//				.startWith(Event.FILE_EVENT)
				// emit a max of 1 event per sample period
				.sample(sampleTimeMs, TimeUnit.MILLISECONDS)
				// tail file triggered by events
				.lift(null);
	}

	/**
	 * Returns an {@link Observable} of {@link WatchEvent}s from a
	 * {@link WatchService}.
	 *
	 * @param watchService
	 * @return
	 */
	public final static Observable<WatchKey> from(WatchService watchService) {
		return Observable.create(new WatchServiceOnSubscribe(watchService));
	}

	/**
	 * If file does not exist at subscribe time then is assumed to not be a
	 * directory. If the file is not a directory (bearing in mind the aforesaid
	 * assumption) then a {@link WatchService} is set up on its parent and
	 * {@link WatchEvent}s of the given kinds are filtered to concern the file
	 * in question. If the file is a directory then a {@link WatchService} is
	 * set up on the directory and all events are passed through of the given
	 * kinds.
	 *
	 * @param file
	 * @param kinds
	 * @return
	 */
	private static WatchEvent<?> current = null;
	
	@SafeVarargs
	public final static Observable<WatchEvent<?>> from(final File file, Kind<?>... kinds) {
		
		return watchService(file, kinds).flatMap(TO_WATCH_EVENTS).flatMap(new Func1<WatchKey, Observable<WatchEvent<?>>>() {

			@Override
			public Observable<WatchEvent<?>> call(WatchKey key) {
				return Observable.create(new OnSubscribe<WatchEvent<?>>() {

					@Override
					public void call(Subscriber<? super WatchEvent<?>> subscriber) {
						for (WatchEvent<?> event : key.pollEvents()) {
							subscriber.onNext(event);
						}
						
					}
					
				});
			}
		}).flatMap(new Func1<WatchEvent, Observable<WatchEvent<?>>>() {

			@Override
			public Observable<WatchEvent<?>> call(WatchEvent event) {
				return Observable.create(new OnSubscribe<WatchEvent<?>>() {
					
					
					@Override
					public void call(Subscriber<? super WatchEvent<?>> subscriber) {
						//TODO add tempo
						String s1 = null;
						if (current != null) {
							s1 = current.kind().name() + " "  + current.context();
						}
						String s2 = event.kind().name() + " "  + event.context();
						if (current == null) {
							System.out.println("current is null");
							current = event;
							subscriber.onNext(event);
						} else if (!s2.equals(s1)) {
							System.out.println("not the same");
							current = event;
							subscriber.onNext(event);
						}						
					}
					
				});
			}
		});
	}

	/**
	 * Creates a {@link WatchService} on subscribe for the given file and event
	 * kinds.
	 *
	 * @param file
	 * @param kinds
	 * @return
	 */
	@SafeVarargs
	public final static Observable<WatchService> watchService(final File file,
			final Kind<?>... kinds) {
		return Observable.create(new OnSubscribe<WatchService>() {
			@Override
			public void call(Subscriber<? super WatchService> subscriber) {
				final Path path = getBasePath(file);
				try {
					WatchService watchService = path.getFileSystem()
							.newWatchService();
					path.register(watchService, kinds);
					subscriber.onNext(watchService);
					subscriber.onCompleted();
				} catch (IOException e) {
					subscriber.onError(e);
				}
			}
		});
	}

	private final static Path getBasePath(final File file) {
		final Path path;
		if (file.exists() && file.isDirectory())
			path = Paths.get(file.toURI());
		else
			path = Paths.get(file.getParentFile().toURI());
		return path;
	}

	

	static class WatchServiceOnSubscribe implements OnSubscribe<WatchKey> {
		private final WatchService watchService;
		private final AtomicBoolean subscribed = new AtomicBoolean(true);

		WatchServiceOnSubscribe(WatchService watchService) {
			this.watchService = watchService;
		}

		@Override
		public void call(Subscriber<? super WatchKey> subscriber) {
			if (!subscribed.get()) {
				subscriber
						.onError(new RuntimeException(
								"WatchService closed. You can only subscribe once to a WatchService."));
				return;
			}
			subscriber.add(createSubscriptionToCloseWatchService(watchService,
					subscribed, subscriber));
			emitEvents(subscriber);
		}

		private void emitEvents(Subscriber<? super WatchKey> subscriber) {
			// get the first event before looping
			WatchKey key = nextKey(subscriber);
			
			while (key != null) {
				subscriber.onNext(key);
				boolean valid = key.reset();
				if (!valid && subscribed.get()) {
					subscriber.onCompleted();
					return;
				} else if (!valid)
					return;
				key = nextKey(subscriber);
			}
//			subscriber.onNext(key);
		}

		private WatchKey nextKey(Subscriber<? super WatchKey> subscriber) {
			try {
				// this command blocks but unsubscribe close the watch
				// service and interrupt it
				return watchService.take();
			} catch (ClosedWatchServiceException e) {
				// must have unsubscribed
				if (subscribed.get())
					subscriber.onCompleted();
				return null;
			} catch (InterruptedException e) {
				// this case is problematic because unsubscribe may call
				// Thread.interrupt() before calling the unsubscribe method of
				// the Subscription. Thus at this point we don't know if a
				// deliberate interrupt was called in which case I would call
				// onComplete or if unsubscribe was called in which case I
				// should not call anything. For the moment I choose to not call
				// anything partly because a deliberate stop of the
				// watchService.take ignorant of the Observable should ideally
				// happen via a call to the WatchService.close() method rather
				// than Thread.interrupt().
				// TODO raise the issue with RxJava team in particular
				// Subscriptions.from(Future) calling FutureTask.cancel(true)
				try {
					watchService.close();
				} catch (IOException e1) {
					// do nothing
				}
				return null;
			}
		}
	}

	private final static Subscription createSubscriptionToCloseWatchService(final WatchService watchService, final AtomicBoolean subscribed,
			final Subscriber<? super WatchKey> subscriber) {
		return new Subscription() {
			@Override
			public void unsubscribe() {
				try {
					watchService.close();
					subscribed.set(false);
				} catch (ClosedWatchServiceException e) {
					// do nothing
					subscribed.set(false);
				} catch (IOException e) {
					// do nothing
					subscribed.set(false);
				}
			}

			@Override
			public boolean isUnsubscribed() {
				return !subscribed.get();
			}
		};
	}

	private final static Func1<WatchService, Observable<WatchKey>> TO_WATCH_EVENTS = new Func1<WatchService, Observable<WatchKey>>() {
		@Override
		public Observable<WatchKey> call(WatchService watchService) {
			return from(watchService);
		}
	};
	
	
	public static void main(String[] args) {
		File file = new File("C:\\temp_es\\doc");
		from(file,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.OVERFLOW,
				StandardWatchEventKinds.ENTRY_DELETE)
				.subscribe(
				new Action1<WatchEvent<?>>() {

					@Override
					public void call(WatchEvent<?> event) {
							System.out.println("------------");
							Path name = (Path) event.context();
					        Path basePath = getBasePath(file);
					        Path child = basePath.resolve(name);
					        Kind<?> kind = event.kind();
					        if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
					            System.out.println("Creating file: " + child);

					            boolean isGrowing = false;
					            Long initialWeight = new Long(0);
					            Long finalWeight = new Long(0);

					            do {
					                initialWeight = child.toFile().length();
					                try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					                finalWeight = child.toFile().length();
					                isGrowing = initialWeight < finalWeight;

					            } while(isGrowing);

					            System.out.println("Finished creating file!");

					        }
							
					}
				});
	}
	


}
