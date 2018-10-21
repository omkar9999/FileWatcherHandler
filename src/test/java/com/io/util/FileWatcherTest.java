/**
 * 
 */
package com.io.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test for FileWatcher
 * @author Omkar Marathe
 * @since October 20,2018
 *
 */
public class FileWatcherTest {
	
	FileWatcher fileWatcher;
	FileHandlerTest fileHandlerTest;
	Thread watcherThread;
	Path path;
	File file;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fileHandlerTest = new FileHandlerTest();
		path = Paths.get("");
		path.toFile().mkdirs();
		fileWatcher = new FileWatcher(path, fileHandlerTest, StandardWatchEventKinds.ENTRY_CREATE);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		watcherThread = null;
	}

	@Test
	public void testFileHandler() throws IOException {
		watcherThread = new Thread(fileWatcher);
		file = new File("Test.txt");
		file.createNewFile();
		assertTrue(file.exists());
		watcherThread.start();
	}

}
