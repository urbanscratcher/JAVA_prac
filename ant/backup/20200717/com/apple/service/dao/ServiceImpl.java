package com.apple.service.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.apple.assignboard.dao.AssignBoardDao;
import com.apple.assignboard.domain.AssignBoardVO;
import com.apple.assignment.dao.AssignmentDao;
import com.apple.assignment.domain.AssignmentVO;
import com.apple.login.dao.LoginDao;
import com.apple.login.domain.LoginVO;

//---------------------------------------------------------------------
// Servie
// : 여러가지 Dao들을 연결해서 메서드 작성
//---------------------------------------------------------------------

public class ServiceImpl {
	// ---Log4j Setting-------------------------------------------------
	private static Logger LOG = Logger.getLogger(ServiceImpl.class);

	// ---Variables------------------------------------------------------
	private static List<AssignBoardVO> assignBoard;
	private static List<AssignmentVO> assignment;
	private static List<LoginVO> memberData;

	AssignBoardDao abDao = new AssignBoardDao();
	AssignmentDao amDao = new AssignmentDao();
	LoginDao lgDao = new LoginDao();

	// ---Constructor----------------------------------------------------
	public ServiceImpl() {
		assignBoard = abDao.readFile();
		assignment = amDao.readFile();
		memberData = lgDao.readFile();
	}
	
	//---Methods---------------------------------------------------------
	
	
	

	// ---Main for test-------------------------------------------------
	public static void main(String[] args) {
		ServiceImpl svc = new ServiceImpl();
	}

}
