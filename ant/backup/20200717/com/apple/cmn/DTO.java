/**
* <pre>
* com.ehr.ex04.scanner
* Class Name : DTO.java
* Description: 모든 Value Object의 Parent
* Author: ehr
* Since: 2020/06/30
* Version 0.1
* Copyright (c) by H.R.KIM All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/06/30 최초생성
*-----------------------------------------------------
* </pre>
*/
package com.apple.cmn;

public class DTO {
	//numbering
	int no;
	
	

	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}

	@Override
	public String toString() {
		return "DTO [no=" + no + "]";
	}
	
	
}
