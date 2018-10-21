/**
 * 
 */
package com.io.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;

/**
 * This class is created to demonstrate 
 * FileHandler functionality
 * @author Omkar Marathe
 * @since October 20,2018
 *
 */
public class FileHandlerTest implements FileHandler {

	/*
	 * This implemented method will delete the file
	 * 
	 * @see com.io.util.FileHandler#handle(java.io.File,
	 * java.nio.file.WatchEvent.Kind)
	 */
	public void handle(File file, Kind<?> fileEvent) {
		System.out.println("Handler is triggered");
		if(fileEvent == StandardWatchEventKinds.ENTRY_CREATE) {
			System.out.println("Deleting the file "+file.getAbsolutePath());
			try {
				boolean deleted = Files.deleteIfExists(Paths.get(file.getPath()));
				assertTrue(deleted);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
