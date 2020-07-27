/**
* <pre>
* com.ehr.board.controller
* Class Name : BoardController.java
* Description:
* Author: ehr
* Since: 2020/07/17
* Version 0.1
* Copyright (c) by H.R.KIM All right reserved.
* Modification Information
* 수정일   수정자    수정내용
*-----------------------------------------------------
*2020/07/17 최초생성
*-----------------------------------------------------
* </pre>
*/

package com.apple.score.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.apple.score.domain.ScoreVO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScoreController implements Initializable 
{
	@FXML private TableView<ScoreVO> myTableView;
	@FXML private TableColumn<ScoreVO, String> memIdColumn;
	@FXML private TableColumn<ScoreVO, String> pjSeqNoColumn;
	@FXML private TableColumn<ScoreVO, String> AssignNoColumn;
	@FXML private TableColumn<ScoreVO, String> scoreColumn;
	@FXML private TableColumn<ScoreVO, String> scCommentColumn;
	@FXML private TableColumn<ScoreVO, String> scSeqNoColumn;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		//memIdColumn.setCellValueFactory(cellData -> cellData.getValue().getMemId());
		// 이벤트 감지
	}


	
	

}
