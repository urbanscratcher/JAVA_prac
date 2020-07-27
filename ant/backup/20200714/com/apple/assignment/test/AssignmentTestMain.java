package com.apple.assignment.test;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.apple.assignment.dao.AssignmentDao;
import com.apple.assignment.domain.AssignmentVO;
import com.apple.cmn.SearchVO;

public class AssignmentTestMain {

	public static void main(String[] args) {
		// ---Variables-----------------------------------
		AssignmentDao dao = new AssignmentDao();
		AssignmentVO inVO = null;
		int flag = 0;

		// ---Console input---------------------------------
		String commandInput = "";
		Scanner scanner = new Scanner(System.in);

		Scanner dataScanner = null;

		// ---Console----------------------------------------
		do {
			System.out.print("(C: 등록, R: 단건 조회, D: 삭제, L: 검색, Q: 종료) 입력>> ");
			commandInput = scanner.nextLine().trim();

			if (!commandInput.equalsIgnoreCase("q") && !commandInput.equalsIgnoreCase("c")
					&& !commandInput.equalsIgnoreCase("r")) {
				System.out.println("커맨드를 다시 입력해주세요.");
				continue;
			}

			switch (commandInput.toLowerCase()) {
			case "c":
				inVO = dao.getInputData(dataScanner);

				flag = dao.doSave(inVO);
				if (flag == 1) {
					System.out.println("과제 제출을 성공했습니다.");
				}
				break;

			case "r":
				inVO = dao.getInputData(dataScanner);
				AssignmentVO outVO = (AssignmentVO) dao.doSelectOne(inVO);

				System.out.println("-----------------------데이터를 조회합니다--------------------------");
				System.out.println("outVO: " + outVO);
				System.out.println("------------------------------------------------------------");

			case "d":
				inVO = dao.getInputData(dataScanner);

				flag = dao.doDelete(inVO);

				if (flag == 1) {
					System.out.println("삭제를 성공했습니다.");
				}

			case "l":
				SearchVO schVO = dao.getInputSearchData(dataScanner);
				System.out.println("Search Word: " + schVO);

				List<AssignmentVO> list = dao.doSelectList(schVO);

				Collections.sort(list);
				
				System.out.println("-----------------------데이터를 조회합니다--------------------------");
				for (AssignmentVO vo : list) {
					System.out.println(vo);
				}
				System.out.println("------------------------------------------------------------");
				break;

			case "q":
				System.out.println("프로그램이 종료되었습니다.");
				break;
			}

		} while (!commandInput.equalsIgnoreCase("q"));

		System.out.println("=============System END===========");
	}

}
