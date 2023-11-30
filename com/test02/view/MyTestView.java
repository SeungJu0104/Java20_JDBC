package com.test02.view;

import java.util.Scanner;
import java.sql.Connection;
import java.util.List;

import com.test02.dao.MyTestDao;
import com.test02.dto.MyTest;
//위의 import문을 줄일 수 있을거 같아서
//import static com.test02.*; 해봤더니
//패키지 째로는 안된다.



public class MyTestView {
	
	public static int getMenu() {
		int select = 0;
		
		System.out.println("1. 전체 출력");
		System.out.println("2. 추가");
		System.out.println("3. 수정");
		System.out.println("4. 삭제");
		System.out.println("5. 종료");
		
		System.out.print("번호 선택 : ");
		select = new Scanner(System.in).nextInt();
		
		return select;
	}
	
	

	public static void main(String[] args) {
		
		//변수, 객체 선언
		int no = 0, mno = 0;
		String mname = null, nickname = null;
		Scanner sc = new Scanner(System.in);
		MyTestDao dao = new MyTestDao();
		
		do {
			no = getMenu();
			
			switch(no) {
			
			//전체 출력
			case 1:
				List <MyTest> selectRes = dao.selectAll();
				
				System.out.println("****전체 출력****");
				
				for(MyTest tmp : selectRes) {//selectRes의 값을 tmp에 하나씩 담아 출력한다.
					System.out.println(tmp);
				}
				
				System.out.println();
				break;
				
			//추가
			case 2:
//				System.out.println("추가할 번호 : ");
//				mno = sc.nextInt();
				//디비버로 기본키 auto increment해서 따로 번호 설정할 필요 없다.
				System.out.print("추가할 이름 : ");
				mname = sc.next();
				System.out.print("추가할 별명 : ");
				nickname = sc.next();
				
				MyTest tmp = new MyTest(0, mname, nickname);
				//auto increment하면서 번호 설정 필요 없어졌다.
				//1) 의미없는 값 0을 번호에 넣는다.
				//2) 이름과 별명만 받는 생성자를 새로 만든다
				//3) 기본 생성자로 객체 만든 뒤에, setter 메소드로 각 값을 저장한다.
				//일단 1번 방법 써본다.
				int insertRes = dao.insert(tmp);
				//int insertRes = dao.insert(new MyTest(mno, mname, nickname));
				
				if(insertRes > 0) {
					System.out.println("DB 입력 성공");
				} else {
					System.out.println("DB 입력 실패");
				}
				
				System.out.println();
				break;
				
			//수정
			case 3:
				System.out.print("수정할 사람 번호 : ");
				mno = sc.nextInt();
				System.out.print("수정할 이름 : ");
				mname = sc.next();
				System.out.print("수정할 별명 : ");
				nickname = sc.next();
				
				MyTest update = new MyTest(mno, mname, nickname);
				int updateRes = dao.update(update);
				//입력받은 데이터들을 MyTest 객체에 담아 MyTestDao로 넘겨주고, 결과 값을 받아온다.
				//SELECT문을 제외한 DML문은 모두 정수 값을 리턴 준다.(변경된 row의 개수)
				
				if(updateRes > 0) {
					System.out.println("DB 수정 완료");
				} else {
					System.out.println("DB 수정 실패");
				}
				
				System.out.println();
				break;
				
			//삭제
			case 4:
				System.out.print("삭제할 번호 입력 : ");
				mno = sc.nextInt();
				
				int deleteRes = dao.delete(mno);
				//입력받은 번호를 MyTestDao에 넘겨주고, 그 결과 값을 리턴 받는다.
				
				if(deleteRes > 0) {//dao.delete(mno) > 0도 상관없다.
					System.out.println("DB 데이터 삭제 성공");
				} else {
					System.out.println("DB 데이터 삭제 실패");
				}
				
				System.out.println();
				break;
			//종료
			case 5:
				System.out.println("프로그램 종료");
				break;
			}
			
		}while(no != 5);
		//5입력하면 false 결과값 가지니 반복문 종료.
		
	}

}
