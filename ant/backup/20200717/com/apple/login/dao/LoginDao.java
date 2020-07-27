package com.apple.login.dao;

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

import org.apache.log4j.Logger;

import com.apple.cmn.DTO;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.cmn.WorkDiv;
import com.apple.login.domain.LoginVO;

public class LoginDao implements WorkDiv {
	//Log4j
	private static Logger LOG = Logger.getLogger(LoginDao.class);
	private List<LoginVO> memberData;
	private String ADD_FILE = StringUtil.getPath("memberData.csv");
	//private String ADD_FILE = "D:\\eHR20200529\\01_JAVA\\data\\memberData.csv";
	
	public LoginDao() {
     memberData = readFile(ADD_FILE);
     for(LoginVO vo : memberData) {
    	 System.out.println("vo"+vo);
     }
	}
		
	
	
	// 파일을 읽는다.
	// 파일을 한줄(Row) 읽는다.
	// --> 한줄을 -> MemberVO
	// -->List 추가.
	 public List<LoginVO> readFile(String path){
		 List<LoginVO> list = new ArrayList<LoginVO>();
		 FileReader fr = null;
		 BufferedReader br = null;
		 
		 try {
			fr = new FileReader(path);
			br = new BufferedReader(fr); //보조스트림(Buffer)
		 String data = ""; //한줄 String 저장 변수
			while(( data = br.readLine()) !=null) {
				LoginVO outVO = null;
				//LOG.debug("data:"+data);
				String[] loginArray = data.split(",");
				
				outVO = new LoginVO();
				outVO.setMemId(loginArray[0]);
				outVO.setMemName(loginArray[1]);
				outVO.setMemPw(loginArray[2]);
				outVO.setManager(loginArray[3]);
			    list.add(outVO);
			    
			 }//--while end
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != br) {
							try {
								br.close();
							}catch (IOException e) {
								e.printStackTrace();
							}
			}
		}
		return list;
	 }
	 public List<LoginVO> readFile(){
		 
		 return this.readFile(ADD_FILE);
	 }
	 
		public SearchVO getInputSearchData(Scanner dataScanner) {
			SearchVO outVO = null;
			dataScanner = new Scanner(System.in);
			//이름,이메일
			System.out.print("처리 데이터 입력 ex)10(ID)/20(이름), 최현우\n>>");
			String inputData = dataScanner.nextLine();
			inputData = inputData.trim();
			LOG.info("inputData:" + inputData);
			
			
			String[] dataArray = inputData.split(",");
			outVO = new SearchVO(Integer.parseInt(dataArray[0])
					    , dataArray[1]);
			LOG.info("outVO:" + outVO);
			return outVO;
		}
		
		
		
	
	 public LoginVO getInputData(Scanner dataScanner) {
		 LoginVO outVO = null;
		 dataScanner = new Scanner(System.in);
		 System.out.print("처리 데이터 입력 ex)choihw,최현우,0021\n>>");
		 String inputData = dataScanner.nextLine();
		 inputData = inputData.trim();
		 LOG.info("inputData:"+inputData);
		 
		 //memid
		 if(inputData.indexOf(",")== -1) {
			 outVO = new LoginVO();
			 outVO.setMemId(inputData);
		 }else {//모든데이터 입력
			 String[] dataArray = inputData.split(",");
			 outVO = new LoginVO(dataArray[0], dataArray[1], dataArray[2], dataArray[3]);
		 }
		 LOG.info("outVO:" + outVO);
		 
		 return outVO;
	 }
	 
	 
	 public int isMemberExists(DTO dto) {
		 LoginVO vo = (LoginVO)dto;
		 int flag=0;
		 for(int i = 0;i<memberData.size();i++) {
			 LoginVO vsVO = memberData.get(i);
			 
			 if(vo.getMemId().equals(vsVO.getMemId())) {//vo가 입력받은값, VO가 리스트에 있는 모든값 비교
				 flag =1;
				 break;
			 }
		 }
		 return flag;
	 }
	 
	 public int isLoginExists(DTO dto) {
		 LoginVO vo = (LoginVO)dto;
		 int flag=0;
		 for(int i = 0; i<memberData.size();i++) {
			 LoginVO vsVO = memberData.get(i);
			 //param email, list있는 email 비교
			 if(vo.getMemId().equals(vsVO.getMemId()) && (vo.getMemPw().equals(vsVO.getMemPw()))) {
				 flag = 1;
				 break;
			 }
		 }
		return flag;
	 }

	@Override
	public int doSave(DTO dto) {
		int flag = 0;
		LoginVO inVO = (LoginVO)dto;
		
		boolean isSuccess = memberData.add(inVO);
		if(isSuccess == true)
			flag = 1;
		
		LOG.info("isSuccess: "+isSuccess);
		for(LoginVO vo: memberData) {
			LOG.info(vo);
		}
		int listFlag = doSaveFile(memberData);
		
		return 0;
	}

	@Override
	public int doDelete(DTO dto) {
		int flag=0;
		LoginVO inVO = (LoginVO)dto;
		for(int i = memberData.size()-1;i>=0;i++) {
			LoginVO vsVO=memberData.get(i);
			if(vsVO.getMemId().equals(inVO.getMemId())) {
				memberData.remove(i);
				flag++;
				break;
			}
		}
		
		return 0;
	}

	@Override
	public DTO doSelectOne(DTO dto) {
		LoginVO outVO = null;
		LoginVO inVO = (LoginVO)dto;
		for(LoginVO vo:memberData) {
			if(inVO.getMemId().equals(vo.getMemId())) {
				outVO = vo;
				break;
			}
		}
		return null;
	}

	@Override
	public List doSelectList(DTO dto) {
		List<LoginVO> list=new ArrayList<LoginVO>();
		SearchVO  inVO=(SearchVO) dto;
		//10(이름)/20(이메일)
		int cnt =1;
		for(int i=0;i<memberData.size();i++) {
			LoginVO vo =memberData.get(i);
			String data = "";
			
			if(inVO.getSearchDiv()==10) {
				data = vo.getMemName();
			}else if(inVO.getSearchDiv()==20) {
				data = vo.getMemName();
			}
			
			if(data.matches(".*"+inVO.getSearchWord()+".*")) {
				vo.setNo(cnt++);
				list.add(vo);
			}	
		}
		
		return list;
	}

	@Override
	public int doUpdate(DTO dto) {
		int flag=0;
		LoginVO inVO = (LoginVO)dto;
		if(isMemberExists(inVO)==1) {
			flag+=this.doDelete(inVO);
			if(flag>0) {
				flag+=this.doSave(inVO);
			}
		}
		LOG.info("flag="+flag);
		return 0;
	}

	@Override
	public int doSaveFile(List list) {
		int cnt = 0;
		FileWriter writer = null;
		BufferedWriter bw =null;
		
		try {
			File file = new File(this.ADD_FILE);
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			//----------------------------------------------
			for(int i = 0; i<list.size();i++) {
				LoginVO vo = (LoginVO)list.get(i);
				//VO -> choi,최현우,0021
				StringBuffer sb = new StringBuffer();
				String delim = ",";
				sb.append(vo.getMemId()+delim);
				sb.append(vo.getMemName()+delim);
				sb.append(vo.getMemPw());
				cnt++;
				
				if((i+1) != list.size()) {
					sb.append("\n");
				}
				bw.write(sb.toString());
			}
			
		}catch(IOException e) {
			LOG.info(e.getMessage());
		}finally {
			if(null!=bw) {
				try {
					bw.close();
				}catch(IOException e) {
					LOG.info(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	public int doLogin() {
		int flag =0;
		
		LoginVO outVO = null;
		Scanner scanner = new Scanner(System.in);
		String Data= scanner.nextLine();
		String [] dataArray = Data.split(",");
		
		outVO = new LoginVO();
		outVO.setMemId(dataArray[0]);
		outVO.setMemPw(dataArray[1]);
		
		//String info
		
		for(int i = 0; i< memberData.size();i++) {
			LoginVO vsVO = memberData.get(i);
			
			if(outVO.getMemId().equals(vsVO.getMemId()) && (outVO.getMemPw().equals(vsVO.getMemPw()))) {
				System.out.println("돌아라");
				flag = 1;
				break;
			}
		}
		return flag;
	}
}

