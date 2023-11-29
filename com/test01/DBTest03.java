package com.test01;

import common.JDBCTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBTest03 extends JDBCTemplate{
	
	//키보드로 입력받은 내용을 DB에 저장한다.(insert)
	//DB 연결, 해제 부분 코드들은 별도의 메소드로 만들어 가져다 써본다.
	public static void main(String[] args) {
		//DBTest03 dt = new DBTest03();
		//dt.insert();
		
		new DBTest03().insert();
		System.out.println();
		new DBTest03().select();
	}
	
	public void insert() {
		
		//변수 선언
		Connection con = null;
		Statement stmt = null;
		//SELECT외 DML은 실행 후 정수(적용된 row의 숫자)를 리턴 받는다.
		//그래서 ResultSet 대신 int 변수 생성해서 초기화
		int res = 0, no = 0;
		String name = null, nickName = null;
		
		Scanner scn = new Scanner (System.in);
		
		System.out.print("번호 : ");
		no = scn.nextInt();
		
		System.out.print("이름 : ");
		name = scn.next();
		//nextLine 하니까 입력 안받고 그냥 넘어가버린다.
		
		System.out.print("별명 : ");
		nickName = scn.next();
		//nextLine 하니까 입력 안받고 그냥 넘어가버린다.
		
		String sql = "INSERT INTO MYTEST VALUES("+ no +", '" + name + "', '" + nickName + "');";
		//SQL문의 문자는 '' 또는 ""를 붙어야하므로 따로 ''를 붙여줬다.
		
		//1. DB 연결
		con = getConnection();
		//상속 받아서 부모 클래스의 메소드 모두 가져왔다.
		//오버라이딩 안했으니 부모 클래스의 메소드 가져다 쓴다.
		//아니면 import static common.JDBCTemplate.*;
		//*은 해당 클래스의 모든 static 메소드를 import한다.
		
		//2. SQL 실행 및 리턴
		try {
			stmt = con.createStatement();
			res = stmt.executeUpdate(sql);
			//SELECT문 외의 DML은 executeUpdate 메소드를 사용한다.
			
			if(res > 0) {
				System.out.println("Insert 성공");
				//SQL 실행 결과로 변경된 ROW을 개수를 받기 때문에
				//0 초과 값을 받으면 제대로 INSERT된 것이다.
			} else {
				System.out.println("Insert 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//4. DB 연결 해제
			close(stmt);
			close(con);
			//사실 close 메소드 내에 이미 예외처리되어 있어 굳이 finally에 안넣어도 동작한다.
			//ResultSet은 없으므로 종료할 필요가 없다.
			scn.close();
			//미리 작성해둔 close 메소드 실행
		}
		
	}
	
	public void select() {
		
		//변수 선언
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM MYTEST;";
		
		Scanner scn = new Scanner(System.in);
		
		con = getConnection();
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString("nickName"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close(rs);
		close(stmt);
		scn.close();
		
	}

}
