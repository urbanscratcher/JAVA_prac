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

package com.apple.assignboard.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.apple.assignboard.dao.AssignBoardDao;
import com.apple.assignboard.domain.AssignBoardVO;
import com.apple.cmn.CmnUtil;
import com.apple.cmn.StringUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AssignBoardController implements Initializable {

	@FXML
	TextField titleTf;
	@FXML
	DatePicker startDateDp, dueDateDp;
	@FXML
	TextArea contentsTa;
	@FXML
	Button regBtn, cancelBtn;

	Alert alert = new Alert(AlertType.WARNING);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 이벤트 감지

	}

	public void registAction(ActionEvent event) {
		AssignBoardVO inVO = new AssignBoardVO();
		AssignBoardDao dao = new AssignBoardDao();

		inVO.setAssgnNo(StringUtil.getPK());
		inVO.setTitle(titleTf.getText());
		inVO.setContents(contentsTa.getText());
		inVO.setDueDate(CmnUtil.convertLd(dueDateDp.getValue()));
		inVO.setStartDate(CmnUtil.convertLd(startDateDp.getValue()));
		inVO.setRegDate(new Date());

		int flag = dao.doSave(inVO);
		System.out.println(flag);
	}

	public void cancelAction(ActionEvent event) {
		
	}

}
