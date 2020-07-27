package com.apple.assignment.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.apple.assignment.domain.AssignmentVO;
import com.apple.cmn.DTO;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.cmn.WorkDiv;

public class AssignmentDao implements WorkDiv {
	// ---Log4j Setting--------------------------------------
	private static Logger LOG = Logger.getLogger(AssignmentDao.class);

	// ---Variables----------------------------------------------
	private List<AssignmentVO> assignment;
	private String ADD_FILE = StringUtil.getPath("assignment.csv");

	// ---Constructor------------------------------------------------------
	public AssignmentDao() {
		super();
		this.assignment = readFile(ADD_FILE);

		// ---[LOG] Print records-------------------------------------------
		for (AssignmentVO vo : assignment) {
			LOG.debug(vo);
		}
	}

	// ---Read File----------------------------------------------
	/**
	 * Read File
	 * 
	 * @param String filePath
	 * @return List
	 */
	public List<AssignmentVO> readFile(String filePath) {
		List<AssignmentVO> list = new ArrayList<AssignmentVO>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String data = "";

			while ((data = br.readLine()) != null) {
				if (!("".equals(data) || data.length() == 0)) {
					String[] dataArray = data.split(",");

					AssignmentVO outVO = new AssignmentVO();
					outVO.setAssgnNo(dataArray[0]);
					outVO.setMemId(dataArray[1]);
					outVO.setTitle(dataArray[2]);
					outVO.setContents(dataArray[3]);
					outVO.setRegDate(StringUtil.stringToDate(dataArray[4]));

					list.add(outVO);
				}
			}

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
	
	/**
	 * Read File (Overloading)
	 *  
	 * @return list
	 */
	public List<AssignmentVO> readFile() {
		return this.readFile(ADD_FILE);
	}

	// --get input VO from Scanner-------------------------------------------------
	/**
	 * Get input data from Scanner
	 * 
	 * @param Scanner dataScanner
	 * @return AssignmentVO
	 */
	public AssignmentVO getInputData(Scanner dataScanner) {
		while (true) {
			AssignmentVO outVO = null;
			dataScanner = new Scanner(System.in);

			System.out.print("처리 데이터 입력(e.g. 1,memId,title,contents,2020-07-14 15:00:30,1)>> ");
			String inputData = dataScanner.nextLine().trim();
			LOG.debug("inputData: " + inputData);

			// ---input all fields-----------------------------------------------------
			if (!("".equals(inputData) || inputData.length() == 0)) {
				String[] dataArray = inputData.split(",");
				outVO = new AssignmentVO();

				if (dataArray.length == 2) {
					outVO.setAssgnNo(dataArray[0]);
					outVO.setMemId(dataArray[1]);
				} else {
					outVO.setAssgnNo(dataArray[0]);
					outVO.setMemId(dataArray[1]);
					outVO.setTitle(dataArray[2]);
					outVO.setContents(dataArray[3]);
					outVO.setRegDate(new Date());

					LOG.debug("input data by scanner: " + outVO);
				}

				return outVO;

			} else {
				System.out.println("데이터를 제대로 입력해주세요.");
				continue;
			}

		}

	}

	/**
	 * get SearchVO from Scanner
	 * 
	 * @param Scanner dataScanner
	 * @return SearchVO
	 */
	public SearchVO getInputSearchData(Scanner dataScanner) {
		while (true) {
			SearchVO outVO = null;
			dataScanner = new Scanner(System.in);

			System.out.print("처리 데이터 입력(e.g. 10(과제번호)/20(회원ID)/30(제목)/40(내용))>> ");
			String inputData = dataScanner.nextLine().trim();
			LOG.debug("inputData: " + inputData);

			if (!("".equals(inputData) || inputData.length() == 0)) {
				String[] dataArray = inputData.split(",");
				outVO = new SearchVO(Integer.parseInt(dataArray[0]), dataArray[1]);
				return outVO;
			} else {
				System.out.println("데이터를 제대로 입력해주세요.");
				continue;
			}

		}

	}

	// ---CRUD-----------------------------------------------------------
	/**
	 * Save data to list
	 * 
	 * @param DTO dto
	 * @return int (1: Success, 0: Fail)
	 */
	@Override
	public int doSave(DTO dto) {
		AssignmentVO inVO = (AssignmentVO) dto;
		int flag = 0;

		boolean isSuccess = assignment.add(inVO);
		if (isSuccess == true) {
			flag = 1;
		}

		LOG.debug("isSuccess: " + isSuccess);
		for (AssignmentVO vo : assignment) {
			LOG.debug(vo);
		}

		int listFlag = doSaveFile(assignment);
		LOG.debug("listFlag: " + listFlag);

		return flag;
	}

	/**
	 * Delete Data
	 * 
	 * @param DTO dto
	 * @return int (1: Success, 0: Fail)
	 */
	@Override
	public int doDelete(DTO dto) {
		int flag = 0;
		AssignmentVO inVO = (AssignmentVO) dto;

		for (int i = assignment.size() - 1; i >= 0; i--) {
			AssignmentVO tmpVO = assignment.get(i);
			if (tmpVO.getAssgnNo().equals(inVO.getAssgnNo()) && tmpVO.getMemId().equals(inVO.getMemId())) {
				LOG.debug("remove:" + assignment.get(i));
				assignment.remove(i);
				flag++;
				break;
			}
		}

		if (flag != 0) {
			this.doSaveFile(assignment);
		}

		return flag;
	}

	/**
	 * Select one record
	 * 
	 * @param DTO dto
	 * @return VO
	 */
	@Override
	public DTO doSelectOne(DTO dto) {
		AssignmentVO inVO = (AssignmentVO) dto;
		AssignmentVO outVO = null;

		// PK 값이 2개라 2개 조건 다 만족해야 함
		for (AssignmentVO vo : assignment) {
			if (vo.getAssgnNo().equals(inVO.getAssgnNo()) && vo.getMemId().equals(inVO.getMemId())) {
				outVO = vo;
				break;
			}
		}

		return outVO;
	}

	/**
	 * Select list by search div, search word
	 * 
	 * @param DTO dto (SearchVO)
	 * @return List
	 */
	@Override
	public List doSelectList(DTO dto) {
		List<AssignmentVO> list = new ArrayList<AssignmentVO>();
		SearchVO inVO = (SearchVO) dto;
		int cnt = 0;

		for (int i = 0; i < assignment.size(); i++) {
			AssignmentVO vo = assignment.get(i);
			String data = "";

			// 처리 데이터 입력(e.g. 10(과제번호)/20(회원ID)/30(제목)/40(내용))
			if (inVO.getSearchDiv() == 10) {
				data = vo.getAssgnNo();
			} else if (inVO.getSearchDiv() == 20) {
				data = vo.getMemId();
			} else if (inVO.getSearchDiv() == 30) {
				data = vo.getTitle();
			} else if (inVO.getSearchDiv() == 40) {
				data = vo.getContents();
			} else {
				System.out.println("구분 입력이 잘못 되었습니다.");
				break;
			}

			if (data.matches(".*" + inVO.getSearchWord() + ".*")) {
				vo.setNo(cnt++);
				list.add(vo);
			}

		}
		System.out.println(cnt + "건 조회되었습니다.");

		return list;
	}

//	/**
//	 * Update (Delete + Create)
//	 * : 화면에서 구현할 예정
//	 * 
//	 * @param DTO dto
//	 * @return int flag (1: Success, 0: Fail)
//	 */
	@Override
	public int doUpdate(DTO dto) {
//		int flag = 0;
//		AssignmentVO inVO = (AssignmentVO) dto;
//
//		if (isMemberExsits(inVO) == 1) {
//			flag += doDelete(inVO);
//			if (flag > 1) {
//				flag += this.doSave(inVO);
//				LOG.debug("Save Success");
//			}
//		}

		return 0;
	}

	/**
	 * Write File
	 * 
	 * @param List
	 * @return int (Num of the written records)
	 */
	@Override
	public int doSaveFile(List list) {
		int cnt = 0;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			File file = new File(this.ADD_FILE);
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			for (int i = 0; i < list.size(); i++) {
				AssignmentVO vo = (AssignmentVO) list.get(i);
				StringBuffer sb = new StringBuffer();
				String delim = ",";
				sb.append(vo.getAssgnNo() + delim);
				sb.append(vo.getMemId() + delim);
				sb.append(vo.getTitle() + delim);
				sb.append(vo.getContents() + delim);
				sb.append(StringUtil.formatDate(vo.getRegDate(), "yyyy-MM-dd HH:mm:ss") + delim);
				cnt++;

				if ((i + 1) != list.size()) {
					sb.append("\n");
				}

				bw.write(sb.toString());
			}

			return cnt;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	// ---Added Methods--------------------------------------------
	/**
	 * Check if VO having equal PK exists
	 * 
	 * @param DTO dto
	 * @return int (1: already exists, 0: not exists)
	 */
	public int isMemberExsits(DTO dto) {
		int flag = 0;
		AssignmentVO inVO = (AssignmentVO) dto;

		for (int i = 0; i < assignment.size(); i++) {
			AssignmentVO vsVO = assignment.get(i);
			if (inVO.getAssgnNo().equals(vsVO.getAssgnNo()) && inVO.getMemId().equals(vsVO.getMemId())) {
				flag = 1;
				LOG.debug("mem exists, flag: " + flag);
				break;
			}
		}
		return flag;
	}



}
