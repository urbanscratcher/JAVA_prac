
package com.apple.assignboard.domain;

import java.time.LocalDate;

import com.apple.cmn.DTO;

//-------------------------------------------------
//AssignBoardVO
//관리자 과제 등록, 과제 목록 및 내용 출력, 과제 제출
//-------------------------------------------------
public class AssignBoardVO extends DTO implements Comparable {

	// ---fields-------------------------------------------------------------------------
	private String assgnNo; 		// 과제번호 (PK)
	private String title; 			// 제목 : 사용자 입력값
	private String contents; 		// 내용 : 사용자 입력값
	private LocalDate regDate; 		// 작성일 : 현재
	private LocalDate startDate; 	// 과제시작일 : 사용자 입력값
	private LocalDate dueDate;		// 과제마감일 : 사용자 입력값

	// ---constructor-------------------------------------------------------------------

	public AssignBoardVO() {
	}

	public AssignBoardVO(String assgnNo, String title, String contents, LocalDate regDate, LocalDate startDate,
			LocalDate dueDate) {
		this.assgnNo = assgnNo;
		this.title = title;
		this.contents = contents;
		this.regDate = regDate;
		this.startDate = startDate;
		this.dueDate = dueDate;
	}

	

	// ---getter & setter--------------------------------------------------------

	/**
	 * @return the assgnNo
	 */
	public String getAssgnNo() {
		return assgnNo;
	}

	/**
	 * @param assgnNo the assgnNo to set
	 */
	public void setAssgnNo(String assgnNo) {
		this.assgnNo = assgnNo;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the regDate
	 */
	public LocalDate getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the dueDate
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	// ---toString--------------------------------------------------------------------
	@Override
	public String toString() {
		return "AssignBoardVO [assgnNo=" + assgnNo + ", title=" + title + ", contents=" + contents + ", regDate="
				+ regDate + ", startDate=" + startDate + ", dueDate=" + dueDate + "]";
	}

	// ---compareTo-------------------------------------------------------------------
	@Override
	public int compareTo(Object o) {
		return 0;
	}

}