package boardapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // 사용할 데이터베이스 이름이 'board' 라면 URL을 이렇게 설정하세요
    private static final String URL =
        "jdbc:mysql://localhost:3306/board?serverTimezone=Asia/Seoul&useSSL=false";
    private static final String USER     = "root";   // 본인 MySQL 계정
    private static final String PASSWORD = "1234";   // 본인 MySQL 비밀번호

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
