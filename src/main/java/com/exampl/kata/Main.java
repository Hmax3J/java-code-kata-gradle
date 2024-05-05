package com.exampl.kata;

import com.exampl.kata.app.MainApplication;
import com.exampl.kata.common.config.KataFlyway;

public class Main {

    public static void main(String[] args) {
        // flyway
        KataFlyway flyway = new KataFlyway();
        flyway.migrate();

        // application
        MainApplication application = new MainApplication();
        application.run();
    }
}
