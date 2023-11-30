package com.test02.dao;

import com.test02.dto.MyTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import static common.JDBCTemplate.*;

//DB에 연결
public class MyTestDao {
	//select 메소드
	//데이터가 몇개 들어올지 모르기때문에 배열보다
	//크기가 가변적인 List가 더 효율적이다.
	public List <MyTest> selectAll(){
		//변수, 객체 선언
		Connection con = getConnection();
		//import static common.JDBCTemplate.*;로
		//JDBCTemplate 클래스의 모든 메소드가 메모리에 상주.
		//그래서 객체 등으로 따로 지칭안해도 인식한다.
		
		Statement stmt = null;
		ResultSet rs = null;
		
		List <MyTest> res = new ArrayList <MyTest>();
		String sql = "SELECT * FROM MYTEST;";
		
		//쿼리 실행 후 결과 리턴
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			//Statement는 실행할 때 쿼리문을 넣는다.
			
			while(rs.next()) {
				MyTest tmp = new MyTest(rs.getInt(1), rs.getString(2), rs.getString(3));
				//한 Row가 MyTest 객체에 담긴다.
				res.add(tmp);
				//한 Row씩 List에 추가한다.
				//이걸 한줄로 줄이면
				//res.add(new MyTest(rs.getInt(1), rs.getString(2), rs.getString(3))); 이렇게 쓸 수 있다.
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
		
		return res;
	}
	
	//insert 메소드
	public int insert(MyTest mytest) {
		
		//변수, 객체 선언
		Connection con = null;
		PreparedStatement pstm = null;
		int res = 0;
		//String sql = "INSERT INTO MYTEST VALUES(?, ?, ?);";
		String sql = "INSERT INTO MYTEST VALUES(NULL, ?, ?);";
		//디비버로 기본키 auto increment 설정 걸면서 번호 쓸 필요가 없어졌다.
		//null값 집어 넣으면 알아서 1부터 시작해 1씩 올라간다.
		
		con = getConnection();
		//import static common.JDBCTemplate.*;로
		//JDBCTemplate 클래스의 모든 메소드가 메모리에 상주.
		//그래서 객체 등으로 따로 지칭안해도 인식한다.
		
		try {
			pstm = con.prepareStatement(sql);
			//PreparedStatement는 객체 만들 때 sql문을 넣는다.
			//Statement는 실행할 때 sql문을 넣는다.
			
			//pstm.setInt(1, mytest.getMno()); //auto increment때문에 번호 설정 필요 없어졌다.
			pstm.setString(1, mytest.getMname());
			pstm.setString(2, mytest.getNickname());
			//쿼리문의 ? 부분을 채워넣는다.
			//?의 개수만큼 다 안채우면 에러 발생한다.
			
			res = pstm.executeUpdate();
			//PreparedStatement는 실행할 때 sql문 안 넣는다.
			
			if(res > 0) {
				commit(con);
			} else {
				rollback(con);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}
		
		return res;
	}
	
	//update 메소드
	public int update(MyTest mytest) {
		//변수, 객체 선언
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = "UPDATE MYTEST SET MNAME = ?, NICKNAME = ? WHERE MNO = ?";
		//SQL신택스 에러는 sql 구문 중 뭔가 잘못된 것이다.
		
		//String sql = " UPDATE MYTEST SET MNAME = ?, NICKNAME = ? "
		//				+" WHERE MNO = ? ";
		//이렇게 둘로 나눠쓰는 경우 간혹가다 각 문장의 시작과 끝에 공백이 없으면 합치는 과정에서 문장을 인식못해 문제가 생기는 경우가 있다.
		//그래서 문장 앞 뒤에 공백 주는 것이 편하다.
		
		//쿼리 실행 후 결과 리턴
		try {
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, mytest.getMname());
			pstm.setString(2, mytest.getNickname());
			pstm.setInt(3, mytest.getMno());
			
			res = pstm.executeUpdate();
			
			//연결 설정할 때, autocommit off했으므로,
			//수동으로 commit해야한다.
			if(res > 0) {
				commit(con);
			} else {
				rollback(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con);
			close(pstm);
		}
		
		return res;
	}
	
	//delete 메소드
	public int delete(int no) {
		
		//변수, 객체 선언
		Connection con = null;
		PreparedStatement pstm = null;
		int res = 0;
		String sql = "DELETE FROM MYTEST WHERE MNO = ?";
		
		//쿼리 실행 후 결과 리턴
		con = getConnection();
		
		try {
			pstm = con.prepareStatement(sql);
			
			pstm.setInt(1, no);
			//쿼리문의 물음표에 no 값이 들어간다.
			//쿼리문에 물음표가 1개만 있기 때문에 1이외의 값 쓰면 에러 발생한다.
			
			res = pstm.executeUpdate();
			
			//연결 설정할 때, autocommit off했으므로,
			//수동으로 commit해야한다.
			if(res >0) {
				commit(con);
			} else {
				rollback(con);
			}
			//auto commit off했기 때문에, commit 안하면
			//내가 구문 실행해도 DB에 반영 안된다.
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con);
			close(pstm);
		}
		
		return res;
	}
	
}
