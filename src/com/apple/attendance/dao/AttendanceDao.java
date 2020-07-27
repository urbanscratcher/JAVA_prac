package com.apple.attendance.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.apple.attendance.domain.AttendanceVO;
import com.apple.cmn.DTO;
import com.apple.cmn.PropertiesUtils;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.cmn.WorkDiv;

public class AttendanceDao implements WorkDiv {
	private Logger LOG = Logger.getLogger(AttendanceDao.class); // 1. 멤버변수 올린다. log 남기는 기능
	public List<AttendanceVO> attendanceList; // 2. 파일 올려서 리스트에 올려놓고 시작한다.
	private final String SAVE_FILE_NM = "attendance";
	// 3. 파일만들기.(파일만들고 데이터를 붙여 넣는다. csv확장자로
	private String ADD_FILE = ""; // 4. 파일 경로를 읽어온다.

	public AttendanceDao() {
		Properties prop = PropertiesUtils.readProperties();
		String path = prop.getProperty("root"); // 키값이 루트인 파일을 읽어서 가져옴
		path += File.separator + "data" + File.separator + SAVE_FILE_NM;
		LOG.debug("==============");
		LOG.debug("=path=" + path);
		LOG.debug("==============");
		ADD_FILE = path;

		attendanceList = readFile(StringUtil.getPath("attendance.csv"));
		for (AttendanceVO vo : attendanceList) {
			LOG.debug(vo);
		}
	}

	public List<AttendanceVO> readFile(String path) {
		List<AttendanceVO> list = new ArrayList<AttendanceVO>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);// 기반 스트림
			br = new BufferedReader(fr);// 보조스트림(Buffer) 빠르게 해준다.
			String data = "";// 한 줄 String저장 변수
			while ((data = br.readLine()) != null) { // 읽어들이면 된다.
				AttendanceVO outVO = null;
				data = data.trim();
				if ("".equals(data) || data.length() == 0) {
					continue;
				}
				String[] dateArray = data.split(",");
				outVO = new AttendanceVO(dateArray[0], // 7. 개수 맞춰주고
						dateArray[1], dateArray[2]);

				list.add(outVO);
			} // --while end

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public int doSave(DTO dto) {
		int flag = 0;
		AttendanceVO inVO = (AttendanceVO) dto;
		LOG.debug("inVO" + inVO);
		// this.addressBook 추가
		
		
		
		
		boolean isSuccess = this.attendanceList.add(inVO); // 리스트에 추가한다.
		
		
		
		
		
		
		if (isSuccess == true)
			flag = 1; // 성공적으로 작성하면 트루를 받는다.

		LOG.debug("isSuccess:" + isSuccess);// 찍어본다.
		for (AttendanceVO vo : attendanceList) {
			LOG.debug("vo:" + vo);
		}
		int listFlag = doSaveFile(attendanceList);
		LOG.debug(listFlag);

		// 파일에 저장
		return flag;
	}

	@Override
	public int doDelete(DTO dto) {
		int flag = 0;// 1성공, 0이면 실패
		AttendanceVO inVO = (AttendanceVO) dto;

		// arraylist삭제는 뒤에서 부터.
		for (int i = this.attendanceList.size() - 1; i >= 0; i--) {
			AttendanceVO vsVO = attendanceList.get(i);
			if (vsVO.getMemId().equals(inVO.getMemId())) {
				attendanceList.remove(i);
				flag++;
				break;
			}
		}
		// 파일에서 삭제
		if (flag != 0)
			this.doSaveFile(attendanceList);

		return flag;
	}

	@Override
	public DTO doSelectOne(DTO dto) {
		AttendanceVO outVO = null;// Return MemberVO
		AttendanceVO inVO = (AttendanceVO) dto;// param
		for (AttendanceVO vo : this.attendanceList) {// 보드리스트가 데이터를 하나씩 꺼내줌
			if (inVO.getMemId().equals(vo.getMemId())) {
				outVO = vo;
				break;
			}
		}
		return outVO;
	}

	@Override
	public List doSelectList(DTO dto) {
		List<AttendanceVO> list = new ArrayList<AttendanceVO>(); // 조회 한내용 다시 던져주기
		SearchVO inVO = (SearchVO) dto; // 검색조건
		// 10(id)/20(date)
		int cnt = 1;
		for (int i = 0; i < this.attendanceList.size(); i++) {
			AttendanceVO vo = attendanceList.get(i);// 하나 꺼내서 vo에 저장
			String data = "";

			if (inVO.getSearchDiv() == 10) {
				data = vo.getMemId();
			} else if (inVO.getSearchDiv() == 20) {
				data = vo.getLoginDate();
			}

			if (data.matches(inVO.getSearchWord())) {
				vo.setNo(cnt++);
				list.add(vo);// 리스트에 담는다.
			}
		}

		return list;
	}
	
	
	

	@Override
	public int doUpdate(DTO dto) {
		// 수정: 삭제후 등록
		int flag = 0;
		AttendanceVO inVO = (AttendanceVO) dto;
		// param 값이 존재하는지 확인
		if (isMemIdExists(inVO) == 1) {
			flag += this.doDelete(inVO);
			if (flag > 0) {
				flag += this.doSave(inVO);
			}
		}
		LOG.info("flag=" + flag);

		return flag;
	}

	public int isMemIdExists(DTO dto) {
		AttendanceVO vo = (AttendanceVO) dto; // 파람 읽는다.
		int flag = 0;
		for (int i = 0; i < this.attendanceList.size(); i++) {
			AttendanceVO vsVO = attendanceList.get(i);
			// param email, list 있는 email
			if (vo.getMemId().equals(vsVO.getMemId())) {// Seq 똑같은게 있는지 찾는다.
				flag = 1;
				break;
			}
		}

		return flag;
	}

	@Override
	public int doSaveFile(List list) {
		int cnt = 0;
		FileWriter writer = null; // 한글저장된 파일 처리
		BufferedWriter bw = null; // write속도 향상을 위해 사용.

		// List -> BoardVO -> 한줄 적기.리스트를 하나씩 꺼낸다.
		try {
			File file = new File(StringUtil.getPath("attendance.csv"));
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			// ---------------------------------------
			for (int i = 0; i < list.size(); i++) {
				AttendanceVO vo = (AttendanceVO) list.get(i);
				// VO -> 202007150701_001,java 프로젝트_01,첫번째 프로젝트_01,0,20200715,james
				StringBuffer sb = new StringBuffer();
				String delim = ",";
				sb.append(vo.getMemId() + delim);
				sb.append(vo.getLoginDate() + delim);
				sb.append(vo.getLoginTime());
				cnt++;
				// 마지막 줄에 \n제거
				if ((i + 1) != list.size()) {// 제일 마지막줄에는 라인스킵 뺀다. 이거 작성안하면 빈줄이 생긴다.
					sb.append("\n");
				}

				bw.write(sb.toString());
			}

			// ---------------------------------------
		} catch (IOException e) {
			LOG.info(e.getMessage());
		} finally {
			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
					LOG.info(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		return cnt;// 몇라인이 저장됐는지 확인 할 수 있다.
	}

	public SearchVO getInputSearchData(Scanner dataScanner) {// 검색 구분하고 검색어 입력
		SearchVO outVO = null;
		dataScanner = new Scanner(System.in);
		// 이름,이메일
		System.out.print("처리 데이터 입력 ex)10(제목)/20(내용),\n>>");
		String inputData = dataScanner.nextLine();
		inputData = inputData.trim();
		LOG.info("inputData:" + inputData);

		String[] dataArray = inputData.split(",");
		outVO = new SearchVO(Integer.parseInt(dataArray[0]), dataArray[1]);
		LOG.info("outVO:" + outVO);
		return outVO;
	}

	public AttendanceVO getInputData(Scanner dataScanner) {
		AttendanceVO outVO = null;
		dataScanner = new Scanner(System.in);
		System.out.print("처리 데이터 입력 ex)202007150701_001,java 프로젝트_01,첫번째 프로젝트_01,0,20200715,james\n>>");
		String inputData = dataScanner.nextLine();
		inputData = inputData.trim();
		LOG.info("inputData:" + inputData);

		// seq
		if (inputData.indexOf(",") == -1) {// 키값을 읽어 들인다.
			outVO = new AttendanceVO();
			outVO.setMemId(inputData);
		} else {// 모든데이터 입력 , 모든데이터를 읽어 들인다.
			String[] loginArray = inputData.split(",");
			outVO = new AttendanceVO(loginArray[0], // 7. 개수 맞춰주고
					loginArray[1], loginArray[2]);// 생성자
		}
		LOG.info("outVO:" + outVO);

		return outVO;
	}
}
