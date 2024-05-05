package com.exampl.kata.app.fishbread.dao;

import com.exampl.kata.app.fishbread.domain.FishBread;
import com.exampl.kata.app.fishbread.mapper.FishBreadJdbcMapper;

import java.sql.*;
import java.util.Collections;
import java.util.List;

import static com.exampl.kata.common.support.db.DatabaseConstants.*;
import static com.exampl.kata.common.support.db.DatabaseConstants.DB_PASSWORD;

public class FishBreadDao {
    static { // static 블록은 클래스가 로드될 때 실행됨.
        try {
            Class.forName(DB_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final FishBreadJdbcMapper mapper = new FishBreadJdbcMapper();

    public List<FishBread> findAll() {
        String sql = "SELECT * FROM fishbread";
        // try catch ~ resources <=
        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL,
                        DB_USERNAME,
                        DB_PASSWORD
                );
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            return mapper.toEntityList(resultSet);
        } catch (SQLException e) {

        }

        return Collections.emptyList();
    }
}
