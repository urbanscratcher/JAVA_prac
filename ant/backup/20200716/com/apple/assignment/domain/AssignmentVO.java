package com.apple.assignment.domain;

import java.util.Date;

import com.apple.cmn.DTO;

//-------------------------------------------------
// AssignmentVO
// 회원이 제출한 과제 정보
//-------------------------------------------------
public class AssignmentVO extends DTO implements Comparable {

	// ---Fields--------------------------------------------
	private String assgnNo; 	// 과제번호 (PK)
	private String memId; 		// 회원 ID (PK) //이중 for문으로 데이터 가져오기
	private String title; 		// 제목 : 사용자 입력값
	private String contents; 	// 내용 : 사용자 입력값
	private Date   regDate; 	// 과제 제출일 : 현재
	
	// ---Constructor--------------------------------------

	public AssignmentVO() {
		super();
	}

	public AssignmentVO(String assgnNo, String memId, String title, String contents, Date regDate) {
		super();
		this.assgnNo = assgnNo;
		this.memId = memId;
		this.title = title;
		this.contents = contents;
		this.regDate = regDate;
	}

	// ---getter & setter--------------------------------------

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
	 * @return the memId
	 */
	public String getMemId() {
		return memId;
	}

	/**
	 * @param memId the memId to set
	 */
	public void setMemId(String memId) {
		this.memId = memId;
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
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	// ---toString-------------------------------------------------------
	@Override
	public String toString() {
		return "AssignmentVO [assgnNo=" + assgnNo + ", memId=" + memId + ", title=" + title + ", contents=" + contents
				+ ", regDate=" + regDate + "]";
	}
	
	
	// ---compareTo-------------------------------------------------------
	// 최신 글이 가장 상단에 위치하도록 함
	@Override
	public int compareTo(Object o) {
		AssignmentVO vo = (AssignmentVO) o;
		return vo.getRegDate().compareTo(this.getRegDate());
	}
	
}
