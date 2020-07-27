package com.apple.attendance.view;


import java.util.Optional;

import com.sun.javafx.scene.control.behavior.OptionalBoolean;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AppMainFXML extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//fxml loading
		Parent root = FXMLLoader.load(getClass().getResource("attendance.fxml"));//연결한 주소를 입력해서연결해준다?
		//root에 로딩이 되었다. 로딩된걸 Scene에 추가해야 한다.
		
		//Scene에 추가
		Scene scene= new Scene(root);
		
		primaryStage.setTitle("fxml loading");// 윈도제목
		primaryStage.setScene(scene);//윈도에 화면추가
		primaryStage.show();//윈도화면 보이게 처리
		
	
	}

	public static void main(String[] args) {
		launch(args);
		

	}

}
