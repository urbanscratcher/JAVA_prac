package com.apple.login.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.attendance.dao.AttendanceDao;
import com.apple.attendance.domain.AttendanceVO;
import com.apple.cmn.SearchVO;
import com.apple.cmn.StringUtil;
import com.apple.login.dao.LoginDao;
import com.apple.login.domain.LoginVO;
import com.apple.main.controller.SideBarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController implements Initializable {

	private static Logger LOG = Logger.getLogger(LoginController.class);
	@FXML
	PasswordField userPass;
	@FXML
	TextField userId;
	@FXML
	Button loginBtn;
	@FXML
	Button lostBtn;
	@FXML
	BorderPane bp;

	Alert alert = new Alert(AlertType.WARNING);

	private List<LoginVO> memberData;
	private String ADD_FILE = StringUtil.getPath("memberData.csv");

	// ---login 성공시 정보를 받을 변수------------------------------------
	public static String idPass = null;
	static String[] s2 = null;
	public static boolean isSuccessLogin = false;
	// ----------------------------------------------------------

	// String i = null;

	// public void ChangeData() {
	// memberData = readFile(ADD_FILE);
	// }
	// // static활용해서 리턴값
	//
	// public List<LoginVO> readFile(){
	//
	// return this.readFile(ADD_FILE);
	// }

	/**
	 * Admin Check
	 * 
	 * @return boolean
	 */
	public static boolean isAdmin() {
		return idPass.equals("ehr");
	}

//	public List<LoginVO> readFile(String path) {
//		List<LoginVO> list = new ArrayList<LoginVO>();
//		FileReader fr = null;
//		BufferedReader br = null;
//
//		try {
//			fr = new FileReader(path);
//			br = new BufferedReader(fr); // 보조스트림(Buffer)
//			String data = ""; // 한줄 String 저장 변수
//			while ((data = br.readLine()) != null) {
//				LoginVO outVO = null;
//				// LOG.debug("data:"+data);
//				String[] loginArray = data.split(",");
//
//				outVO = new LoginVO();
//				outVO.setMemId(loginArray[0]);
//				outVO.setMemName(loginArray[1]);
//				outVO.setMemPw(loginArray[2]);
//				outVO.setManager(loginArray[3]);
//				list.add(outVO);
//
//			} // --while end
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (null != br) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return list;
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void loginAction(ActionEvent event) {
		String uId = userId.getText();
		String uPass = userPass.getText();

		// Data 입력되어 있지 않으면!
		if (null == uId || "".equals(uId)) {
			alert.setContentText("아이디를 입력 하세요.");
			alert.show();
			// focus 커서위치?
			userId.requestFocus();
			return;
		}
		// Data입력되어 있지 않으면!
		if (null == uPass || "".equals(uPass)) {
			alert.setContentText("비밀번호를 입력 하세요.");
			alert.show();
			// focus 커서위치
			userPass.requestFocus();
			return;
		}

		LoginVO vo = new LoginVO();
		LoginDao dao = new LoginDao();
		vo.setMemId(uId);
		vo.setMemPw(uPass);
//		System.out.println(this.readFile(ADD_FILE));

		int flag = dao.isLoginExists(vo);
		if (flag == 1) {
			idPass = uId;
			isSuccessLogin = true;
			LOG.debug("로그인에 성공하였습니다. : " + idPass + ", " + isSuccessLogin);

			LoginVO outVO = new LoginVO();
			outVO = (LoginVO) dao.doSelectOne(vo);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 확인");
			alert.setHeaderText("로그인 성공");
			alert.setContentText(outVO.getMemName() + "님, " + "SKYCASTLE LMS에 오신 것을 환영합니다!");

			alert.show();

			alert.setOnCloseRequest(e -> {
				// ---[클릭시 오늘 날짜 찍히게] AttendanceVO 처리----------------------
				AttendanceVO atVO = new AttendanceVO();

				// ---중복 날짜 제거----------------------------------------
				AttendanceDao atDao = new AttendanceDao();
				SearchVO schVODate = new SearchVO(20, StringUtil.formatDate("yyyy-MM-dd"));
				SearchVO schVOId = new SearchVO(10, idPass);

				List<AttendanceVO> tmpListDate = atDao.doSelectList(schVODate);
				int tmp = 0;

				for (int i = 0; i < tmpListDate.size(); i++) {
					if (tmpListDate.get(i).getMemId().equals(idPass)) {
						tmp++;
					}

				}

				if (tmp == 0) {
					atVO.setMemId(idPass);
					atVO.setLoginDate(StringUtil.formatDate("yyyy-MM-dd"));
					atVO.setLoginTime(null);
					atDao.doSave(atVO);
				}

				// ----------------------------------------------------------
				SideBarController sc = new SideBarController(); // 장면전환

				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("/com/apple/main/view/SideBarNav.fxml"));
					Scene scene = new Scene(parent);
					Stage window = (Stage) loginBtn.getScene().getWindow();
					window.setScene(scene);
				} catch (IOException e1) {
					LOG.info(e1.getMessage());
				}

				Button logoutBtn = (Button) parent.lookup("#logoutBtn");
				logoutBtn.setText("로그아웃");

				// ---------------------------------------------------------------

			});

		} else if (flag == 0) {
			alert.setTitle("쌍용강북교육센터");
			alert.setHeaderText("아이디 또는 패스워드를 확인하세요.");
			alert.setContentText("확인 후 재로그인 해주세요.");
			isSuccessLogin = false;
			LOG.debug("로그인 실패 : " + idPass + ", " + isSuccessLogin);
			Optional result = alert.showAndWait();
		}

	}

	public void lostAction(ActionEvent event) {
		alert.setTitle("쌍용강북교육센터");
		alert.setHeaderText("아이디 또는 비밀번호 찾기");
		alert.setContentText("아이디 또는 비밀번호를 찾기 원하시면 사무실(02-123-4567)로 연락주시기 바랍니다.");

		Optional result = alert.showAndWait();
		return;
	}

	public void loginHandlerBtn(ActionEvent event) {
		LOG.debug("loginBtn 클릭");
	}

}
