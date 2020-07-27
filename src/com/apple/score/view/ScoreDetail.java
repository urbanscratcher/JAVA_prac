package com.apple.score.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.apple.cmn.SearchVO;
import com.apple.login.dao.LoginDao;
import com.apple.login.domain.LoginVO;
import com.apple.score.dao.ScoreDao;
import com.apple.score.domain.ScoreVO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreDetail 
{
	private SimpleStringProperty dmemId;
	private SimpleStringProperty dPjSeqNo;
	private SimpleStringProperty dAssignNo;
	private SimpleStringProperty dAssignName;
	private SimpleIntegerProperty dScore;
	private SimpleStringProperty dGrade;
	private SimpleStringProperty dScSeqNo;
	private SimpleStringProperty dComment;
	
	
	public ScoreDetail() 
	{
		dmemId = new SimpleStringProperty();
		dPjSeqNo = new SimpleStringProperty();
		dAssignNo = new SimpleStringProperty();
		dAssignName = new SimpleStringProperty();
		dScore = new SimpleIntegerProperty();
		dGrade = new SimpleStringProperty();	
		dScSeqNo = new SimpleStringProperty();
		dComment = new SimpleStringProperty();
	}

	public ScoreDetail(String dmemId, String dPjSeqNo,String dAssignNo, String dAssignName,
							int dScore, String dGrade, String dScSeqNo, String dComment) 
	{
		this.dmemId = new SimpleStringProperty(dmemId);
		this.dPjSeqNo = new SimpleStringProperty(dPjSeqNo);
		this.dAssignNo = new SimpleStringProperty(dAssignNo);
		this.dAssignName = new SimpleStringProperty(dAssignName);
		this.dScore = new SimpleIntegerProperty(dScore);
		this.dGrade = new SimpleStringProperty(dGrade);
		this.dScSeqNo = new SimpleStringProperty(dScSeqNo);
		this.dComment = new SimpleStringProperty(dComment);
	}

	public List<ScoreDetail> ScoreDetailList()
	{
		ScoreDao scDao = new ScoreDao();
		List<ScoreVO> list = scDao.readFile();
		Collections.sort(list);
		List<ScoreDetail> scd = new ArrayList<ScoreDetail>();
		
		for(int i=0;i<list.size();i++)
		{
			ScoreDetail sd = new ScoreDetail();
			sd.setDmemId(list.get(i).getMemId());
			sd.setDPjSeqNo(list.get(i).getpjSeqNo());
			if(list.get(i).getAssignNo().equals("10"))
			{
				sd.setDAssignNo("출석");
				sd.setDAssignName("출석체크");
			}
			else if (list.get(i).getAssignNo().equals("20"))
			{
				sd.setDAssignNo("과제");
				sd.setDAssignName("과제1");
			}
			else
			{
				sd.setDAssignNo(list.get(i).getAssignNo());
				sd.setDAssignName(list.get(i).getAssignNo());
			}
			sd.setDScore(list.get(i).getScore());
			sd.setDGrade(list.get(i).getScSeqNo());
			sd.setdScSeqNo(list.get(i).getScSeqNo());
			sd.setDComment(list.get(i).getScComment());
			scd.add(sd);
			
		}
		//System.out.println(scd.toString());
		return scd;
		
	}
	
//	public List<ScoreDetail> ScoreDetailList(List<ScoreVO> scdVO)
//	{
//		ScoreDao scDao = new ScoreDao();
//		List<ScoreVO> list = scdVO;
//		Collections.sort(list);
//		List<ScoreDetail> scd = new ArrayList<ScoreDetail>();
//		
//		for(int i=0;i<list.size();i++)
//		{
//			ScoreDetail sd = new ScoreDetail();
//			sd.setDmemId(list.get(i).getMemId());
//			sd.setDPjSeqNo(list.get(i).getpjSeqNo());
//			if(list.get(i).getAssignNo().equals("10"))
//			{
//				sd.setDAssignNo("출석");
//				sd.setDAssignName("출석체크");
//			}
//			else if (list.get(i).getAssignNo().equals("20"))
//			{
//				sd.setDAssignNo( "과제");
//				sd.setDAssignName("과제1");
//			}
//			else 
//			{
//				sd.setDAssignNo(list.get(i).getAssignNo());
//				sd.setDAssignName(list.get(i).getAssignNo());
//			}
//			sd.setDScore(list.get(i).getScore());
//			sd.setDGrade(list.get(i).getScore());
//			sd.setdScSeqNo(list.get(i).getScSeqNo());
//			sd.setDComment(list.get(i).getScComment());
//			scd.add(sd);
//			
//		}
//		
//		return scd;
//		
//	}
	
	public List<ScoreDetail> ScoreDetailList(List<ScoreVO> scdVO, String memId)
	{
		List<ScoreDetail> list = new ArrayList<ScoreDetail>();
		for(int i=0; i < scdVO.size();i++)
		{
			ScoreDetail sd = new ScoreDetail();
			LoginDao lgDao = new LoginDao();
			List<LoginVO> logList = new ArrayList<LoginVO>();
			//SearchVO schVO = new SearchVO(10,scdVO.get(i).getMemId());
			SearchVO schVO = new SearchVO(10,memId);
			logList = lgDao.doSelectList(schVO);
			Collections.sort(logList);
			//System.out.println(logList);
			
			for(int j=0;j<logList.size();j++)
			{
				sd.setDmemId(scdVO.get(i).getMemId());
				sd.setDPjSeqNo(scdVO.get(i).getpjSeqNo());
				if(scdVO.get(i).getAssignNo().equals("10"))
				{
					sd.setDAssignNo("출석");
					sd.setDAssignName("출석체크");
				}
				else if (scdVO.get(i).getAssignNo().equals("20"))
				{
					sd.setDAssignNo( "과제");
					sd.setDAssignName("과제1");
				}
				else 
				{
					sd.setDAssignNo(scdVO.get(i).getAssignNo());
					sd.setDAssignName(scdVO.get(i).getAssignNo());
				}
				sd.setDScore(scdVO.get(i).getScore());
				sd.setDGrade(scdVO.get(i).getScSeqNo());
				sd.setdScSeqNo(scdVO.get(i).getScSeqNo());
				sd.setDComment(scdVO.get(i).getScComment());
				list.add(sd);
			}
		}
		
		return list;
		
		
	}

	public String getDmemId() 
	{
		return dmemId.get();
	}

	public void setDmemId(String dmemId) 
	{
		this.dmemId.set(dmemId);
	}
	
	public String getDPjSeqNo() 
	{
		return dPjSeqNo.get();
	}

	public void setDPjSeqNo(String dPjSeqNo) 
	{
		this.dPjSeqNo.set(dPjSeqNo);
	}

	public String getDAssignNo() 
	{
		return dAssignNo.get();
	}

	public void setDAssignNo(String dAssignNo) 
	{
		this.dAssignNo.set(dAssignNo);
	}
	
	public String getDAssignName() 
	{
		return dAssignName.get();
	}

	public void setDAssignName(String dAssignName) 
	{
		this.dAssignName.set(dAssignName);
	}


	public int getDScore() 
	{
		return dScore.get();
	}

	
	public void setDScore(int dScore) 
	{
		this.dScore.set(dScore);
	}

	
	public String getDGrade() 
	{
		return dGrade.get();
	}


	public void setDGrade(String dGrade) 
	{
		this.dGrade.set(dGrade);
	}
	
	public String getdScSeqNo() 
	{
		return dScSeqNo.get();
	}

	public void setdScSeqNo(String dScSeqNo) 
	{
		this.dScSeqNo.set(dScSeqNo);
	}

	public String getDComment() 
	{
		return dComment.get();	
	}

	public void setDComment(String dComment) 
	{
		this.dComment.set(dComment);
	}
	
}
