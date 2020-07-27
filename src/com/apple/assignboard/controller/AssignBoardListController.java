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

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.assignboard.dao.AssignBoardDao;
import com.apple.assignboard.domain.AssignBoardVO;
import com.apple.assignment.dao.AssignmentDao;
import com.apple.assignment.domain.AssignmentVO;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.login.controller.LoginController;
import com.apple.main.controller.SideBarController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AssignBoardListController implements Initializable {

	private final Logger LOG = Logger.getLogger(AssignBoardListController.class);

	// ---공유변수------------------------------------------------
	public static ObservableList<Assignment> asObList = null;
	public static AssignBoard ab = null; // selected row of AssignBoard TableView
	public static Assignment as = null; // selected row of Assignment TableView
	public static boolean submit = false; // 과제 제출 여부
	// ---인스턴스 변수------------------------------------------------
	SideBarController sc = new SideBarController(); // 장면전환에 필요
	List<AssignBoardVO> abVOList = null;
	List<AssignmentVO> asVOList = null;
	AssignBoardDao abDao = new AssignBoardDao();
	AssignmentDao asDao = new AssignmentDao();
	// -----------------------------------------------------------

	@FXML
	TableView<AssignBoard> assignBoardTv; // 과제 게시판
	@FXML
	TableView<Assignment> assignmentTv; // 과제 제출 게시판
	@FXML
	TableColumn abTitleC, abContentsC, abStartDateC, abDueDateC, abRegDateC, asTitleC, asContentsC, asIdC, memNameC,
			asRegDateC; // 과제 제출 게시판의 컬럼들
	@FXML
	ComboBox<String> selectedCombo; // 과제 게시판 검색 구분 선택
	@FXML
	TextField searchTf; // 과제 게시판 검색어 입력
	@FXML
	Button newBtn; // 과제 게시판 글 등록 버튼
	@FXML
	BorderPane bp; // 보더팬(화면전환에 필요)
	@FXML
	Label AssignmentListLb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// ---컨트롤러 초기 설정----------------------------------------
		if (LoginController.isAdmin()) {
			newBtn.setVisible(true);
			assignBoardTv.setPlaceholder(new Label("등록된 과제가 없습니다."));
			assignmentTv.setPlaceholder(new Label("제출된 과제가 없습니다."));
		} else {
			newBtn.setVisible(false);
			AssignmentListLb.setText("제출한 과제");
			assignBoardTv.setPlaceholder(new Label("등록된 과제가 없습니다."));
			assignmentTv.setPlaceholder(new Label("과제를 제출하지 않았습니다."));
		}

		// ------------------------------------------------------
		// TableView Data Binding
		List<AssignBoard> list = new AssignBoard().assignBoardList();

		ObservableList<AssignBoard> obList = FXCollections.observableArrayList(list);
		abTitleC.setCellValueFactory(new PropertyValueFactory("title"));
		abContentsC.setCellValueFactory(new PropertyValueFactory("contents"));
		abStartDateC.setCellValueFactory(new PropertyValueFactory("startDate"));
		abStartDateC.setStyle("-fx-alignment: CENTER");
		abDueDateC.setCellValueFactory(new PropertyValueFactory("dueDate"));
		abDueDateC.setStyle("-fx-alignment: CENTER");
		abRegDateC.setCellValueFactory(new PropertyValueFactory("regDate"));
		abRegDateC.setStyle("-fx-alignment: CENTER");

		assignBoardTv.setItems(obList);

		// -Event 감지 및 처리---------------------------------------------------
		// ---[AssignBoard] 선택한 과제에 대해 AssgnNo 검색해서 과제 제출 게시판에 보여주기-------
		assignBoardTv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AssignBoard>() {

			@Override
			public void changed(ObservableValue<? extends AssignBoard> observable, AssignBoard oldValue,
					AssignBoard newValue) {

				// ---과제번호로 과제(assignmentVO) 찾기-----------------
				SearchVO schVO = new SearchVO(10, newValue.getAssgnNo());
				asVOList = asDao.doSelectList(schVO);
				Collections.sort(asVOList);

				// ---유저와 관리자 나눠서 Assignment List 넘겨주기(다른 유저 것 보면
				// 안되니까)----------------------------------------
				if (!LoginController.idPass.equals("ehr")) { // User
					List<Assignment> list = new Assignment().assignmentList(asVOList, LoginController.idPass);
					asObList = FXCollections.observableArrayList(list);
				} else { // admin
					List<Assignment> list = new Assignment().assignmentList(asVOList);
					asObList = FXCollections.observableArrayList(list);
				}

				asTitleC.setCellValueFactory(new PropertyValueFactory("title"));
				asContentsC.setCellValueFactory(new PropertyValueFactory("contents"));
				asIdC.setCellValueFactory(new PropertyValueFactory("memId"));
				asIdC.setStyle("-fx-alignment: CENTER");
				memNameC.setCellValueFactory(new PropertyValueFactory("memName"));
				memNameC.setStyle("-fx-alignment: CENTER");
				asRegDateC.setCellValueFactory(new PropertyValueFactory("regDate"));
				asRegDateC.setStyle("-fx-alignment: CENTER");

				assignmentTv.setItems(asObList);

			}
		});

		// ---[AssignBoard] 과제 게시판 더블클릭시 조회-----------------------------------
		assignBoardTv.setRowFactory(select -> {
			TableRow<AssignBoard> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					ab = row.getItem();

					Stage dialog = new Stage(StageStyle.UTILITY);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initOwner(row.getScene().getWindow());
					dialog.setTitle("과제 확인");

					try {
						Parent parent = FXMLLoader
								.load(getClass().getResource("/com/apple/assignboard/view/AssignBoardSelectOne.fxml"));
						Scene scene = new Scene(parent);
						dialog.setScene(scene);
						dialog.setResizable(false);
						dialog.show();

						// ---[User의 Assignment 처리] 과제 제출 버튼 처리------------------------------------

						Button regUserBtn = (Button) parent.lookup("#regUserBtn");

						regUserBtn.setOnAction(e -> {

							try {
								Parent parent2 = FXMLLoader
										.load(getClass().getResource("/com/apple/assignment/view/AssignmentAdd.fxml"));
								Scene scene2 = new Scene(parent2);
								dialog.setScene(scene2);
								dialog.setResizable(false);
								dialog.show();

								Button asRegBtn = (Button) parent2.lookup("#asRegBtn");
								TextField titleTf = (TextField) parent2.lookup("#titleTf");
								TextArea contentsTa = (TextArea) parent2.lookup("#contentsTa");

								asRegBtn.setOnAction(evnt -> {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									ButtonType buttonTypeOk = new ButtonType("네");
									ButtonType buttonTypeNo = new ButtonType("아니오");
									alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
									alert.setTitle("제출 확인 메시지");
									alert.setHeaderText("과제 제출 확인");
									alert.setContentText("과제를 제출하시겠습니까? 제출 후 수정/삭제가 불가능합니다.");
									alert.show();

									alert.setOnCloseRequest(ev -> {
										if (alert.getResult() == buttonTypeOk) {
											AssignmentVO inVO = new AssignmentVO();
											inVO.setAssgnNo(ab.getAssgnNo());
											inVO.setTitle(titleTf.getText());
											inVO.setContents(contentsTa.getText());
											inVO.setRegDate(new Date());
											inVO.setMemId(LoginController.idPass);

											asDao.doSave(inVO);

											dialog.close();
											sc.setBp(bp);
											sc.assignClick();

										} // --alert if yes

									});// --alert Action

								});// --asRegBtn

							} catch (IOException e1) {
								e1.printStackTrace();
							}
						});

						// ---수정 버튼 처리---------------------------------------------------
						Button updateBtn = (Button) parent.lookup("#updateBtn");

						updateBtn.setOnAction(e -> {

							TextField titleTf = (TextField) parent.lookup("#titleTf");
							TextField startDateTf = (TextField) parent.lookup("#startDateTf");
							TextField dueDateTf = (TextField) parent.lookup("#dueDateTf");
							TextField regDateTf = (TextField) parent.lookup("#regDateTf");
							TextArea contentsTa = (TextArea) parent.lookup("#contentsTa");

							// ---Date format validation check------------------------------------
							if (!validationDate(dueDateTf.getText()) || !validationDate(startDateTf.getText())) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("경고 메시지");
								alert.setHeaderText("에러 확인");
								alert.setContentText("알맞은 날짜 형식으로 입력해주세요 (yyyy-MM-dd)");
								alert.show();
								return;
							} else if (StringUtil.stringToDate2(dueDateTf.getText())
									.before(StringUtil.stringToDate2(startDateTf.getText()))) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("경고 메시지");
								alert.setHeaderText("경고");
								alert.setContentText("과제 마감일은 과제 시작일 이후로 설정해야 합니다.");
								alert.show();
								return;
							} // --validation check if

							// ---수정 기능 처리---------------------------------------------------
							if (!titleTf.getText().equals("") && !startDateTf.getText().equals("")
									&& !dueDateTf.getText().equals("") && !regDateTf.getText().equals("")
									&& !contentsTa.getText().equals("")) {

								Alert alert = new Alert(AlertType.CONFIRMATION);
								ButtonType buttonTypeOk = new ButtonType("네");
								ButtonType buttonTypeNo = new ButtonType("아니오");
								alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
								alert.setTitle("수정 확인 메시지");
								alert.setHeaderText("수정 확인");
								alert.setContentText("글 수정을 완료하시겠습니까?");
								alert.show();

								alert.setOnCloseRequest(ev -> {
									if (alert.getResult() == buttonTypeOk) {
										AssignBoardVO inVO = new AssignBoardVO();
										inVO.setAssgnNo(ab.getAssgnNo());
										inVO.setTitle(titleTf.getText());

										inVO.setContents(contentsTa.getText().replaceAll("\n", "<br/>"));
										inVO.setRegDate(new Date());
										inVO.setStartDate(startDateTf.getText());
										inVO.setDueDate(dueDateTf.getText());

										LOG.debug(abDao);

										if (null != inVO) {
											abDao.doUpdate(inVO);
											LOG.info("update success: " + abDao);
										}

										dialog.close();
										sc.setBp(bp);
										sc.assignClick();
									} // --alert if yes
									return;
								});// --alert Action
							} // --if validation check

							return;
						});// --updateBtn

						// ---삭제 버튼 처리---------------------------------------------------
						Button delBtn = (Button) parent.lookup("#delBtn");
						delBtn.setOnAction(evn -> {

							TextField titleTf = (TextField) parent.lookup("#titleTf");
							TextField startDateTf = (TextField) parent.lookup("#startDateTf");
							TextField dueDateTf = (TextField) parent.lookup("#dueDateTf");
							TextField regDateTf = (TextField) parent.lookup("#regDateTf");
							TextArea contentsTa = (TextArea) parent.lookup("#contentsTa");

							if (!titleTf.getText().isEmpty() && !startDateTf.getText().isEmpty()
									&& !dueDateTf.getText().isEmpty() && !regDateTf.getText().isEmpty()
									&& !contentsTa.getText().isEmpty()) {

								Alert alert = new Alert(AlertType.CONFIRMATION);
								ButtonType buttonTypeOk = new ButtonType("네");
								ButtonType buttonTypeNo = new ButtonType("아니오");
								alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
								alert.setTitle("삭제 확인 메시지");
								alert.setHeaderText("삭제 확인");
								alert.setContentText("글을 삭제하시겠습니까?");
								alert.show();

								alert.setOnCloseRequest(ev -> {
									if (alert.getResult() == buttonTypeOk) {

										// --AssignBoard 삭제----------------------
										AssignBoardVO inVO = new AssignBoardVO(ab.getAssgnNo(), "", "", null, "", "");
										abVOList = abDao.readFile();
										int flag = 0;

										for (AssignBoardVO vo : abVOList) {
											if (vo.getAssgnNo().equals(inVO.getAssgnNo())) {
												flag++;
												abVOList.remove(vo);
												break;
											}
										}
										if (flag != 0) {
											abDao.doSaveFile(abVOList);

										}

										// --Assignment 삭제-------------------------------------

										SearchVO schVO = new SearchVO(10, ab.getAssgnNo());
										List<Assignment> asList = asDao.doSelectList(schVO);

										AssignmentVO asVO = new AssignmentVO(ab.getAssgnNo(), "", "", "", null);

										for (int i = 0; i < asList.size(); i++) {
											asVOList = asDao.readFile();
											flag = 0;
											
											for (AssignmentVO vo : asVOList) {
												if (vo.getAssgnNo().equals(asVO.getAssgnNo())) {
													flag++;
													asVOList.remove(vo);
													break;
												}

											}

											if (flag != 0) {
												asDao.doSaveFile(asVOList);
											}
										}

										dialog.close();
										sc.setBp(bp);
										sc.assignClick();

									} // --alert if yes

								});// --alert Action
							} // --if validation check
						});// --delBtn

					} catch (IOException e) {
						LOG.debug(e.getMessage());
					}

				} // --if

			}// --event
			);// --setOnMouseClick
			return row;
		});

		// ---[Assignment] 과제 제출 게시판 더블클릭시 조회-----------------------------------
		assignmentTv.setRowFactory(select ->

		{
			TableRow<Assignment> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					as = row.getItem();

					Stage dialog = new Stage(StageStyle.UTILITY);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initOwner(row.getScene().getWindow());
					dialog.setTitle("제출 과제 확인");

					try {
						Parent parent = FXMLLoader
								.load(getClass().getResource("/com/apple/assignment/view/AssignmentSelectOne.fxml"));
						Scene scene = new Scene(parent);
						dialog.setScene(scene);
						dialog.setResizable(false);
						dialog.show();

					} catch (IOException e) {
						LOG.debug(e.getMessage());
					}

				} // --if

			}// --event
			);// --setOnMouseClick
			return row;
		});

	}

	// ---검색---------------------------------------------------------------------
	public void searchClick(ActionEvent event) {

		assignBoardTv.getSelectionModel().clearSelection();

		String searchDivStr = null;
		searchDivStr = selectedCombo.getValue();
		String searchWord = null;
		searchWord = searchTf.getText();
		int searchDiv = 0;

		if (searchDivStr.equals("제목")) {
			searchDiv = 10;
		} else if (searchDivStr.equals("내용")) {
			searchDiv = 20;
		}

		SearchVO schVO = new SearchVO(searchDiv, searchWord);
		abVOList = abDao.doSelectListNl(schVO);
		Collections.sort(abVOList);

		List<AssignBoard> list = new AssignBoard().assignBoardList(abVOList);
		if (list.size() == 0) {
			assignBoardTv.setPlaceholder(new Label("검색된 과제가 없습니다."));
		}

		ObservableList<AssignBoard> obList = FXCollections.observableArrayList(list);
		abTitleC.setCellValueFactory(new PropertyValueFactory("title"));
		abContentsC.setCellValueFactory(new PropertyValueFactory("contents"));
		abStartDateC.setCellValueFactory(new PropertyValueFactory("startDate"));
		abStartDateC.setStyle("-fx-alignment: CENTER");
		abDueDateC.setCellValueFactory(new PropertyValueFactory("dueDate"));
		abDueDateC.setStyle("-fx-alignment: CENTER");
		abRegDateC.setCellValueFactory(new PropertyValueFactory("regDate"));
		abRegDateC.setStyle("-fx-alignment: CENTER");

		if (searchDiv == 10 || searchDiv == 20 || !"".equals(searchWord)) {
			assignBoardTv.getSelectionModel().clearSelection();
			assignBoardTv.setItems(obList);
		} else {
		}

	}

	// ---과제 등록 새창-------------------------------------------------------
	public void newClick(ActionEvent event) {

		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(newBtn.getScene().getWindow());
		dialog.setTitle("과제 등록");

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/com/apple/assignboard/view/AssignBoardAdd.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();

			// ---취소 버튼 처리-----------------------------------------------
			Button cancelBtn = (Button) parent.lookup("#cancelBtn");
			cancelBtn.setOnAction(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				ButtonType buttonTypeOk = new ButtonType("네");
				ButtonType buttonTypeNo = new ButtonType("아니오");
				alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
				alert.setTitle("취소 확인 메시지");
				alert.setHeaderText("취소 확인");
				alert.setContentText("등록을 취소하시겠습니까? 취소 시 과제 목록으로 되돌아 갑니다.");
				alert.show();
				alert.setOnCloseRequest(ev -> {
					if (alert.getResult() == buttonTypeOk) {
						dialog.close();
					} else {
						alert.close();
					}

				});// --alert
			});// --cancelBtn

			// ---등록 버튼 처리----------------------------------------------------
			Button regBtn = (Button) parent.lookup("#regBtn");
			regBtn.setOnAction(e -> {

				TextField titleTf = (TextField) parent.lookup("#titleTf");
				TextArea contentsTa = (TextArea) parent.lookup("#contentsTa");
				DatePicker dueDateDp = (DatePicker) parent.lookup("#dueDateDp");
				DatePicker startDateDp = (DatePicker) parent.lookup("#startDateDp");

				if (!titleTf.getText().isEmpty() && !contentsTa.getText().isEmpty() && null != dueDateDp.getValue()
						&& null != startDateDp.getValue()) {
					AssignBoardVO inVO2 = new AssignBoardVO();

					inVO2.setAssgnNo(StringUtil.getPK());
					inVO2.setTitle(titleTf.getText());
					inVO2.setContents(contentsTa.getText());
					inVO2.setDueDate(dueDateDp.getValue().toString());
					inVO2.setStartDate(startDateDp.getValue().toString());
					inVO2.setRegDate(new Date());

					Alert alert = new Alert(AlertType.CONFIRMATION);
					ButtonType buttonTypeOk = new ButtonType("네");
					ButtonType buttonTypeNo = new ButtonType("아니오");
					alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
					alert.setTitle("등록 확인 메시지");
					alert.setHeaderText("등록 확인");
					alert.setContentText("등록하시겠습니까?");
					alert.show();
					alert.setOnCloseRequest(ev -> {
						if (alert.getResult() == buttonTypeOk) {
							abDao.doSave(inVO2);
							dialog.close();
							sc.setBp(bp);
							sc.assignClick();
						} else if (alert.getResult() == buttonTypeNo) {
							alert.close();
						}
					});

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("경고 메시지");
					alert.setHeaderText("경고");
					alert.setContentText("필수 항목(*)을 입력해 주세요.");
					alert.show();
				} // --if validation

			});// --regBtn

		} catch (IOException e) {
			LOG.debug(e.getMessage());
		}
	}

	/**
	 * Date format validation
	 * 
	 * @param String checkDate
	 * @return boolean
	 */
	public boolean validationDate(String checkDate) {

		if (checkDate.length() == 10) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				sdf.setLenient(false);
				sdf.parse(checkDate);

				return true;

			} catch (ParseException e) {
				return false;
			}

		}

		return false;

	}

}
