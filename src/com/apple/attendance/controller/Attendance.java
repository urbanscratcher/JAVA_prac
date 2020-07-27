package com.apple.attendance.controller;

import javafx.beans.property.SimpleStringProperty;

public class Attendance {
	/**
	 * 회원ID
	 */
	private SimpleStringProperty memId;	      //회원ID
	/**
	 * 날짜별정보
	 */
	private SimpleStringProperty loginDate; // 날짜별 정보
	
	public Attendance() {
		memId = new SimpleStringProperty();
		loginDate = new SimpleStringProperty();
	}
	public Attendance(String memId, String loginDate) {
		super();
		this.memId = new SimpleStringProperty(memId);
		this.loginDate = new SimpleStringProperty(loginDate);
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
	 * @return the loginDate
	 */
	public String getLoginDate() {
		return loginDate.get();
	}
	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(String loginDate) {
		this.loginDate.set(loginDate);
	}
	@Override
	public String toString() {
		return "Attendance [memId=" + memId + ", loginDate=" + loginDate + "]";
	}
	
	
}
