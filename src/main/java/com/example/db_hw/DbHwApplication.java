package com.example.db_hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbHwApplication.class, args);
        password = args[0];
        ip = args[1];
    }
    public static String password;
    public static String ip;
}
