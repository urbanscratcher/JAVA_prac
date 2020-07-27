/**
* <pre>
* com.ehr.cmn
* Class Name : PropertiesUtils.java
* Description:
* Author: ehr
* Since: 2020/07/10
* Version 0.1
* Copyright (c) by H.R.KIM All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/07/10 최초생성
*-----------------------------------------------------
* </pre>
*/

package com.apple.cmn;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	public static Properties readProperties() {
		String path = "src/config/ehr_java_dev.properties";
		Properties properties = new Properties();
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		
		try {
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			
			properties.load(bis);
			
//			System.out.println(properties.getProperty("eclass"));
//			System.out.println(properties.getProperty("root"));
//			properties.list(System.out);
			
			
		} catch(FileNotFoundException f) {
			f.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(null!=bis) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return properties;
	}
}
