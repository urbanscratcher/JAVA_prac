package com.apple.score.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.apple.cmn.DTO;
import com.apple.cmn.StringUtil;
import com.apple.score.domain.ScoreVO;
import com.apple.score.domain.SearchVO;

public class ScoreDao //implements WorkDiv;
{
	private List<ScoreVO> Score;
	private String ADD_FILE = StringUtil.getPath("assignment.csv");
	//private final String ADD_FILE ="D:\\score.csv";
	
	public ScoreDao()
	{
		Score = readFile(ADD_FILE);
		
		for(ScoreVO vo : Score)
		{
			//System.out.println(vo);
		}
	}
	
	public List<ScoreVO> readFile(String ADD_FILE)
	{
		List<ScoreVO> list = new ArrayList<ScoreVO>();
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(ADD_FILE); //기반 스트림
			br = new BufferedReader(fr);//보조 스트림(Buffer)
			String data = "";
			while((data = br.readLine()) != null) //파일 내용의 다음 줄이 null일 때까지
			{
				ScoreVO outVO = null;
				//System.out.println("data : "+data);
				if(!"".equals(data) || data.length() != 0) //빈칸이아니거나 길이가 0이 아닌경우
				{
					String[] ScoreArray = data.split(",");
					outVO = new ScoreVO(ScoreArray[0], 		//memId
												   ScoreArray[1],	 	//pjSeqNo;		
												   ScoreArray[2], 		//assignNo;	
												   ScoreArray[3], 		//score;	
												   ScoreArray[4], 		//scComment;	
												   ScoreArray[5]);		//scSeqNo;	
					list.add(outVO);
				}
				
				
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(null != br)
			{
				try 
				{
					br.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return list ;
		
	}
	
	public int doSave(ScoreVO dto)
	{
		int flag = 0;
		ScoreVO inVO = (ScoreVO)dto;
		
		//this.addressBook 추가
		boolean isSuccess = Score.add(inVO);
		if(isSuccess==true) flag = 1;
		
		for(ScoreVO vo :Score)
		{
			System.out.println(vo);
		}
		//파일에 저장
		int listFlag = doSaveFile(Score);
		
		return flag;
	}
	
	public int doSaveOne(ScoreVO dto)
	{
		int flag = 0;
		ScoreVO inVO = (ScoreVO)dto;
		
		//this.addressBook 추가
		boolean isSuccess = Score.add(inVO);
		if(isSuccess==true) flag = 1;
		
		for(ScoreVO vo :Score)
		{
			System.out.println(vo);
		}
		//파일에 저장
		int listFlag = doSaveFile(Score);
		
		return flag;
	}
	
	public int doUpdate(ScoreVO dto) 
	{
		//수정: 삭제후 등록
		int flag = 0;
		ScoreVO inVO = (ScoreVO) dto;
		//param 값이 존재하는지 확인
		
		flag+=this.doDelete(inVO);
		if(flag>0) 
		{
			flag+=this.doSave(inVO);
		}
		
		return flag;
	}
	
	public int doDelete(ScoreVO dto) 
	{
		int flag = 0; //1성공 0이면 실패
		ScoreVO inVO = (ScoreVO) dto;
		//arraylist삭제는 뒤에서 부터
		for(int i=Score.size()-1;i>=0;i--)
		{
			ScoreVO vsVO = Score.get(i);
			if(vsVO.getScSeqNo().equals(inVO.getScSeqNo()))
			{
				Score.remove(i);
				flag++;
				break;
				
			}
		}
		//파일에서 삭제
				if(flag !=0)
				{
					this.doSaveFile(Score);
				}

				return flag;
	}
	
	public int doSaveFile(List list)
	{
		int cnt = 0;
		FileWriter writer = null; //한글저장된 파일 처리
		BufferedWriter bw = null; //write속도 향상을 위해 사용
		//List -> MemberVO -> 한줄 작성.
		try
		{
			File file = new File(this.ADD_FILE);
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);	
			//------------------------------------------------
			for(int i=0;i<list.size();i++)
			{
				ScoreVO vo = (ScoreVO)list.get(i);
				StringBuffer sb = new StringBuffer();
				String delim = ",";
				sb.append(vo.getMemId()+delim);
				sb.append(vo.getpjSeqNo()+delim);
				sb.append(vo.getAssignNo()+delim);
				sb.append(vo.getScore()+delim);
				sb.append(vo.getScComment()+delim);
				sb.append(vo.getScSeqNo());
				cnt++;
				//마지막 줄에 \n제거
				if( (i+1) != list.size())
				{
					sb.append("\n");
				}
				
				bw.write(sb.toString());
			}
			//------------------------------------------------
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			if(null != bw)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return 0;
	}
	
	public int doSaveFileOne(String string)
	{
		int cnt = 0;
		FileWriter writer = null; //한글저장된 파일 처리
		BufferedWriter bw = null; //write속도 향상을 위해 사용
		String str = null;
		//
		try
		{
			File file = new File(this.ADD_FILE);
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);	
			//------------------------------------------------
			//ScoreVO vo = (ScoreVO)str;
			StringBuffer sb = new StringBuffer();
			
			cnt++;
			//마지막 줄에 \n제거
			
			bw.write(sb.toString());
			
		//------------------------------------------------
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			if(null != bw)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return 0;
	}
	
	//public List<ScoreVO> doSelectList(DTO dto)
	public List<ScoreVO> doSelectList(ScoreVO sVO)
	{
		int cnt = 1;
		List<ScoreVO> list = new ArrayList<ScoreVO>();
		ScoreVO inVO = (ScoreVO)sVO;
		for(int i=0;i<Score.size();i++)
		{
			ScoreVO vo = Score.get(i);
			
			String data = "";
			
			data = vo.getMemId();
			if(data.equals(inVO.getMemId()))
			{
				list.add(vo);
			}
	
			
			
		}
			
		return list;
	}
		
	
	public List<ScoreVO> doSelectListOne(DTO dto)
	{
		int cnt = 1;
		List<ScoreVO> list = new ArrayList<ScoreVO>();
		SearchVO inVO = (SearchVO)dto;
		//10(이름)/20(이메일)
		
		for(int i=0;i<Score.size();i++)
		{
			ScoreVO vo = Score.get(i);
			
			String data = "";
			
			if(vo.getMemId().equals(inVO.getSearchId()))
			{
				data = vo.getScSeqNo();
				if(data.matches(".*"+inVO.getSearchSeq()+".*"))
				{
					list.add(vo);
				}
			}
			
			
		}
			
		return list;
	}
	
	public int isMemberExists(ScoreVO dto)
	{
		ScoreVO vo = (ScoreVO)dto;
		int flag = 0;
		for(int i=0;i<Score.size();i++)
		{
			ScoreVO vsVO = Score.get(i);
			//Param email, list email 비교
			if(vo.getScSeqNo().equals(vsVO.getScSeqNo()))
			{
				flag = 1;
				break;
			}
		}
		
		return flag;
	}
	
	public ScoreVO getInputData(Scanner dataScanner)
	{
		ScoreVO outVO = null;
		dataScanner = new Scanner(System.in);
		System.out.print("처리 입력 데이터 ex) SAMGYOBI \n");
		String inputData = dataScanner.nextLine();
		inputData = inputData.trim();

		if(inputData.indexOf(",") == -1) //쉼표가 없으면 
		{
			outVO = new ScoreVO();
			outVO.setMemId(inputData);
			
		}
		else //모든 데이터 입력
		{
			String[] ScoreArray = inputData.split(",");
			outVO = new ScoreVO(ScoreArray[0],
										   ScoreArray[1],
										   ScoreArray[2],
										   ScoreArray[3],
										   ScoreArray[4],
										   ScoreArray[5]);
		}
		
		return outVO;
	}
	
	public ScoreVO getInputData2(Scanner dataScanner2)
	{
		ScoreVO outVO2 = new ScoreVO();
		dataScanner2 = new Scanner(System.in);
		System.out.print("Comment 작성>> \n");
		String inputData = dataScanner2.nextLine();
		inputData = inputData.trim();
		
		outVO2.setScComment(inputData);
		//System.out.println(outVO2);
		//outVO.setScComment(inputData);

		return outVO2;
	}
	
	public SearchVO getInputSearchData(Scanner dataScanner)
	{
		SearchVO outVO = null;
		dataScanner = new Scanner(System.in);
		//이름, 이메일
		System.out.print("처리 데이터 입력 ex) SAMGYOBI,1 \n>>");
		String inputData = dataScanner.nextLine();
		inputData = inputData.trim();

		
		String[] dataArray = inputData.split(",");
		outVO = new SearchVO(dataArray[0],Integer.parseInt(dataArray[1]));
	
		return outVO;
	}
}
