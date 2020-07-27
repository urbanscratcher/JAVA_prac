package com.apple.assignboard.dao;

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

import com.apple.assignboard.domain.AssignBoardVO;
import com.apple.cmn.DTO;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.cmn.WorkDiv;

public class AssignBoardDao implements WorkDiv {

	// ---Log4j Setting-------------------------------------------------
	private static Logger LOG = Logger.getLogger(AssignBoardDao.class);

	// ---Variables------------------------------------------------------
	private List<AssignBoardVO> assignBoard;
	private static String ADD_FILE = StringUtil.getPath("assignBoard.csv");

	// ---Constructor----------------------------------------------------
	public AssignBoardDao() {
		assignBoard = readFile(ADD_FILE);

		// ---[LOG] Print records------------------------------------
		for (AssignBoardVO vo : assignBoard) {
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
	public List<AssignBoardVO> readFile(String filePath) {
		List<AssignBoardVO> list = new ArrayList<AssignBoardVO>();
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String data = "";

			while ((data = br.readLine()) != null) {
				if (!("".equals(data) || data.length() == 0)) {
					String[] dataArray = data.split(",");

					AssignBoardVO outVO = new AssignBoardVO();
					outVO.setAssgnNo(dataArray[0]);
					outVO.setTitle(dataArray[1]);
					outVO.setContents(dataArray[2]);
					outVO.setRegDate(StringUtil.stringToDate(dataArray[3]));
					outVO.setStartDate(StringUtil.stringToDate(dataArray[4]));
					outVO.setDueDate(StringUtil.stringToDate(dataArray[5]));

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
	public List<AssignBoardVO> readFile() {
		return this.readFile(ADD_FILE);	
	}

	// ---get input VO from
	// Scanner---------------------------------------------------------
	/**
	 * Get input data from Scanner
	 * 
	 * @param Scanner dataScanner
	 * @return AssignBoardVO
	 */
	public AssignBoardVO getInputData(Scanner dataScanner) {
		while (true) {
			AssignBoardVO outVO = null;
			dataScanner = new Scanner(System.in);

			System.out.println("처리 데이터 입력(pk,제목,내용,등록일,과제시작일,과제마감일)>> ");
			System.out.print(
					"처리 데이터 입력(e.g. 1,title,contents,2020-07-10 15:12:15,2020-07-20 15:12:15,2020-07-27 15:12:15)>> ");
			String inputData = dataScanner.nextLine().trim();
			LOG.debug("inputData: " + inputData);

			// ---input all fields----------------------------------
			if (!("".equals(inputData) || inputData.length() == 0)) {
				outVO = new AssignBoardVO();

				if (inputData.indexOf(",") == -1) {
					outVO.setAssgnNo(inputData);
				} else {
					String[] dataArray = inputData.split(",");
					outVO.setAssgnNo(StringUtil.getPK());
					outVO.setTitle(dataArray[1]);
					outVO.setContents(dataArray[2]);
					outVO.setRegDate(new Date());
					outVO.setStartDate(StringUtil.stringToDate(dataArray[4]));
					outVO.setDueDate(StringUtil.stringToDate(dataArray[5]));

					LOG.debug("input data by scanner: " + outVO);
				}

				return outVO;

			} else {
				System.out.println("데이터를 제대로 입력해주세요.");
				continue;
			}
		}

	}

	// ---Input Search Word----------------------------------------------
	/**
	 * get searched data by input
	 * 
	 * @param dataScanner
	 * @return SearchVO
	 */
	public SearchVO getInputSearchData(Scanner dataScanner) {
		while (true) {
			SearchVO outVO = null;
			dataScanner = new Scanner(System.in);

			System.out.print("처리 데이터 입력(e.g. 10(제목)/20(내용))>> ");
			String inputData = dataScanner.nextLine().trim();
			LOG.debug("inputData: " + inputData);

			if (!("".equals(inputData) || inputData.length() == 0)) {
				// ---input to SearchVO-----------------------------------------
				String[] dataArray = inputData.split(",");
				outVO = new SearchVO(Integer.parseInt(dataArray[0]), dataArray[1]);
				LOG.debug("outVO: " + outVO);
				return outVO;
			} else {
				System.out.println("데이터를 제대로 입력해주세요.");
				continue;
			}
		}

	}

	// ---CRUD----------------------------------------------------------
	/**
	 * Save data to list
	 * 
	 * @param DTO dto
	 * @return int (1: Success, 0: Fail)
	 */
	@Override
	public int doSave(DTO dto) {
		AssignBoardVO inVO = (AssignBoardVO) dto;
		int flag = 0;

		boolean isSuccess = assignBoard.add(inVO);
		if (isSuccess == true) {
			flag = 1;
		}

		LOG.debug("isSuccess: " + isSuccess);
		for (AssignBoardVO vo : assignBoard) {
			LOG.debug(vo);
		}

		int listFlag = doSaveFile(assignBoard);
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
		AssignBoardVO inVO = (AssignBoardVO) dto;

		// delete first one from the last of list
		for (int i = assignBoard.size() - 1; i >= 0; i--) {
			if ((assignBoard.get(i).getAssgnNo()).equals(inVO.getAssgnNo())) {
				assignBoard.remove(i);
				flag++;
				break;
			}
		}

		// delete in File = write current list to File
		if (flag != 0) {
			this.doSaveFile(assignBoard);
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
		AssignBoardVO inVO = (AssignBoardVO) dto; // input data
		AssignBoardVO outVO = null;

		for (AssignBoardVO vo : assignBoard) {
			if (inVO.getAssgnNo().equals(vo.getAssgnNo())) {
				outVO = vo;
				break;
			}
		}

		return outVO;
	}

	/**
	 * select list by search div, search word
	 * 
	 * @param DTO (SearchVO)
	 * @return List
	 */
	@Override
	public List doSelectList(DTO dto) {
		List<AssignBoardVO> list = new ArrayList<AssignBoardVO>();
		SearchVO inVO = (SearchVO) dto;
		int cnt = 0;

		for (int i = 0; i < assignBoard.size(); i++) {
			AssignBoardVO vo = assignBoard.get(i);
			String data = "";

			if (inVO.getSearchDiv() == 10) {
				data = vo.getTitle();
			} else if (inVO.getSearchDiv() == 20) {
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
//		AssignBoardVO inVO = (AssignBoardVO) dto;
//
//		if (isMemberExists(inVO) == 1) {
//			flag += this.doDelete(inVO);
//			LOG.debug("member exists");
//			if (flag > 0) {
//				flag += this.doSave(inVO);
//				LOG.debug("save success");
//			}
//		}
//
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

			// List -> AssignBoardVO -> String 1 line for all elements -> Write
			for (int i = 0; i < list.size(); i++) {
				AssignBoardVO vo = (AssignBoardVO) list.get(i);
				StringBuffer sb = new StringBuffer();
				String delim = ",";
				sb.append(vo.getAssgnNo() + delim);
				sb.append(vo.getTitle() + delim);
				sb.append(vo.getContents() + delim);
				sb.append(StringUtil.formatDate(vo.getRegDate(), "yyyy-MM-dd HH:mm:ss") + delim);
				sb.append(StringUtil.formatDate(vo.getStartDate(), "yyyy-MM-dd HH:mm:ss") + delim);
				sb.append(StringUtil.formatDate(vo.getDueDate(), "yyyy-MM-dd HH:mm:ss"));
				cnt++;

				// add "\n" to each line but last line
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
	public int isMemberExists(DTO dto) {
		AssignBoardVO inVO = (AssignBoardVO) dto;
		int flag = 0;
		for (int i = 0; i < assignBoard.size(); i++) {
			AssignBoardVO vsVO = assignBoard.get(i);
			if (inVO.getAssgnNo().equals(vsVO.getAssgnNo())) {
				flag = 1;
				LOG.debug("mem exists, flag: " + flag);
				break;
			}
		}
		return flag;
	}

}
