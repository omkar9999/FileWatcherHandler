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
 * 
 * @author Omkar Marathe
 * @since October 20,2018
 *
 */
public class FileWatcherTest {

	
	FileHandlerTest fileHandlerTest;
	Thread watcherThread;
	Path path;
	File file;
	private FileWatcher fileWatcher;
	
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

	@Test(timeout=900)
	public void testFileHandler() throws IOException, InterruptedException {
		watcherThread = new Thread(fileWatcher);
		file = new File("Test.txt");
		watcherThread.start();
		file.createNewFile();
		assertTrue(file.exists());
	}
	
	@Test(timeout=900)
	public void testFileWatcherStart() throws InterruptedException, IOException {
		Thread watcherThread2 = new Thread(fileWatcher);
		watcherThread2.start();
		Thread.sleep(450);
		File file2 = new File("Test1.txt");
		file2.createNewFile();
	}
	
	@Test(timeout=900)
	public void testFileWatcherTestInterrupt() throws InterruptedException {
		Thread watcherThread3 = new Thread(fileWatcher);
		watcherThread3.start();
		watcherThread3.interrupt();
	}

}
