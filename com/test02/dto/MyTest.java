package com.test02.dto;

//DB에서 받아온 데이터를 객체에 저장하고, 다른 곳에 데이터를 리턴주는 클래스
public class MyTest {
	
	//필드 선언
	private int mno;
	private String mname;
	private String nickname;
	
	//생성자
	public MyTest () {}
	public MyTest (int mno, String mname, String nickname) {
		super();
		this.mno = mno;
		this.mname = mname;
		this.nickname = nickname;
	}
	
	public MyTest(String mname, String nickname) {
		super();
		this.mname = mname;
		this.nickname = nickname;
	}
	
	//getter 와 setter 메소드
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	//toString 오버라이딩
	@Override
	public String toString() {
		return "["+ mno + ", " + mname + ", " + nickname + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
