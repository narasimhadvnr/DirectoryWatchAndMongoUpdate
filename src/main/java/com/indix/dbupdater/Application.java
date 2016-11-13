package com.indix.dbupdater;

import com.indix.dbupdater.service.CSVFileReader;
import com.indix.dbupdater.service.DirectoryWatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

import javax.annotation.PostConstruct;

/**
 * Created by venkat on 12/11/16.
 */
@SpringBootApplication
@EnableMongoRepositories("com.indix.dbupdater.repository")
public class Application {

    @Autowired
    private CSVFileReader csvFileReader;

    @Autowired
    DirectoryWatchService dservice;

    public static void main(String args[]){
        SpringApplication.run(Application.class,args);

    }

    @PostConstruct
    private void run() throws IOException{
    	dservice.startService("/Users/Gopal/Documents/Datasets/test");
        csvFileReader.writeCSVFolderToMongo("/Users/Gopal/Documents/Datasets/test");
    }
}
