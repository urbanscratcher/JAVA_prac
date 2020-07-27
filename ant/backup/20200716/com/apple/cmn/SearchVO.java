/**
* <pre>
* com.ehr.cmn
* Class Name : searchVO.java
* Description:
* Author: ehr
* Since: 2020/07/08
* Version 0.1
* Copyright (c) by H.R.KIM All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/07/08 최초생성
*-----------------------------------------------------
* </pre>
*/

package com.apple.cmn;

public class SearchVO extends DTO {

	/**
	 * 검색 구분 이름 : 10, 이메일: 20
	 */
	private int searchDiv;
	private String searchWord;

	public SearchVO() {
		super();
	}

	public SearchVO(int searchDiv, String searchWord) {
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}

	/**
	 * @return the searchDiv
	 */
	public int getSearchDiv() {
		return searchDiv;
	}

	/**
	 * @param searchDiv the searchDiv to set
	 */
	public void setSearchDiv(int searchDiv) {
		this.searchDiv = searchDiv;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Override
	public String toString() {
		return "searchVO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + "]";
	}

}
