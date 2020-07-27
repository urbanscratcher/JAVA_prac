/**
* <pre>
* com.ehr.cmn
* Class Name : WorkDiv.java
* Description: CRUD(등록,수정,삭제)
* Author: ehr
* Since: 2020/07/01
* Version 0.1
* Copyright (c) by H.R.KIM All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/07/01 최초생성
*-----------------------------------------------------
* </pre>
*/

package com.apple.cmn;
import java.util.*;


public interface WorkDiv {
	/**
	 * 등록
	 * @param dto
	 * @return int(1->등록 성공, 1!=x 실패)	
	 */
	public int doSave(DTO dto);
	
	/**
	 * 삭제
	 * @param dto
	 * @return int(1->등록 성공, 1!=x 실패)	
	 */
	public int doDelete(DTO dto);
	
	/**
	 * 단건 조회
	 * @param dto
	 * @return DTO	
	 */
	public DTO doSelectOne(DTO dto);

	/**
	 * 목록 조회
	 * @param dto
	 * @return List	
	 */
	public List doSelectList(DTO dto);
	
	/**
	 * 수정(삭제, 등록)
	 * @param dto
	 * @return int(1->등록 성공, 1!=x 실패)	
	 */
	public int doUpdate(DTO dto);
	
	/**
	 * 파일 저장
	 * @param list
	 * @return int(1->등록 성공, 1!=x 실패)
	 */
	public int doSaveFile(List list);
	
}
