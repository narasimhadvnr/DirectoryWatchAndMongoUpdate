package com.indix.dbupdater.service;

import com.darylteo.nio.DirectoryWatcher;
import com.darylteo.nio.DirectoryWatcherSubscriber;
import com.darylteo.nio.ThreadPoolDirectoryWatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by venkat on 12/11/16.
 */
@Service
public class DirectoryWatchService {

    //private static Path root = Paths.get("/home/venkat/Desktop/test");
	@Autowired
	CSVFileReader csvReaderService;

    public void startService(String folderName) throws IOException {
        ThreadPoolDirectoryWatchService factory=new ThreadPoolDirectoryWatchService();
        DirectoryWatcher watcher= factory.newWatcher(folderName);
        factory = new ThreadPoolDirectoryWatchService();
        watcher = factory.newWatcher(folderName);

        System.out.println("subscribing to watcher");
        watcher.subscribe(new DirectoryWatcherSubscriber() {
            @Override
            public void entryCreated(DirectoryWatcher watcher, Path entry) {
                System.out.println(entry+" has been created");
                csvReaderService.writeCSVFileToMongo(entry.getFileName().toString(), false);
                super.entryCreated(watcher, entry);
            }
        });

        watcher.subscribe(new DirectoryWatcherSubscriber() {
            @Override
            public void entryModified(DirectoryWatcher watcher, Path entry) {
                System.out.println(entry+" has been modified");
                csvReaderService.writeCSVFileToMongo(entry.getFileName().toString(), false);
                super.entryCreated(watcher, entry);
            }
        });

      /* watcher.subscribe(new DirectoryWatcherSubscriber() {
            @Override
            public void entryDeleted(DirectoryWatcher watcher, Path entry) {
                System.out.println(entry+" has been deleted");
                super.entryCreated(watcher, entry);
            }
        });*/
    }
}
