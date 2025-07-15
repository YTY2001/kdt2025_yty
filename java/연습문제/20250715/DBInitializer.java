// src/boardapp/DBInitializer.java
package boardapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    // MySQL 서버 접속 (데이터베이스 지정 없이)
    private static final String URL_NO_DB =
        "jdbc:mysql://localhost:3306/?serverTimezone=Asia/Seoul&useSSL=false";
    // 실제 생성할/접속할 DB 이름으로 바꿔 주세요
    private static final String URL_WITH_DB =
        "jdbc:mysql://localhost:3306/board?serverTimezone=Asia/Seoul&useSSL=false";
    private static final String USER     = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1) board 데이터베이스 생성
            try (Connection conn = DriverManager.getConnection(URL_NO_DB, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS board " +
                    "CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci"
                );
                System.out.println("▶ Database 'board' created or already exists.");
            }

            // 2) board DB에 접속해서 table 생성
            try (Connection conn = DriverManager.getConnection(URL_WITH_DB, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS board");
                stmt.executeUpdate(
                    "CREATE TABLE board (" +
                    " id INT AUTO_INCREMENT PRIMARY KEY," +
                    " title VARCHAR(255) NOT NULL," +
                    " writer VARCHAR(100) NOT NULL," +
                    " content TEXT," +
                    " date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    " hit INT DEFAULT 0" +
                    ") ENGINE=InnoDB"
                );
                System.out.println("▶ Table 'board' created.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ SQL error during initialization.");
            e.printStackTrace();
        }
    }
}
