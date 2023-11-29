package com.test01;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DBTest02 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//1. 연결
		//드라이버 등록
		//DriverManager가 만들어진다.
		Class.forName("com.mysql.cj.jdbc.Driver");
		//throws로 jvm에 예외처리 넘긴다.
		
		//Connection 객체 생성
		//getConnection 메소드 사용.
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/multi", "root", "1234");
		//throws로 예외처리 jvm에 넘긴다.
		
		
		//2. SQL 실행 및 리턴
		//Statement 객체 생성
		//Connection 객체의 레퍼런스 변수명.createStatement();
		Statement stmt = con.createStatement();
		
		//쿼리문 실행 및 실행 결과 저장
		//Statement 객체의 레퍼런스 변수명. executeQuery("쿼리문" 또는 쿼리문 담긴 변수명)
		//ResultSet 타입으로 리턴 받기 때문에 ResultSet 타입의 객체에 저장한다.
		ResultSet rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
		
		//갖고 온 DB를 자바 코드로 뭔가 작업을 한다.
		while(rs.next()) {
			System.out.println(rs.getString(1) + " : " + rs.getString(2) + "\t[" + rs.getString("LOCATION_ID") + "]");
		}
		//ResultSet의 next()는 boolean 값을 리턴 준다.
		//DEPT_ID에 커서가 있다가
		//한 ROW씩 내려가면서 해당 값들을 get으로 가져온다.
		
		//3. 연결 해제
		//반드시 종료해서 메모리 돌려줘야한다.
		//자원의 효율적 사용
		rs.close();
		stmt.close();
		con.close();
		//지금은 throws로 예외처리해서 따로 예외처리 안했지만
		//try-catch 쓰면 close도 예외처리해야한다.
	}

}
