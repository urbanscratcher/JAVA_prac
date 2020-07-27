package com.apple.attendance.test;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.apple.attendance.dao.AttendanceDao;
import com.apple.attendance.domain.AttendanceVO;
import com.apple.cmn.SearchVO;

public class AttendanceTest {

	public static void main(String[] args) {
		AttendanceDao dao = new AttendanceDao(); //테스트 , 생성자 호출한다.
		String input01 = "";// Command ex) C   //10. 멤버변수를 가져다 쓴다.
		Scanner scanner = new Scanner(System.in);// Command
		Scanner dataScanner = null; // CRUD 입력 데이터
		do {
			System.out.print("C:등록,D:삭제,U:수정,R:단건조회,L:검색Q:종료>>"); //커맨드 보여주고 커맨드가 맞지 않으면 다시 입력해주세요.
			input01 = scanner.nextLine();
			input01 = input01.toUpperCase().trim();// 앞뒤 빈공간(space)삭제,command는 대문자
			if (!input01.equalsIgnoreCase("Q") && !input01.equalsIgnoreCase("C") && !input01.equalsIgnoreCase("R")
					&& !input01.equalsIgnoreCase("U") && !input01.equalsIgnoreCase("D")
					&& !input01.equalsIgnoreCase("L")) {

				System.out.println("Command를 다시 입력 해주세요.");
				continue;// 반복문 처음으로 이동
			}
			switch (input01) {
			case "L":
				// 입력처리: 검색종류,검색어
				SearchVO searchVO = dao.getInputSearchData(dataScanner);
				System.out.println("searchVO:" + searchVO);
				// 검색: Like( REGEXP ): %검색어% -> .*+검색어+.*
				List<AttendanceVO> list = dao.doSelectList(searchVO);
				Collections.sort(list);


				for (AttendanceVO vo : list) {
					System.out.println(vo);
				}

				break;
			case "R":
				// 입력처리(email)
				AttendanceVO inRVO = dao.getInputData(dataScanner);//데이터 하나 읽어서 보드vo에 저장
				// 단건조회 호출
				AttendanceVO outRVO = (AttendanceVO) dao.doSelectOne(inRVO);
				System.out.println("=================");
				System.out.println("outRVO=" + outRVO);
				System.out.println("=================");

				break;

			case "U":
				AttendanceVO inUVO = dao.getInputData(dataScanner);
				int uFlag = dao.doUpdate(inUVO);
				if (uFlag == 2) {
					System.out.println("수정 성공!");
				}
				break;
				
			case "C"://12. C기능 넣는다. 메소드 정의하러 가야한다.
				AttendanceVO outVO = dao.getInputData(dataScanner);

				 //동일 Seq check
				if (1 == dao.isMemIdExists(outVO)) {
					System.out.println("동일한 사람(Email)이 존재 합니다.");
					continue;
				}
				// 등록
				int flag = dao.doSave(outVO);
				if (flag == 1) {
					System.out.println("등록 성공!");
				}
				break;
			case "D":
				// 입력처리(email)
				AttendanceVO inVO = dao.getInputData(dataScanner);
				// doDelete호출
				int dFlag = dao.doDelete(inVO);
				if (dFlag == 1) {
					System.out.println("삭제 성공!");
				}
				break;
			case "Q"://11. Q기능 넣는다.
				System.out.println("프로그램 종료");
				break;
         }
	} while (!input01.equalsIgnoreCase("Q"));// q/Q 입력하면 종료
	System.out.println("=============End=");
}
}
