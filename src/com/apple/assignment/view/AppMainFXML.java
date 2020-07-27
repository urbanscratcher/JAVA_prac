package com.apple.assignment.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMainFXML extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//fxml loading
		Parent root = FXMLLoader.load(getClass().getResource("AssignmentSelectOne.fxml"));
		
		//Scene에 추가
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("SKYCATLE LMS");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}
