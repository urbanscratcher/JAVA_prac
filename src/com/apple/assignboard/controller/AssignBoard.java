package com.apple.assignboard.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.apple.assignboard.dao.AssignBoardDao;
import com.apple.assignboard.domain.AssignBoardVO;
import com.apple.cmn.StringUtil;

import javafx.beans.property.SimpleStringProperty;

public class AssignBoard {

	// ---fields-------------------------------------------------------------------------
	private SimpleStringProperty assgnNo; // 과제번호 (PK)
	private SimpleStringProperty title; // 제목 : 사용자 입력값
	private SimpleStringProperty contents; // 내용 : 사용자 입력값
	private SimpleStringProperty regDate; // 작성일 : 현재
	private SimpleStringProperty startDate; // 과제시작일 : 사용자 입력값
	private SimpleStringProperty dueDate; // 과제마감일 : 사용자 입력값

	// ---constructor-------------------------------

	public AssignBoard() {
		assgnNo = new SimpleStringProperty();
		title = new SimpleStringProperty();
		contents = new SimpleStringProperty();
		regDate = new SimpleStringProperty();
		startDate = new SimpleStringProperty();
		dueDate = new SimpleStringProperty();

	}

	public AssignBoard(String assgnNo, String title, String contents, String regDate, String startDate,
			String dueDate) {
		this.assgnNo = new SimpleStringProperty(assgnNo);
		this.title = new SimpleStringProperty(title);
		this.contents = new SimpleStringProperty(contents);
		this.regDate = new SimpleStringProperty(regDate);
		this.startDate = new SimpleStringProperty(startDate);
		this.dueDate = new SimpleStringProperty(dueDate);
	}

	// ---connect to AssignBoardVO--------------------------
	/**
	 * 파일 읽어서 AssignBoard 리스트로 반환
	 * 
	 * @return List<AssignBoard>
	 */
	public List<AssignBoard> assignBoardList() {
		AssignBoardDao abDao = new AssignBoardDao();
		List<AssignBoardVO> list = abDao.readFile();
		Collections.sort(list);
		List<AssignBoard> abList = new ArrayList<AssignBoard>();

		for (int i = 0; i < list.size(); i++) {
			AssignBoard ab = new AssignBoard();
			ab.setAssgnNo(list.get(i).getAssgnNo());
			ab.setContents(list.get(i).getContents());
			ab.setDueDate(list.get(i).getDueDate());
			ab.setRegDate(StringUtil.formatDate(list.get(i).getRegDate(), "yyyy-MM-dd HH:mm:ss"));
			ab.setStartDate(list.get(i).getStartDate());
			ab.setTitle(list.get(i).getTitle());
			abList.add(ab);
		}

		return abList;
	}

	/**
	 * (Overloading) AssignBoardVO 리스트 읽어서 AssignBoard 리스트로 반환
	 * 
	 * @param List<AssignBoardVO> schList
	 * @return List<AssignBoard>
	 */
	public List<AssignBoard> assignBoardList(List<AssignBoardVO> schList) {
		AssignBoardDao abDao = new AssignBoardDao();
		List<AssignBoardVO> list = schList;
		Collections.sort(list);
		List<AssignBoard> abList = new ArrayList<AssignBoard>();

		for (int i = 0; i < list.size(); i++) {
			AssignBoard ab = new AssignBoard();
			ab.setAssgnNo(list.get(i).getAssgnNo());
			ab.setContents(list.get(i).getContents());
			ab.setDueDate(list.get(i).getDueDate());
			ab.setRegDate(StringUtil.formatDate(list.get(i).getRegDate(), "yyyy-MM-dd HH:mm:ss"));
			ab.setStartDate(list.get(i).getStartDate());
			ab.setTitle(list.get(i).getTitle());
			abList.add(ab);
			
		}

		return abList;
	}



	// ---getter and setter-----------------------

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

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate.get();
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate.set(startDate);
	}

	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate.get();
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}

	@Override
	public String toString() {
		return "AssignBoard [assgnNo=" + assgnNo + ", title=" + title + ", contents=" + contents + ", regDate="
				+ regDate + ", startDate=" + startDate + ", dueDate=" + dueDate + "]";
	}

}
