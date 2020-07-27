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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.cmn.CmnUtil;
import com.apple.cmn.StringUtil;
import com.apple.login.controller.LoginController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class AssignBoardSelectOneController implements Initializable {

	private final Logger LOG = Logger.getLogger(AssignBoardSelectOneController.class);

	@FXML
	TextField titleTf, startDateTf, dueDateTf, regDateTf;
	@FXML
	TextArea contentsTa;
	@FXML
	BorderPane bp;
	@FXML
	Button updateBtn;
	@FXML
	Button delBtn;
	@FXML
	Button regUserBtn;
	@FXML
	Label btwDateLb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		regUserBtn.setVisible(false);
		regDateTf.setDisable(true);
		regDateTf.setStyle("-fx-opacity: 1;");

		if (LoginController.isAdmin()) {
			regUserBtn.setVisible(false);
			updateBtn.setVisible(true);
			delBtn.setVisible(true);

			contentsTa.setEditable(true);
			contentsTa.setFocusTraversable(true);
			titleTf.setDisable(false);
			startDateTf.setDisable(false);
			dueDateTf.setDisable(false);

		} else {

			if (AssignBoardListController.asObList.isEmpty()) {
				regUserBtn.setVisible(true);
			}

			updateBtn.setVisible(false);
			delBtn.setVisible(false);

			contentsTa.setEditable(false);
			contentsTa.setFocusTraversable(false);
			titleTf.setDisable(true);
			titleTf.setStyle("-fx-opacity: 1;");
			startDateTf.setDisable(true);
			startDateTf.setStyle("-fx-opacity: 1;");
			dueDateTf.setDisable(true);
			dueDateTf.setStyle("-fx-opacity: 1;");

		}

		// ----------------------------------------
		titleTf.setText(AssignBoardListController.ab.getTitle());
		startDateTf.setText(AssignBoardListController.ab.getStartDate());
		dueDateTf.setText(AssignBoardListController.ab.getDueDate());
		regDateTf.setText(AssignBoardListController.ab.getRegDate());
		contentsTa.setText(AssignBoardListController.ab.getContents());
		contentsTa.setText(contentsTa.getText().replaceAll("　", "\n"));

		// ---D-Day-------------------------------------------------------------
		Date date1 = CmnUtil.convertLd(LocalDate.now());
		Date date2 = StringUtil.stringToDate2(AssignBoardListController.ab.getDueDate());

		LOG.debug(date1 + ", " + date2);

		if (date1.equals(date2)) {
			btwDateLb.setText("D-DAY");
		} else if (date1.after(date2)) {
			btwDateLb.setText("이미 마감된 과제입니다.");
			regUserBtn.setVisible(false);
		} else {
			int diff = CmnUtil.dateDiff(date1, date2);
			btwDateLb.setText("D-" + diff);
		}

	}

}
