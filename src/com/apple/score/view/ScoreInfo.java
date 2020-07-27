package com.apple.score.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.apple.assignboard.controller.Assignment;
import com.apple.cmn.SearchVO;
import com.apple.login.dao.LoginDao;
import com.apple.login.domain.LoginVO;
import com.apple.score.dao.ScoreDao;
import com.apple.score.domain.ScoreVO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreInfo 
{
	private SimpleIntegerProperty iNumber;
	private SimpleStringProperty iClass;
	private SimpleStringProperty iPjName;
	private SimpleStringProperty iDetail;
	
	public ScoreInfo() 
	{
		iNumber = new SimpleIntegerProperty();
		iClass = new SimpleStringProperty();
		iPjName = new SimpleStringProperty();
		iDetail = new SimpleStringProperty();	
	}

	public ScoreInfo(int iNumber, String iClass, 
						  String iPjName, String iDetail) 
	{
		super();
		this.iNumber = new SimpleIntegerProperty(iNumber);
		this.iClass = new SimpleStringProperty(iClass);
		this.iPjName = new SimpleStringProperty(iPjName);
		this.iDetail = new SimpleStringProperty(iDetail);
	}
	
	public List<ScoreInfo> ScoreInfoList()
	{
		ScoreDao scDao = new ScoreDao();
		LoginDao lgDao = new LoginDao();
		//
		String loginMemId = TableController.memId;
		
		List<ScoreVO> list = scDao.readFile();
		Collections.sort(list);
		List<ScoreInfo> sci = new ArrayList<ScoreInfo>();
		
		
		for(int i=0;i<list.size();i++)
		{
			
			ScoreInfo si = new ScoreInfo();
			si.setINumber(i+1);
			si.setIClass("E Class");
			if(list.get(i).getpjSeqNo().equals("1"))
			{
				si.setIPjName("JAVA를 이용한 풀스택 융합과정");
			}
			else si.setIPjName(list.get(i).getpjSeqNo());
			if(list.get(i).getScComment() != null)
			{
				si.setIDetail(" O ");
			}
			else si.setIDetail(" X ");
			sci.add(si);
			break;
			
		}
		
		return sci;
		
	}
	
//	public List<ScoreInfo> ScoreInfoList(List<ScoreVO> scdVO)
//	{
//		ScoreDao scDao = new ScoreDao();
//		List<ScoreVO> list = scdVO;
//		Collections.sort(list);
//		List<ScoreInfo> sci = new ArrayList<ScoreInfo>();
//		
//		for(int i=0;i<list.size();i++)
//		{
//			ScoreInfo si = new ScoreInfo();
//			si.setINumber(i+1);
//			si.setIClass("E Class");
//			if(list.get(i).getpjSeqNo().equals("1"))
//			{
//				si.setIPjName("JAVA를 이용한 풀스택 융합과정");
//			}
//			else si.setIPjName(list.get(i).getpjSeqNo());
//			si.setIScore(list.get(i).getScore());
//			if(list.get(i).getScComment() != null)
//			{
//				si.setIDetail(" O ");
//			}
//			else si.setIDetail(" X ");
//			sci.add(si);
//			
//		}
//		
//		return sci;
//		
//	}
	
	public List<ScoreInfo> ScoreInfoList(List<ScoreVO> scdVO, String memId)
	{
		List<ScoreInfo> list = new ArrayList<ScoreInfo>();
		for (int i = 0; i < scdVO.size(); i++) 
		{
			
			ScoreInfo si = new ScoreInfo();
			LoginDao lgDao = new LoginDao();
			List<LoginVO> logList = new ArrayList<LoginVO>();
			//SearchVO schVO2 = new SearchVO(10, scdVO.get(i).getMemId());
			SearchVO schVO2 = new SearchVO(10,memId);
			logList = lgDao.doSelectList(schVO2);
			Collections.sort(logList);
			

			for(int j=0;j<logList.size();j++)
			{
				si.setINumber(i+1);
				si.setIClass("E Class");
				if(scdVO.get(i).getpjSeqNo().equals("1"))
				{
					si.setIPjName("JAVA를 이용한 풀스택 융합과정");
				}
				else si.setIPjName(scdVO.get(i).getpjSeqNo());
				
				if(scdVO.get(i).getScComment() != null)
				{
					si.setIDetail(" O ");
				}
				else si.setIDetail(" X ");
				
				

				list.add(si);
				
				
				

			}
			break;
		}
		
		return list;
		
	}
	
	public int getINumber() 
	{
		return iNumber.get();
	}

	public void setINumber(int iNumber) 
	{
		this.iNumber.set(iNumber);
	}

	public String getIClass() 
	{
		return iClass.get();
	}

	public void setIClass(String iClass) 
	{
		this.iClass.set(iClass);
	}

	public String getIPjName() 
	{
		return iPjName.get();
	}

	public void setIPjName(String iPjName) 
	{
		this.iPjName.set(iPjName);
	}

	public String getIDetail() 
	{
		return iDetail.get();
		
	}
	
	public void setIDetail(String iDetail) 
	{
		this.iDetail.set(iDetail);
	}
	
	
}
