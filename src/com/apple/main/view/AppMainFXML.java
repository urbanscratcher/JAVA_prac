package com.apple.main.view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMainFXML extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SideBarNav.fxml"));
		Scene scene = new Scene(root);

		// 외부 스타일 읽기
		URL url = getClass().getResource("app.css");

		// Scene에 스타일 정보 가져오기
		scene.getStylesheets().add(url.toString());

		primaryStage.setTitle("SKYCATLE LMS");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
