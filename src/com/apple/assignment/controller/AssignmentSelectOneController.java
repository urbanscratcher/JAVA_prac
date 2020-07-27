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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AssignmentSelectOneController implements Initializable {

	private final Logger LOG = Logger.getLogger(AssignmentSelectOneController.class);

	@FXML
	TextField memNameTf, titleTf, idTf, regDateTf;
	@FXML
	TextArea contentsTa;
	@FXML
	BorderPane bp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		memNameTf.setDisable(true);
		memNameTf.setStyle("-fx-opacity: 1;");
		titleTf.setDisable(true);
		titleTf.setStyle("-fx-opacity: 1;");
		idTf.setDisable(true);
		idTf.setStyle("-fx-opacity: 1;");
		contentsTa.setEditable(false);
		contentsTa.setFocusTraversable(false);
		regDateTf.setDisable(true);
		regDateTf.setStyle("-fx-opacity: 1;");

		

		titleTf.setText(AssignBoardListController.as.getTitle());
		memNameTf.setText(AssignBoardListController.as.getMemName());
		idTf.setText(AssignBoardListController.as.getMemId());
		regDateTf.setText(AssignBoardListController.as.getRegDate());
		contentsTa.setText(AssignBoardListController.as.getContents());
		contentsTa.setText(contentsTa.getText().replaceAll("　", "\n"));

		

	}

	

}
