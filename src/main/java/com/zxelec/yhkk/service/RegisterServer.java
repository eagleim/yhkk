package com.zxelec.yhkk.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RegisterServer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Thread th = new Thread();
        th.start();
    }
}
