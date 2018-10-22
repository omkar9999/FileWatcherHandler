# FileWatcherHandler

[![Build Status](https://travis-ci.org/omkar9999/FileWatcherHandler.svg?branch=master)](https://travis-ci.org/omkar9999/FileWatcherHandler) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.io.util%3Afilewatcherhandler&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.io.util%3Afilewatcherhandler) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.io.util%3Afilewatcherhandler&metric=coverage)](https://sonarcloud.io/dashboard?id=com.io.util%3Afilewatcherhandler)

This project allows watching files for different file events like create,modify &amp; delete and then act on these events in generic way.

#How to Use?

1) Create a Path object representing the directory to monitor for file events.

```java
Path path = Paths.get("/home/omkar/test");
```

2) Implement the [FileHandler](src/main/java/com/io/util/FileHandler.java) interface to perform an action detected by file event registered.

```java
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
```

3) Create an instance of an Implemented [FileHandler](src/test/java/com/io/util/FileWatcherTest.java)

```java
FileHandlerTest fileHandlerTest = new FileHandlerTest();
```

4) Create an instance of a FileWatcher by passing path,an instance of an Implemented [FileHandler](src/main/java/com/io/util/FileHandler.java) and types of file events that you want to monitor separated by commas.

```java
FileWatcher fileWatcher = new FileWatcher(path, fileHandlerTest, StandardWatchEventKinds.ENTRY_CREATE);
```
5) Now Create and start a new Thread.

```java
Thread watcherThread = new Thread(fileWatcher);
watcherThread.start();
```
This thread will start polling for your registered file events and will invoke your custom handle method once any of the registered events are detected.

## Authors

* **Omkar Marathe** - *Initial work* - [Omkar](https://github.com/omkar9999/)

## Support/Contributing

If you've found an error in this sample, please file an issue [here](https://github.com/omkar9999/FileWatcherHandler/issues)

Patches are encouraged, and may be submitted by forking this project and
submitting a pull request through GitHub.

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details