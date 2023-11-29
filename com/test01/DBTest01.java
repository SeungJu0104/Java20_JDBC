package com.test01;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DBTest01 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//0. Connector J 세팅
		//프로젝트 설정가서 Java Build Path의 Libraries 들어간다.
		//그리고 Add External JARs를 선택해 다운로드 받은 Connector J 파일을 저장한 위치 가서 파일 추가한다.
		//이러면 이클립스에서 드라이버가 등록된 것이다.
		//안하면, 드라이버 못찾아서 에러난다.
		
		//1. DB 연결
		//Class.forName() 메소드 통해 드라이버 등록한다.
		//Connection 객체는 getConnection() 통해 객체 생성한다.
		//일반적인 객체 생성 방식으로는 생성 못한다.
		//반드시 두 메소드 모두 예외처리해야한다.
		Class.forName("com.mysql.cj.jdbc.Driver"); //DB 드라이버 등록(사용하는 DBMS 종류 별로 달라진다.)
		//"com.mysql.cj.jdbc.Driver" ==> MySQL
		//throws로 JVM에 예외처리 넘긴다.
		//해당 구문 실행 결과로 DriverManager가 만들어진다.
		
		//여기선, MySQL 초기 설정할 때 사용했던 url, id, pw와 동일하다.
		//처음이어서 따로 변수에 담았다.
		//Connection 할 때 해당 값들 직접 입력 넣어도 상관없다.
		String url = "jdbc:mysql://localhost:3306/multi"; 
		//외부 PC면 localhost 대신 해당 IP 주소가 들어간다.
		//기본 포트를 사용하면 포트번호는 생략 가능하다.
		String id = "root";
		String pw = "1234";
		
		//Connection 객체 생성
		//DBMS와 연결
		//이전에 작성한 url, id, pw를 갖고 MySQL과 연결한다.
		Connection con = DriverManager.getConnection(url, id, pw);
		//throws로 JVM에 예외처리 넘긴다.
		
		//2. 실행 및 결과 리턴
		//Statement 객체를 생성할 때는 Connection 객체를 사용한다.
		//createStatement() 메소드 호출해 Statement 객체 생성한다.
		//일반적인 객체 생성 방식으로는 생성 못한다.
		Statement stmt = con.createStatement();
		
		//쿼리문
		String sql = "SELECT * FROM EMPLOYEE";
		ResultSet rs = stmt.executeQuery(sql); 
		//Statement 객체에 executeQuery() 사용해 ()안의 쿼리문을 DB에 넘겨 실행시킨다.
		//DB에서 실행시켜 넘겨받은 결과를 ResultSet 타입의 객체에 담는다.
		//MySQL 배우면서 결과 값을 ResultSet이라고 한 이유가 이것이다.
		
		//3. 결과 값 처리
		while(rs.next()){
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt("SALARY"));
		}
		//rs.next()하면 맨 처음에는 EMP_ID에 커서가 있다가
		//반복문 때문에 한 ROW씩 내려오면서
		//해당 ROW의 값들을 get으로 갖고온다.
		//ResultSet의 next()는 boolean 타입이다.
		
		//4. DB 연결 종료
		rs.close();
		stmt.close();
		con.close();
		//지금은 throws로 예외처리해서 따로 예외처리 안했지만
		//try-catch 쓰면 close도 예외처리해야한다.
		//close해주는 순서는 상관있나???
	}
	

}
