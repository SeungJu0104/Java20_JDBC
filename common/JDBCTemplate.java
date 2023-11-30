package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//DB연결, 연결해제, 저장, 취소 구문을 따로 적어
//필요할 때마다 가져다 쓴다.
public class JDBCTemplate {
	//1. DB 연결 메소드
	//Connection 객체까지 만들어 리턴 준다.
	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//DriverManager 객체 생성
		} catch (ClassNotFoundException e) {
			System.out.println("[Error] 드라이버 등록 실패");
			e.printStackTrace(); //에러 메시지 출력
		}
		
		String url = "jdbc:mysql://localhost:3306/multi";
		String id = "root";
		String pw = "1234";
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url,id,pw);
			//Connection 객체 생성
			con.setAutoCommit(false);//자동 Commit Off
			
		} catch (SQLException e) {
			System.out.println("[Error] DB 연결 실패");
			e.printStackTrace();
		}
		
		//Connection 객체 con을 리턴
		return con;
	}
	
	//2. 연결해제
	//오버로딩
	//1) Connection 해제
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error] DB 연결 해제 실패");
			e.printStackTrace();
		}
	}
	
	//2) Statement 해제
	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			System.out.println("[Error] Statement 연결 해제 실패");
			e.printStackTrace();
		}
	}
	
	//3) ResultSet 해제
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			System.out.println("[Error] ResultSet 연결 해제 실패");
			e.printStackTrace();
		}
	}
	
	//3. Commit / Rollback
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			System.out.println("[Error] Commit  실패");
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			System.out.println("[Error] Rollback  실패");
			e.printStackTrace();
		}
	}
}
