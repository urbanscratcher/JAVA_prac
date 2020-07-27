package com.apple.attendance.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.attendance.dao.AttendanceDao;
import com.apple.attendance.domain.AttendanceVO;
import com.apple.cmn.SearchVO;
import com.apple.login.controller.LoginController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AttendanceController implements Initializable {
	private final Logger LOG = Logger.getLogger(this.getClass());
	@FXML
	TableView<Attendance> tableView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//	LoginVO info =new LoginVO();
//	String info.setMemId();
		List<Attendance> list = new ArrayList<Attendance>();
		List<AttendanceVO> listVO = new ArrayList<AttendanceVO>();
		List<AttendanceVO> listVOUser = new ArrayList<AttendanceVO>();
		AttendanceDao atDao = new AttendanceDao();

		// --Admin/User 파일 다르게 읽기-----------
		if (LoginController.isAdmin()) {
			listVO = atDao.attendanceList;

		} else {
			SearchVO schVo = new SearchVO(10, LoginController.idPass);
			listVO = atDao.doSelectList(schVo);
		}

		// --공통부분 TableView출력--------------------------
		ObservableList<Attendance> memberList = FXCollections.observableArrayList(list);
		for (int i = 0; i < listVO.size(); i++) {
			Attendance at = new Attendance();
			at.setLoginDate(listVO.get(i).getLoginDate());
			at.setMemId(listVO.get(i).getMemId());

			memberList.add(at);
		}

		TableColumn tcmemId = tableView.getColumns().get(0);// 컬럼지정
		tcmemId.setCellValueFactory(new PropertyValueFactory("memId"));// 멤버변수 이름으로 지정
		tcmemId.setStyle("-fx-alignment: CENTER");

		TableColumn tcloginDate = tableView.getColumns().get(1);
		tcloginDate.setCellValueFactory(new PropertyValueFactory("loginDate"));
		tcloginDate.setStyle("-fx-alignment: CENTER");// 정렬

		tableView.setItems(memberList);
		// tableView Event 처리
//		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Attendance>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Attendance> observable, Attendance oldValue,
//					Attendance newValue) {
//				// TODO Auto-generated method stub
//				LOG.debug(newValue.getLoginDate() + "," + newValue.getMemId());
////			if(null !=newValue) {
////				imageView.setImage(new Image("/assets/images/phone/"+newValue.getLoginDate()));
////			}
//
//			}

//		});

	}
}
