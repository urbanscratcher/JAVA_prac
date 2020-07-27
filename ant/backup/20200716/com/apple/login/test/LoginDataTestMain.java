package com.apple.login.test;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.apple.cmn.SearchVO;
import com.apple.login.dao.LoginDao;
import com.apple.login.domain.LoginVO;


public class LoginDataTestMain {

	public static void main(String[] args) {
		LoginDao dao = new LoginDao();
		
		int flag = 0;

		String commandInput = "";
		Scanner scanner = new Scanner(System.in);
		Scanner dataScanner = null;

		// console---------------------------------------------------------------
		do {
			System.out.print("( C:등록,R:단건조회,U:수정,D:삭제,L:검색,I:로그인 Q:종료 >>");
			commandInput = scanner.nextLine().trim();
			commandInput = commandInput.toUpperCase().trim();
			
			if (!commandInput.equalsIgnoreCase("C") 
					&& !commandInput.equalsIgnoreCase("Q")
					&& !commandInput.equalsIgnoreCase("R")
					&& !commandInput.equalsIgnoreCase("U")
					&& !commandInput.equalsIgnoreCase("D")
					&& !commandInput.equalsIgnoreCase("L")
					&& !commandInput.equalsIgnoreCase("I")
					) {
                         continue;
			}	
			
				switch (commandInput.toUpperCase())
				{
				case "C":
					LoginVO outVO = dao.getInputData(dataScanner);

					// 동일 Id check
					if (1 == dao.isLoginExists(outVO)) {
						System.out.println("동일한 사람(Id)이 존재합니다.");
						continue;
					}
					// 등록
					int Cflag = dao.doSave(outVO);
					if (Cflag == 1) {
						System.out.println("등록 성공!");
					}
					break;
				
				case"R":
					//입력처리(memId)
					LoginVO inRVO = dao.getInputData(dataScanner);
					//단건조회 호출
					LoginVO outRVO = (LoginVO)dao.doSelectOne(inRVO);
					System.out.println("=================");
					System.out.println("outRVO=" + outRVO);
					System.out.println("=================");
					
					break;
					
				case "U":
					LoginVO inUVO = dao.getInputData(dataScanner);
					int uFlag=dao.doUpdate(inUVO);
					if(uFlag==2) {
						System.out.println("수정성공");
					}
					break;
				case "D":
					LoginVO inVO=dao.getInputData(dataScanner);
					int dFlag =dao.doDelete(inVO);
					if(dFlag ==1) {
						System.out.println("삭제 성공!");
					}
					break;
				case "L":
					SearchVO searchVO = dao.getInputSearchData(dataScanner);
					System.out.println("searchVO:" + searchVO);
					// 검색: Like( REGEXP ): %검색어% -> .*+검색어+.*
					List<LoginVO> list = dao.doSelectList(searchVO);
					Collections.sort(list);

					for (LoginVO vo :list) {
						System.out.println(vo);
					}

					break;
					
				case "I":
					
					
					break;

				case "Q":
					System.out.println("프로그램 종료");
					break;
				}//--switch
				
				
		} while (!commandInput.equalsIgnoreCase("q"));// q/Q 입력하면 종료
		System.out.println("============End==");
	}//--main
}
