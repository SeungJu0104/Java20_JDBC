package com.test01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import static common.JDBCTemplate.*;
//*은 해당 클래스의 모든 static 메소드를 import한다.

public class DBTest05 {
	
	//MYTEST 테이블에서 데이터 삭제
	public static void main(String[] args) {
		
		//변수 선언
		Connection con = null;
		PreparedStatement pstm = null;
		int res = 0;
		String name = null, sql = null;
		
		sql = "DELETE FROM MYTEST WHERE MNAME = ?";
		//값 부분은 ?로 채운다.
		
		//입력
		Scanner scn = new Scanner(System.in);
		
		System.out.print("삭제할 MNAME의 이름 : ");
		name = scn.next();
		
		//쿼리 실행 및 리턴
		con = getConnection();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, name);//첫번째 물음표에 문자열 name을 채운다.
			res = pstm.executeUpdate();//쿼리 실행하고, 결과 값을 리턴 받는다.
			
			if(res > 0) {
				System.out.println("삭제 성공");
				commit(con);
				//auto commit을 종료했기 때문에,
				//별도로 commit 안하면 실행을 해도
				//DB에 변경 내용이 반영되지 않는다.
				//다시말해, auto commit을 끈 상태에서 별도 commit을 안하면,
				//이클립스에서 삭제 성공이라고 결과가 나와도
				//디비버로 DB를 보면 이클립스로 지운 내용이 삭제되지 않고 여전히 남아있다.
			} else {
				System.out.println("삭제 실패");
				rollback(con);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB 종료
			close(pstm);
			close(con);
			scn.close();
		}
	}

}
