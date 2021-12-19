package org.kelompok3.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
        var x = 1;
        while(true){
            System.out.println(x);
            x++;
            if(x > 1000){
                break;
            }
        }
    }

}
