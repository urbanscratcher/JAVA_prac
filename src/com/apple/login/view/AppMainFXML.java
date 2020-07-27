package com.apple.login.view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AppMainFXML extends Application {
	private static Stage primaryStage;
	
	private void setPrimaryStage(Stage stage) {
		AppMainFXML.primaryStage=stage;
	}
	static public Stage getPrimaryStage() {
		return AppMainFXML.primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		setPrimaryStage(primaryStage);
		//fxml loading
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));//연결한 주소를 입력해서연결해준다?
		//root에 로딩이 되었다. 로딩된걸 Scene에 추가해야 한다.
		
		//Scene에 추가
		Scene scene= new Scene(root);
		
		primaryStage.setTitle("fxml loading");// 윈도제목
		primaryStage.setScene(scene);//윈도에 화면추가
		primaryStage.show();//윈도화면 보이게 처리

		Alert alert = new Alert(AlertType.WARNING);
		
	
	}

	public static void main(String[] args) {
		launch(args);
		
		//ID Password 불일치시 경고창
//		Alert alert = new Alert(AlertType.WARNING);
//		
//			alert.setTitle("쌍용강북교육센터");
//		alert.setHeaderText("아이디 또는 패스워드를 확인하세요.");
//		alert.setContentText("확인 후 재로그인 해주세요.");
//		
//		Optional result = alert.showAndWait();
//		if (result.get() == ButtonType.OK){
//		    // ... user chose OK
//		} else {
//		    // ... user chose CANCEL or closed the dialog
//		}
		
	}

}
