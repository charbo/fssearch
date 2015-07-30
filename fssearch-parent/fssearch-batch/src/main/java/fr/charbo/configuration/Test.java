package fr.charbo.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.Subscriber;

public class Test {
	


    public static void main(String[] args) {
    	File file = new File("C:\\temp_es\\doc");
    	Observable<WatchService> observable = Observable.create(new OnSubscribe<WatchService>() {

			@Override
			public void call(Subscriber<? super WatchService> subscriber) {
				final Path path = getBasePath(file);
				try {
					WatchService watchService = path.getFileSystem().newWatchService();
					path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
							StandardWatchEventKinds.ENTRY_DELETE);
					subscriber.onNext(watchService);
					subscriber.onCompleted();
				} catch (IOException e) {
					subscriber.onError(e);
				}
				
			}
    		
    	});
    	
    	observable.subscribe(new Action1<Object>() {

			@Override
			public void call(Object t) {
				System.out.println("--" + t.getClass());
				
			}
		});
    	
    	
    	Observable<WatchKey> keys = observable.flatMap( new Func1<WatchService, Observable<WatchKey>>() {

			@Override
			public Observable<WatchKey> call(WatchService t) {
				WatchKey key = null;
				try {
					key = t.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				return Observable.create();
				return null;
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
    
}
