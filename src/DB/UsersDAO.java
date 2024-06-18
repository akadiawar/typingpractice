package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class UsersDAO {
	private UsersDAO() {
    }

    private static UsersDAO instance = new UsersDAO();

    public static UsersDAO getInstance() {
        return instance;
    }
    
    //유저 이름으로 전체 기록 검색하기
    public Vector<Users> getusers(String username) throws SQLException {
    	Vector<Users> list = new Vector<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select level, correct, not_correct, tasu, testdate from users where username=? ";
        try {
            conn = DBconnect.connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	Users users = new Users();
                users.setLevel(rs.getInt("level"));
                users.setCorrect(rs.getDouble("correct"));
                users.setNot_correct(rs.getDouble("not_correct"));
                users.setTasu(rs.getDouble("tasu"));
                LocalDateTime time = LocalDateTime.parse(rs.getString("testdate"));
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
                users.setTestdate(time.format(formatter));
                list.add(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBconnect.close();
        }
        return list;
    }
    
    //유저기록 추가
    public boolean insertUsers(Users users) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";
        boolean result = false;
        try {
            conn = DBconnect.connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, users.getUsername());
            pstmt.setInt(2, users.getLevel());
            pstmt.setDouble(3, users.getCorrect());
            pstmt.setDouble(4, users.getNot_correct());
            pstmt.setDouble(5, users.getTasu());
            pstmt.setString(6, users.getTestdate());

            int r = pstmt.executeUpdate();
            System.out.println("return result = " + r);
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBconnect.close();
        }

        return result;
    }
    
    //유저기록 초기화
    public boolean DeleteUsers(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM users WHERE (username = ?)";
        boolean result = false;
        try {
            conn = DBconnect.connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            int r = pstmt.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBconnect.close();
        }
        return result;
    }
}
