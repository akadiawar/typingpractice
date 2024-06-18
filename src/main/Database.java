package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Database {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs;
	String sql;
	String testdate;
	String loggedInUserId;
	int currentLevel;
	String url = "jdbc:mysql://localhost:3306/TypingPractice?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";	//dbstudy 스키마
	String user = "root";
	String passwd = "1234";		//본인이 설정한 root 계정의 비밀번호를 입력하면 된다.

	Database() {	//데이터베이스에 연결한다.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			System.out.println("MySQL 서버 연동 성공");
		} catch(Exception e) {
			System.out.println("MySQL 서버 연동 실패 > " + e.toString());
		}
	}
	
	private static Database instance = new Database();

    public static Database getInstance() {
        return instance;
    }
    public void setLoggedInUserId(String ID) {
        this.loggedInUserId = ID;
    }

    
    public String getLoggedInUserId() {
        return loggedInUserId;
    }

	/* 로그인 정보를 확인 */
	boolean logincheck(String _i, String _p) 
	{
		boolean flag = false;
		
		String id = _i;
		String pw = _p;
		
		try {
			String checkingStr = "SELECT password FROM member WHERE id='" + id + "'";
			ResultSet result = stmt.executeQuery(checkingStr);
			
			int count = 0;
			while(result.next()) {
				if(pw.equals(result.getString("password"))) {
					flag = true;
					System.out.println("로그인 성공");
					loggedInUserId = id;
				}
				
				else {
					flag = false;
					System.out.println("로그인 실패");
				}
				count++;
			}
		} catch(Exception e) {
			flag = false;
			System.out.println("로그인 실패 > " + e.toString());
		}
		
		return flag;
	}
	/* 회원가입 */
	public boolean joinCheck(String _i, String _p) 
	{
		boolean flag = false;
		
		String id = _i;
		String pw = _p;
			
		try {
			String insertStr = "INSERT INTO member VALUES('" + id + "', '" + pw + "')";
			stmt.executeUpdate(insertStr);
				
			flag = true;
			System.out.println("회원가입 성공");
			//loggedInUserId = id;

			
			
		} catch(Exception e) {
			flag = false;
			System.out.println("회원가입 실패 > " + e.toString());
		}
			
		return flag;
	}
	
	/* 기록 저장 */
	public boolean saveTypingRecord(String id, int level, double correct, double notCorrect, double tasu, String testdate) {
	    boolean flag = false;
	    
	    try {
	        // 현재 시간을 가져와서 문자열로 변환
	    	if (testdate == null || testdate.isEmpty()) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	            testdate = dateFormat.format(new Date());
	        }

	        // SQL 쿼리 작성
	        String insertRecordStr = "INSERT INTO record (id, level, correct, not_correct, tasu, testdate) " +
	                "VALUES (?, ?, ?, ?, ?, ?)";

	        // PreparedStatement를 사용하여 SQL 인젝션 방지
	        PreparedStatement preparedStatement = con.prepareStatement(insertRecordStr);
	        preparedStatement.setString(1, id);
	        preparedStatement.setInt(2, level);
	        preparedStatement.setDouble(3, correct);
	        preparedStatement.setDouble(4, notCorrect);
	        preparedStatement.setDouble(5, tasu);
	        preparedStatement.setString(6, testdate);

	        // 실행
	        preparedStatement.executeUpdate();

	        flag = true;
	        System.out.println("타자기록 저장 성공");

	    } catch (Exception e) {
	        flag = false;
	        System.out.println("타자기록 저장 실패 > " + e.toString());
	    }

	    return flag;
	}

	/* 레벨 표시 */
	public void setCurrentLevel(int level) {
        currentLevel = level;
        System.out.println("현재 레벨이 " + level + "로 설정되었습니다.");
    }
	/* 유저 정보 가져오기 */
	public Vector<Record> getUserRecord() throws SQLException{
		Vector<Record> list = new Vector<>();
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String id = loggedInUserId;
		String Sql = "select level, correct, not_correct, tasu, testdate from record where id=? ";
		try {
			pstmt = con.prepareStatement(Sql);
			pstmt.setString(1, id);
			rst = pstmt.executeQuery();
            while (rst.next()) {
                Record record = new Record();
                record.setLevel(rst.getInt("level"));
                record.setCorrect(rst.getDouble("correct"));
                record.setNot_correct(rst.getDouble("not_correct"));
                record.setTasu(rst.getDouble("tasu"));
                record.setTestdate(rst.getString("testdate"));
                list.add(record);
            }
		
	}catch (Exception e) {
        e.printStackTrace();
		}
		return list;
	}
	/* 유저 정보 목록 초기화 */
	public boolean Deletelist() {
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM record WHERE id=?";
        String id = loggedInUserId;
        boolean result = false;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            int r = pstmt.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}