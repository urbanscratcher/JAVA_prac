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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class StringUtil {

	/**
	 * UUID : 32 byte
	 * @return String uuidStr
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString().replaceAll("-", "");
		return uuidStr;
	}

	/**
	 * Format Date
	 * @param String format ex) yyyyMMdd hh:mm:ss
	 * @return String formatted Date
	 */
	public static String formatDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * Get the automated primary key
	 * @return String pk
	 */
	public static String getPK() {
		String pk = StringUtil.formatDate("yyyyMMddhhmmss") + StringUtil.getUUID();
		return pk;
	}

	/**
	 * Get path to CRUD a data file
	 * @param String dataFileName ex) assignBoard.csv
	 * @return String path
	 */
	public static String getPath(String dataFileName) {
		Properties prop = PropertiesUtils.readProperties();
		String path = prop.getProperty("root");
		path = String.join(File.separator, path, "data", dataFileName);
		return path;
	}

}
