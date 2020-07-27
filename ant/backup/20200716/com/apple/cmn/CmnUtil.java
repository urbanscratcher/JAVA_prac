/**
* <pre>
* com.ehr.cmn
* Class Name : StringUtil.java
* Description:
* Author: Joun
* Since: 2020/07/16
* Version 0.1
* Copyright (c) by APPLE All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/07/14 최초생성
*-----------------------------------------------------
* </pre>
*/

package com.apple.cmn;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//-------------------------------------------------
//메서드 목록
//1. int DateDiff(Date1, Date2)				: date1과 date2 기간(일) 구하기 
//2. int isDateBetween(Date, Date, Date)	: 
//3. int isDateLate(Date,Date)				: 
//
//4. 추가바람
//-------------------------------------------------
public class CmnUtil {
	
	/**
	 * Day Difference in Dates
	 * 
	 * @param Date date1
	 * @param Date date2
	 * @return int (absolute num)
	 */

	public static int DateDiff(Date date1, Date date2) {
		LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int result = (int) ChronoUnit.DAYS.between(localDate1, localDate2);
		result = Math.abs(result);

		return result;
	}

	/**
	 * Check if the target date is between date1 and date2
	 * 
	 * @param Date targetDate
	 * @param Date date1
	 * @param Date date2
	 * @return int (1: is between, -1: is not, 0: date1 equals date2)
	 */
	public static int isDateBetween(Date targetDate, Date date1, Date date2) {
		// [로직 설명]
		// date1이 최신인지 date2가 최신인지 모르는 상황

		// obj1.compareTo(obj2) 이용
		// date1 > date2 : 1 -> date1이 더 최신
		// date1 == date2 : 0
		// date1 < date2 : -1 -> date2가 더 최신
		//
		// 모든 경우의 수 나열하면,
		// 
		// 1) date1 < targetdate < date2
		// date1, targetdate : -1
		// date2, targetdate : 1
		//
		// 2) date1 > targetdate > date2
		// date1, targetdate : 1
		// date2, targetdate : -1
		//
		// -> 곱하면 -1
		// -----------------------------------------
		//
		// 3) date1, date2 < targetdate
		// date1, targetdate : -1
		// date2, targetdate : -1
		//
		// 4) date1, date2 > targetdate
		// date1, targetdate : 1
		// date2, targetdate : 1
		// -> 곱하면 1

		return date1.compareTo(targetDate) * date2.compareTo(targetDate) * (-1);
	}
	
	/**
	 * Check if the target date is late
	 * 
	 * @param Date targetDate
	 * @param Date date1
	 * @param Date date2
	 * @return int (1: is late, -1: is not, 0: date1 equals date2)
	 */
	public static int isDateLate(Date targetDate, Date date) {
		return targetDate.compareTo(date);
	}

}
