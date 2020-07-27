package com.apple.assignboard.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.apple.assignment.domain.AssignmentVO;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.login.dao.LoginDao;
import com.apple.login.domain.LoginVO;

import javafx.beans.property.SimpleStringProperty;

public class Assignment {

	private static Logger LOG = Logger.getLogger(Assignment.class);

	// ---fields-------------------------------------------------------------------------
	private SimpleStringProperty assgnNo; // 과제번호 (PK)
	private SimpleStringProperty memId; // 회원 ID (PK)
	private SimpleStringProperty memName; // 회원 이름
	private SimpleStringProperty title; // 제목 : 사용자 입력값
	private SimpleStringProperty contents; // 내용 : 사용자 입력값
	private SimpleStringProperty regDate; // 과제 제출일 : 현재

	// ---Constructor----------------------------------------
	public Assignment() {
		assgnNo = new SimpleStringProperty();
		memId = new SimpleStringProperty();
		memName = new SimpleStringProperty();
		title = new SimpleStringProperty();
		contents = new SimpleStringProperty();
		regDate = new SimpleStringProperty();
	}

	public Assignment(String assgnNo, String memId, String memName, String title, String contents, String regDate) {
		this.assgnNo = new SimpleStringProperty(assgnNo);
		this.memId = new SimpleStringProperty(memId);
		this.memName = new SimpleStringProperty(memName);
		this.title = new SimpleStringProperty(title);
		this.contents = new SimpleStringProperty(contents);
		this.regDate = new SimpleStringProperty(regDate);
	}

	// ---Get Searched List---------------------------------
	public List<Assignment> assignmentList(List<AssignmentVO> schList) {
		List<Assignment> list = new ArrayList<Assignment>();
		for (int i = 0; i < schList.size(); i++) {
			Assignment ab = new Assignment();
			LoginDao lgDao = new LoginDao();
			List<LoginVO> logList = new ArrayList<LoginVO>();
			SearchVO schVO2 = new SearchVO(10, schList.get(i).getMemId());
			logList = lgDao.doSelectList(schVO2);
			Collections.sort(logList);

			for (int j = 0; j < logList.size(); j++) {
				ab.setAssgnNo(schList.get(i).getAssgnNo());
				ab.setMemId(schList.get(i).getMemId());
				ab.setContents(schList.get(i).getContents());
				ab.setRegDate(StringUtil.formatDate(schList.get(i).getRegDate(), "yyyy-MM-dd HH:mm:ss"));
				ab.setTitle(schList.get(i).getTitle());
				ab.setMemName(logList.get(j).getMemName());
				list.add(ab);
			}

		}

		return list;
	}

	/**
	 * 유저일 경우 사용하는 메서드 (해당 ID만)
	 * 
	 * @param List<AssignmentVO> schList, String memId
	 * @return
	 */
	public List<Assignment> assignmentList(List<AssignmentVO> schList, String memId) {
		List<Assignment> list = new ArrayList<Assignment>();
		for (int i = 0; i < schList.size(); i++) {
			Assignment ab = new Assignment();
			LoginDao lgDao = new LoginDao();
			List<LoginVO> logList = new ArrayList<LoginVO>();
			SearchVO schVO2 = new SearchVO(10, schList.get(i).getMemId());
			logList = lgDao.doSelectList(schVO2);
			Collections.sort(logList);

			for (int j = 0; j < logList.size(); j++) {
				if (logList.get(j).getMemId().equals(memId)) {
					ab.setAssgnNo(schList.get(i).getAssgnNo());
					ab.setMemId(schList.get(i).getMemId());
					ab.setContents(schList.get(i).getContents());
					ab.setRegDate(StringUtil.formatDate(schList.get(i).getRegDate(), "yyyy-MM-dd HH:mm:ss"));
					ab.setTitle(schList.get(i).getTitle());
					ab.setMemName(logList.get(j).getMemName());
					list.add(ab);

				}

			}

		}

		return list;
	}
	
	
	

	// ---getter & setter----------------------------------------

	/**
	 * @return the memName
	 */
	public String getMemName() {
		return memName.get();
	}

	/**
	 * @param memName the memName to set
	 */
	public void setMemName(String memName) {
		this.memName.set(memName);
	}

	/**
	 * @return the assgnNo
	 */
	public String getAssgnNo() {
		return assgnNo.get();
	}

	/**
	 * @param assgnNo the assgnNo to set
	 */
	public void setAssgnNo(String assgnNo) {
		this.assgnNo.set(assgnNo);
	}

	/**
	 * @return the memId
	 */
	public String getMemId() {
		return memId.get();
	}

	/**
	 * @param memId the memId to set
	 */
	public void setMemId(String memId) {
		this.memId.set(memId);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title.get();
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title.set(title);
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents.getValue();
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents.set(contents.replaceAll("\n", "　"));
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate.get();
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate.set(regDate);
	}

	// ---toString------------------------------------------------
	@Override
	public String toString() {
		return "Assignment [assgnNo=" + assgnNo + ", memId=" + memId + ", memName=" + memName + ", title=" + title
				+ ", contents=" + contents + ", regDate=" + regDate + "]";
	}

}
