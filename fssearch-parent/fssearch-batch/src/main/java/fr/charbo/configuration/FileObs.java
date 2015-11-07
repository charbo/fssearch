package fr.charbo.configuration;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;



public class FileObs {
  public Date reference = new Date();


	/**
	 * Returns an {@link Observable} of {@link WatchEvent}s from a
	 * {@link WatchService}.
	 *
	 * @param watchService
	 * @return
	 */
	public final Observable<WatchKey> from(WatchService watchService) {
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
	private WatchEvent<?> current = null;

	@SafeVarargs
	public final Observable<MonEvent> from(final File file, Kind<?>... kinds) {

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
		}).filter(new Func1<WatchEvent<?>, Boolean>() {
      @Override
      public Boolean call(WatchEvent<?> watchEvent) {
        Path path = (Path) watchEvent.context();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.tx*");
        return matcher.matches(path);
      }
    }).flatMap(new Func1<WatchEvent<?>, Observable<MonEvent>>() {
      @Override
      public Observable<MonEvent> call(WatchEvent<?> event) {
        return Observable.create(new OnSubscribe<MonEvent>() {

          @Override
          public void call(Subscriber<? super MonEvent> subscriber) {
            Path name = (Path) event.context();
            Path basePath = getBasePath(file);
            Path child = basePath.resolve(name);
            Kind<?> kind = event.kind();

            MonEvent monEvent = new MonEvent();
            monEvent.setName(event.context() + "");
            monEvent.setPath(child);
            monEvent.setKind(kind);

            subscriber.onNext(monEvent);
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
	public final Observable<WatchService> watchService(final File file, final Kind<?>...kinds){
      return Observable.create(new OnSubscribe<WatchService>() {
        @Override
        public void call(Subscriber<? super WatchService> subscriber) {
          final Path path = getBasePath(file);
          try {
            WatchService watchService = path.getFileSystem().newWatchService();
            path.register(watchService, kinds);
            subscriber.onNext(watchService);
            subscriber.onCompleted();
          } catch (IOException e) {
            subscriber.onError(e);
          }
        }
      });
	}

	private final Path getBasePath(final File file) {
		final Path path;
		if (file.exists() && file.isDirectory())
			path = Paths.get(file.toURI());
		else
			path = Paths.get(file.getParentFile().toURI());
		return path;
	}



	class WatchServiceOnSubscribe implements OnSubscribe<WatchKey> {
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

	private final Subscription createSubscriptionToCloseWatchService(final WatchService watchService, final AtomicBoolean subscribed,
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

	private final Func1<WatchService, Observable<WatchKey>> TO_WATCH_EVENTS = new Func1<WatchService, Observable<WatchKey>>() {
		@Override
		public Observable<WatchKey> call(WatchService watchService) {
			return from(watchService);
		}
	};


	public static void main(String[] args) {
    File file = new File("D:\\GAEL\\tests");

    final FileObs fileObs = new FileObs();
    fileObs.from(file, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_DELETE)
            .distinct().subscribe(new Action1<MonEvent>() {

      @Override
      public void call(MonEvent event) {
        fileObs.method(event);
        System.out.println(event);
      }
    });
  }

  public void method(MonEvent event) {
    Tika tika = new Tika();
    try {
      InputStream fileReader = Files.newInputStream(event.getPath());

      String content =  tika.parseToString(fileReader);

      Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
      String json = "{" +
              "\"title\":\"" + event.getName() + "\"," +
              "\"postDate\":\"2013-01-30\"," +
              "\"content\":\"" + content.replace("\r", " ").replace("\n", " ") + "\"," +
              "\"path\":\"" + event.getPath().toString().replace("\\", "/") + "\"" +
              "}";

      client.prepareIndex("documents", "document", event.getName().hashCode() + "").setSource(json).execute();
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TikaException e) {
      e.printStackTrace();
    }
    System.out.println(event);
  }


  public class MonEvent {
    private String name;
    private Path path;
    private Kind<?> kind;

    public MonEvent() {
      super();
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Kind<?> getKind() {
      return kind;
    }

    public void setKind(Kind<?> kind) {
      this.kind = kind;
    }

    public Path getPath() {
      return path;
    }

    public void setPath(Path path) {
      this.path = path;
    }

    @Override
    public int hashCode() {
      Date now = new Date();
      int flag = 0;
      if (now.getTime() - reference.getTime() > 10000) {
        flag = new Random().nextInt();
        reference = now;
      }

      return name.hashCode() + flag;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof MonEvent)) {
        return false;
      }
      MonEvent other = (MonEvent) obj;

      return this.name.equals(other.name);
    }

    @Override
    public String toString() {
      return "MonEvent{" +
              "kind=" + kind +
              ", name='" + name + '\'' +
              ", path=" + path +
              '}';
    }
  }

}
