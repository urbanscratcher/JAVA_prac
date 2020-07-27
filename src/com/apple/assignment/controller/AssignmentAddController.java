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

package com.apple.assignment.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.assignboard.controller.AssignBoardListController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AssignmentAddController implements Initializable {

	private final Logger LOG = Logger.getLogger(AssignmentAddController.class);

	@FXML
	TextField asTitleTf;
	@FXML
	TextField titleTf;
	@FXML
	TextArea contentsTa;
	@FXML
	Button asRegBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		asTitleTf.setDisable(true);
		asTitleTf.setStyle("-fx-opacity: 1;");

		String assgnNo = AssignBoardListController.ab.getAssgnNo();
		String title = AssignBoardListController.ab.getTitle();
		asTitleTf.setText(title);

	}

}
