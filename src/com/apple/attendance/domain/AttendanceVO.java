package com.apple.attendance.domain;

import com.apple.cmn.DTO;

public class AttendanceVO extends DTO implements Comparable{

	private String memId;	      //회원ID
	private String loginDate; // 날짜별 정보
	private String loginTime;  //시간별 정보
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
	 * @return the loginDate
	 */
	public String getLoginDate() {
		return loginDate;
	}
	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	/**
	 * @return the loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	@Override
	public String toString() {
		return "AttendanceVO [memId=" + memId + ", loginDate=" + loginDate + ", loginTime=" + loginTime + "]";
	}
	
	
	public AttendanceVO(String memId, String loginDate, String loginTime) {
		super();
		this.memId = memId;
		this.loginDate = loginDate;
		this.loginTime = loginTime;
	}
	public AttendanceVO() {
		
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}