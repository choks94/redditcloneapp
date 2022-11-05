package com.choks.redditclone;

import org.testcontainers.containers.MySQLContainer;

public class BaseTest {


    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
            .withDatabaseName("spring-reddit-test-db")
            .withUsername("testuser")
            .withPassword("pass");

    static {
        mySQLContainer.start();
    }
}
