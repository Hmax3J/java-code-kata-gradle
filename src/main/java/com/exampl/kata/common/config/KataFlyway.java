package com.exampl.kata.common.config;

import org.flywaydb.core.Flyway;

import static com.exampl.kata.common.support.db.DatabaseConstants.*;

public class KataFlyway {
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        DB_URL,
                        DB_USERNAME,
                        DB_PASSWORD
                )
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
