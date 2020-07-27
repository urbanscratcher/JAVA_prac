package com.apple.score.view;

import java.util.List;

import com.apple.score.domain.ScoreVO;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AppMainFXML extends Application
{

	private final TableView<ScoreVO> table = new TableView<>();
	private List<ScoreVO> Score;
	private final ObservableList<ScoreVO> data =
			FXCollections.observableArrayList(Score);
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//fxml loading
		Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
		
		//Scene에 추가
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("SKYCATLE LMS");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}

}
