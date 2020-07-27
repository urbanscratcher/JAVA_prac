package com.apple.score.domain;

import com.apple.cmn.DTO;

public class SearchVO extends DTO 
{
	private String searchId;
	private int searchSeq;
	
	public SearchVO()
	{
		
	}

	public SearchVO(String searchId, int searchSeq) 
	{
		super();
		this.searchId = searchId;
		this.searchSeq = searchSeq;
	}
	
	public String getSearchId() 
	{
		return searchId;
	}

	public void setSearchId(String searchId) 
	{
		this.searchId = searchId;
	}

	public int getSearchSeq() 
	{
		return searchSeq;
	}

	public void setSearchSeq(int searchSeq) 
	{
		this.searchSeq = searchSeq;
	}

	@Override
	public String toString() 
	{
		return "SearchVO [searchId=" + searchId + ", searchSeq=" + searchSeq + ", "+ 
								 "toString()=" + super.toString() + "]";
	}
	
	
}
