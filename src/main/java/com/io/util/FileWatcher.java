/**
 * 
 */
package com.io.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileWatcherService to be implemented 
 * to perform any action after the file event is detected
 * @author Omkar Marathe
 * @since October 20,2018
 *
 */
public class FileWatcher implements Runnable {
	
	 private WatchService watcher;
	 private FileHandler fileHandler;
	 private List<Kind<?>> watchedEvents;

	/**
	 * @param watcher @Path directory to watch files into
	 * @param fileHandler @FileHandler implemented instance to handle the file event
	 * @param watchedEvents Set of file events watched
	 * @throws IOException 
	 */
	public FileWatcher(Path directory, FileHandler fileHandler, WatchEvent.Kind<?>... watchedEvents) throws IOException {
		super();
		this.watcher = FileSystems.getDefault().newWatchService();
		this.fileHandler = fileHandler;
		this.watchedEvents = Arrays.asList(watchedEvents);
		directory.register(watcher,watchedEvents);
	}
		
	@SuppressWarnings({ "unchecked" })
	public void run() {
		 while (true) {
	            WatchKey key;
	            try {
	                key = watcher.take();
	            } catch (InterruptedException ex) {
	                return;
	            }
	            for (WatchEvent<?> event : key.pollEvents()) {
	                WatchEvent.Kind<?> kind = event.kind();

	                WatchEvent<Path> ev = (WatchEvent<Path>) event;
	                Path fileName = ev.context();

	                if (watchedEvents.contains(kind)) {
	                	Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Invoking handle on {}", fileName.toAbsolutePath());
	                    fileHandler.handle(fileName.toAbsolutePath().toFile(),kind);
	                }
	            }
	            key.reset();
	        }
	}

}
