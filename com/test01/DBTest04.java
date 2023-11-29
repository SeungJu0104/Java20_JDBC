package com.test01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import static common.JDBCTemplate.*;
//*은 해당 클래스의 모든 static 메소드를 import한다.

public class DBTest04 {
	
	//MYTEST 테이블에 데이터 INSERT
	//PreparedStatement 이용
	public static void main(String[] args) {
		
		//변수 선언
		Connection con = null;
		PreparedStatement pstm = null;
		int res = 0, no = 0;
		String name = null, nickName = null;
		//SELECT문 외 DML문은 모두 정수 값을 리턴 준다.
		//(DML문을 실행해 변경된 ROW의 개수)
		
		String sql = "INSERT INTO MYTEST VALUES(?, ?, ?);";
		//insert 할 때 값 넣는 자리에 일단 ?을 넣는다.
		//String sql = "INSERT INTO MYTEST VALUES("+ no +", '" + name + "', '" + nickName + "');";
		
		Scanner scn = new Scanner(System.in);
		
		System.out.print("번호 : ");
		no = scn.nextInt();
		
		System.out.print("이름 : ");
		name = scn.next();
		//nextLine 하니까 입력 안받고 그냥 넘어가버린다.
		
		System.out.print("별명 : ");
		nickName = scn.next();
		//nextLine 하니까 입력 안받고 그냥 넘어가버린다.
		
		con = getConnection();
		
		try {
			pstm = con.prepareStatement(sql);
			//PreparedStatement 객체를 준비할 때부터 sql문을 넣어준다.
			//기본 Statement문은 createStatement 메소드로 sql문을 넣지 않고, 객체 생성했다.
			pstm.setInt(1, no);// 첫번째 물음표를 정수 no로 채운다.
			pstm.setString(2, name);//두번째 물음표를 문자열 name으로 채운다.
			pstm.setString(3, nickName);//세번째 물음표를 문자열 nickName으로 채운다.
			
			res = pstm.executeUpdate();//미리 sql문을 넣어놓았기 때문에 실행 가능하다.
			
			if(res > 0) {
				System.out.println("Insert 성공");
			} else {
				System.out.println("Insert 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
			scn.close();
		}
		
	}
	
}
