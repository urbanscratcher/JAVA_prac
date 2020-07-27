package com.apple.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.login.controller.LoginController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SideBarController implements Initializable {
	private final Logger LOG = Logger.getLogger(SideBarController.class);

	@FXML
	BorderPane bp;
	@FXML
	AnchorPane ap;
	@FXML
	Button logoutBtn;

	/**
	 * @param bp the bp to set
	 */
	public void setBp(BorderPane bp) {
		this.bp = bp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		checkLoginSuccess("/com/apple/attendance/view/attendance.fxml");
	}

	public void attendanceClick(ActionEvent event) {
		checkLoginSuccess("/com/apple/attendance/view/attendance.fxml");

	}

	public void attendanceClick() {
		checkLoginSuccess("/com/apple/attendance/view/attendance.fxml");
	}

	public void assignClick(ActionEvent event) {
		checkLoginSuccess("/com/apple/assignboard/view/AssignBoardList.fxml");
	}

	public void assignClick() {
		checkLoginSuccess("/com/apple/assignboard/view/AssignBoardList.fxml");
	}

	public void scoreClick(ActionEvent event) {
		checkLoginSuccess("/com/apple/score/view/root.fxml");
	}

	public void scoreClick() {
		checkLoginSuccess("/com/apple/score/view/root.fxml");
	}

	/**
	 * Go to Page (Center)
	 * 
	 * @param String page
	 */
	public void loadPage(String page) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(page));
			root.getStylesheets().add("/com/apple/main/view/app.css");
			bp.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * AssignBoardList - 과제 새로 등록 시 화면전환
	 */
	public void newBtn() {
		loadPage("/com/apple/assignboard/view/AssignBoardAdd.fxml");
	}

	/**
	 * AssignmentList - 과제 제출 시 화면전환
	 */
	public void asReg() {
		loadPage("/com/apple/assignboard/view/AssignmentAdd.fxml");
	}

	/**
	 * Logout
	 */
	public void logoutAction() {
		LoginController.isSuccessLogin = false;
		LoginController.idPass = null;
		logoutBtn.setText("로그인");
		checkLoginSuccess("/com/apple/attendance/view/attendance.fxml");
	}

	/**
	 * Check Login Success for redirection
	 * 
	 * @param String page
	 */
	public void checkLoginSuccess(String page) {
		if (!LoginController.isSuccessLogin) {
			loadPage("/com/apple/login/view/login.fxml");

		} else {
			loadPage(page);


		}

	}

}
