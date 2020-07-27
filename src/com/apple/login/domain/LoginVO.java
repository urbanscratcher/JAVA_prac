package com.apple.login.domain;

import com.apple.cmn.DTO;

//------------------------------------------------------
//Login
//apple
//
//------------------------------------------------------

public class LoginVO extends DTO implements Comparable {
	private String memId; // 회원ID
	private String memPw; // 비밀번호
	private String memName; // 회원이름
	private String manager;

	// ---------constructor-------------------------------------------------------------
	public LoginVO(String memId, String memPw, String memName, String manager) {
		super();
		this.memId = memId;
		this.memPw = memPw;
		this.memName = memName;
		this.manager = manager;
	}

	public LoginVO() {

	}

	// ---------getter &
	// setter-------------------------------------------------------------
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
	 * @return the memPw
	 */
	public String getMemPw() {
		return memPw;
	}

	/**
	 * @param memPw the memPw to set
	 */
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}

	/**
	 * @return the memName
	 */
	public String getMemName() {
		return memName;
	}

	/**
	 * @param memName the memName to set
	 */
	public void setMemName(String memName) {
		this.memName = memName;
	}

	/**
	 * @return the manager
	 * @param
	 * @return String
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "LoginVO [memId=" + memId + ", memPw=" + memPw + ", memName=" + memName + ", manager=" + manager + "]";
	}

	// --------compareTo----------------------------------------------------------
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
