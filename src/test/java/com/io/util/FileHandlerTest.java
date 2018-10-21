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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Ignore;

/**
 * This class is created to demonstrate 
 * FileHandler functionality
 * @author Omkar Marathe
 * @since October 20,2018
 *
 */
@Ignore
public class FileHandlerTest implements FileHandler {

	private static final Logger LOGGER = Logger.getLogger(FileHandlerTest.class.getName());
	
	/*
	 * This implemented method will delete the file
	 * 
	 * @see com.io.util.FileHandler#handle(java.io.File,
	 * java.nio.file.WatchEvent.Kind)
	 */
	public void handle(File file, Kind<?> fileEvent) {
		LOGGER.log(Level.INFO,"Handler is triggered for file {0}",file.getPath());
		if(fileEvent == StandardWatchEventKinds.ENTRY_CREATE) {
			try {
				boolean deleted = Files.deleteIfExists(Paths.get(file.getPath()));
				assertTrue(deleted);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
