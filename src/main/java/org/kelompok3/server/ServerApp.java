package org.kelompok3.server;

import org.kelompok3.database.DBConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApp {
    ServerApp(){
        DBConnector.prepareAll();
    }
    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class);
    }
}
