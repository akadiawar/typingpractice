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
	String url = "jdbc:mysql://localhost:3306/TypingPractice?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";	//dbstudy ��Ű��
	String user = "root";
	String passwd = "1234";		//������ ������ root ������ ��й�ȣ�� �Է��ϸ� �ȴ�.

	Database() {	//�����ͺ��̽��� �����Ѵ�.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			System.out.println("MySQL ���� ���� ����");
		} catch(Exception e) {
			System.out.println("MySQL ���� ���� ���� > " + e.toString());
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

	/* �α��� ������ Ȯ�� */
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
					System.out.println("�α��� ����");
					loggedInUserId = id;
				}
				
				else {
					flag = false;
					System.out.println("�α��� ����");
				}
				count++;
			}
		} catch(Exception e) {
			flag = false;
			System.out.println("�α��� ���� > " + e.toString());
		}
		
		return flag;
	}
	/* ȸ������ */
	public boolean joinCheck(String _i, String _p) 
	{
		boolean flag = false;
		
		String id = _i;
		String pw = _p;
			
		try {
			String insertStr = "INSERT INTO member VALUES('" + id + "', '" + pw + "')";
			stmt.executeUpdate(insertStr);
				
			flag = true;
			System.out.println("ȸ������ ����");
			//loggedInUserId = id;

			
			
		} catch(Exception e) {
			flag = false;
			System.out.println("ȸ������ ���� > " + e.toString());
		}
			
		return flag;
	}
	
	/* ��� ���� */
	public boolean saveTypingRecord(String id, int level, double correct, double notCorrect, double tasu, String testdate) {
	    boolean flag = false;
	    
	    try {
	        // ���� �ð��� �����ͼ� ���ڿ��� ��ȯ
	    	if (testdate == null || testdate.isEmpty()) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	            testdate = dateFormat.format(new Date());
	        }

	        // SQL ���� �ۼ�
	        String insertRecordStr = "INSERT INTO record (id, level, correct, not_correct, tasu, testdate) " +
	                "VALUES (?, ?, ?, ?, ?, ?)";

	        // PreparedStatement�� ����Ͽ� SQL ������ ����
	        PreparedStatement preparedStatement = con.prepareStatement(insertRecordStr);
	        preparedStatement.setString(1, id);
	        preparedStatement.setInt(2, level);
	        preparedStatement.setDouble(3, correct);
	        preparedStatement.setDouble(4, notCorrect);
	        preparedStatement.setDouble(5, tasu);
	        preparedStatement.setString(6, testdate);

	        // ����
	        preparedStatement.executeUpdate();

	        flag = true;
	        System.out.println("Ÿ�ڱ�� ���� ����");

	    } catch (Exception e) {
	        flag = false;
	        System.out.println("Ÿ�ڱ�� ���� ���� > " + e.toString());
	    }

	    return flag;
	}

	/* ���� ǥ�� */
	public void setCurrentLevel(int level) {
        currentLevel = level;
        System.out.println("���� ������ " + level + "�� �����Ǿ����ϴ�.");
    }
	/* ���� ���� �������� */
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
	/* ���� ���� ��� �ʱ�ȭ */
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