package com.apple.score.test;

import java.util.List;
import java.util.Scanner;

import com.apple.score.dao.ScoreDao;
import com.apple.score.domain.ScoreVO;
import com.apple.score.domain.SearchVO;

public class ScoreTestMain {
	
	public static void main(String[] args) 
	{
		ScoreDao dao = new ScoreDao();
		ScoreVO inVO = null;
		String input01 = "";
		String input02 = "";
		Scanner scanner = new Scanner(System.in);
		Scanner dataScanner = null;
		do
		{
			System.out.print("A : 등록, U : 유저데이터조회, Q : 종료 >>");
			input01 = scanner.nextLine();
			input01 = input01.toUpperCase().trim();
			if(	!input01.equalsIgnoreCase("A") &&
				!input01.equalsIgnoreCase("U") &&
				!input01.equalsIgnoreCase("Q"))
			{
				System.out.println("Command 다시 입력");
				continue;
			}
			
			switch(input01)
			{
			case "U" :
				ScoreVO uVO = dao.getInputData(dataScanner);
				List<ScoreVO> list = dao.doSelectList(uVO);
				for(ScoreVO vo : list)
				{
					System.out.println(vo);
				}
				break;
				
			case "A" :
				SearchVO aVO = dao.getInputSearchData(dataScanner);
				List<ScoreVO> list2 = dao.doSelectListOne(aVO);
				String seq;
				for(ScoreVO vo : list2)
				{
					seq = vo.getScSeqNo();
					System.out.println(seq);
					System.out.println(vo);
					System.out.print("C : 등록, D : 삭제 , U : 수정, Q : 종료>>");
					input02 = scanner.nextLine();
					input02 = input02.toUpperCase().trim();
					if(	!input02.equalsIgnoreCase("C") &&
						!input02.equalsIgnoreCase("D") &&
						!input02.equalsIgnoreCase("U"))
					{
						System.out.println("Command 다시 입력");
						continue;
					}
					switch(input02)
					{
						case "C" :
							ScoreVO outVO = dao.getInputData2(dataScanner);
							System.out.println(outVO.getScComment());
							outVO.setMemId(vo.getMemId());
							outVO.setpjSeqNo(vo.getpjSeqNo());
							outVO.setAssignNo(vo.getAssignNo());
							outVO.setScore(vo.getScore());
							outVO.setScSeqNo(vo.getScSeqNo());
							System.out.println(outVO);
							//수정
							int uFlag =dao.doUpdate(outVO);
							if (uFlag == 2) {
								System.out.println("수정 성공!");
							}		
							break;
					}
				}
			}
		}
		while(!input01.equalsIgnoreCase("Q")); //q/Q 입력하면 종료
			System.out.println("=====================End=");

	}

}
