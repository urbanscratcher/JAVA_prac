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
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.main.controller.SideBarController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AssignBoardAddController implements Initializable {

	private final Logger LOG = Logger.getLogger(AssignBoardAddController.class);

	SideBarController sc = new SideBarController();

	@FXML
	TextField titleTf;
	@FXML
	DatePicker startDateDp, dueDateDp;
	@FXML
	TextArea contentsTa;
	@FXML
	Button regBtn, cancelBtn;
	@FXML
	BorderPane bp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Save
	 * 
	 * @param event
	 */

	/**
	 * Check if due date is before start date
	 */
	public void dueDateCheck() {
		LocalDate dueDate = dueDateDp.getValue();
		LocalDate startDate = startDateDp.getValue();
		if (dueDate.isBefore(startDate)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("경고 메시지");
			alert.setHeaderText("경고");
			alert.setContentText("과제 마감일은 과제 시작일 이후로 설정해야 합니다.");
			alert.show();

			alert.setOnCloseRequest(e -> {
				dueDateDp.show();
			});
		}
	}


}
