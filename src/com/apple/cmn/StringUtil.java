/**
* <pre>
* com.ehr.cmn
* Class Name : StringUtil.java
* Description:
* Author: ehr
* Since: 2020/07/14
* Version 0.1
* Copyright (c) by H.R.KIM All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/07/14 최초생성
*-----------------------------------------------------
* </pre>
*/

package com.apple.cmn;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

//------------------------------------------------------------------------
// 메서드 설명
// 1. getUUID					 : 랜덤 UUID 생성
// 2-1. formatDate(String)		 : 오늘 날짜/시간을 형식에 맞추어 문자열로 반환
// 2-2. formatDate(Date, String) : Date 객체를 형식에 맺추어 문자열로 반환
// 3. stringToDate(String)		 : 문자열(yyyy-MM-dd HH:mm:ss 형식)을 Date 객체로 변환
// 4. getPK() 					 : PK 값 자동생성 (날짜+시간+UUID)
// 5. getPath(String)			 : config-properties 파일일의 root로 설정된 데이터 파일 경로를 문자열로 반환
//									* 프로젝트에 따라 데이터 경로를 설정하는 path 바꿔주어야 함
// 6. 추가바람
//------------------------------------------------------------------------
public class StringUtil {

	/**
	 * Create UUID (32 byte) : UUID 생성
	 * 
	 * @return String uuidStr
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString().replaceAll("-", "");
		return uuidStr;
	}

	/**
	 * Format Date : 형식을 입력하면 오늘 날짜/시간을 형식에 맞추어 문자열로 반환
	 * 
	 * @param String format ex) yyyyMMdd hh:mm:ss
	 * @return String formatted Date
	 */
	public static String formatDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Format Date (Overloading) : 형식 및 date 객체를 입력하면 date 객체의 날짜/시간을 형식에 맞추어 문자열로
	 * 반환
	 * 
	 * @param date, format
	 * @return String
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Convert String to Date 문자열(yyyy-MM-dd HH:mm:ss 형식) -> Date 객체
	 * 
	 * @param String (yyyy-MM-dd HH:mm:ss)
	 * @return Date
	 */
	public static Date stringToDate(String str) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Convert String to Date 2 문자열(yyyy-MM-dd 형식) -> Date 객체 (set 00:00:00)
	 * 
	 * @param String (yyyy-MM-dd)
	 * @return Date
	 */
	public static Date stringToDate2(String str) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Get the automated primary key : PK 값 자동생성 (날짜+시간+UUID)
	 * 
	 * @return String pk
	 */
	public static String getPK() {
		String pk = StringUtil.formatDate("yyyyMMddhhmmss") + StringUtil.getUUID();
		return pk;
	}

	/**
	 * Get path to create, read, update, and delete a data file : config-property
	 * 파일의 root로 설정된 데이터 파일 경로를 문자열로 반환 프로젝트에 따라 데이터 경로를 설정하는 path 바꿔주어야 함
	 * 
	 * @param String dataFileName ex) assignBoard.csv
	 * @return String path
	 */
	public static String getPath(String dataFileName) {
		Properties prop = PropertiesUtils.readProperties();
		String path = prop.getProperty("root");
		path = String.join(File.separator, path, "data", dataFileName);
		return path;
	}

	/**
	 * Convert Enter : \n <-> <br/>
	 * 
	 * @param source
	 * @param option (1: \n -> <br/>, 2: <br/> -> \n)
	 * @return String
	 */
	public static String replace(String source, int option) {
		// 등록시 : 			\n -> <br/>
		// 조회시 (단건조회) : 	<br/> -> \n
		String resultStr = "";
		if (option == 1) {
			resultStr = source.replaceAll("\n", "<br/>");
		} else if (option == 2) {
			resultStr = source.replaceAll("<br/>", "\n");
		} 

		return resultStr;

	}

}
