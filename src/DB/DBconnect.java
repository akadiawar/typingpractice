package DB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
    public static final String databaseDriver = "com.mysql.cj.jdbc.Driver";
    public static final String databaseUrl = "jdbc:mysql://localhost/TypingPractice?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";
    public static final String databaseUser = "root";
    public static final String databasePassword = "011015";
    public static Connection connection = null;

    public static void main(String[] args) {
		// Connection TEST
        connect();
        // Close TEST
        close();

    }

    public static Connection connect() { //DB CONNECT
        try {
            Class.forName(databaseDriver);
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            if (connection != null) System.out.println("Connection Succeed");
            else System.out.println("Connection Failed");
        } catch (Exception e) {
            // DB접속에러가 나는 경우 JDBC드라이버(mysql-connector)경로를 Java Build Path에 등록했는지 확인하기
        	JOptionPane.showMessageDialog(null, "데이터베이스가 연결되지 않았습니다", "경고!!", JOptionPane.WARNING_MESSAGE);
            System.err.println("Connection Error! : " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void close() { //DB USE AFTER CLOSE
        try {
            if (connection != null) {
                System.out.println("Connection Close");
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Connection Closing Failed! : " + e.getMessage());
            e.printStackTrace();
        }
    }
}