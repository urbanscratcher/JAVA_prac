package com.apple.score.view;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.apple.assignboard.controller.AssignBoardListController;
import com.apple.assignment.dao.AssignmentDao;
import com.apple.assignment.domain.AssignmentVO;
import com.apple.attendance.dao.AttendanceDao;
import com.apple.attendance.domain.AttendanceVO;
import com.apple.cmn.SearchVO;
import com.apple.login.controller.LoginController;
import com.apple.main.controller.SideBarController;
import com.apple.score.dao.ScoreDao;
import com.apple.score.domain.ScoreVO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TableController implements Initializable 
{
	private final Logger LOG = Logger.getLogger(AssignBoardListController.class);

	
	public static ScoreDetail sd = null;
	public static ScoreInfo si = null;
	public static ObservableList<ScoreInfo> siObList = null;
	public static ObservableList<ScoreDetail> sdObList = null;
	public static String memId = null;
	//public static String memId = "SAMGYOBI";
	ScoreDao sdDao = new ScoreDao();
	SideBarController sc = new SideBarController();
	String amDaoTot = "4";
 	String adDaoTot = "10";
 	
	float atdScore, asgScore, totScore;
	
	List<ScoreVO> scVOList = null;
	
	@FXML AnchorPane ap;
	@FXML BorderPane bp;
	@FXML TableView<ScoreInfo> tableView;
	@FXML TableColumn inNumber, inClass, inPjName, inDetail;
	@FXML TableView<ScoreDetail> tableDetailView;
	@FXML TableColumn deMemIdC, deAssignNoC, deAssignNameC, 
								 deScoreC, deGradeC, deCommentC;
	@FXML Button btnCommentCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		ScoreVO scoreVO = new ScoreVO();
		scoreVO.setMemId(LoginController.idPass);

		//scVOList = new ArrayList<ScoreVO>();
		scVOList = sdDao.doSelectList(scoreVO);
		Collections.sort(scVOList);
		
		if(!LoginController.idPass.equals("ehr"))
		{
			List<ScoreInfo> listInfo = new ScoreInfo().ScoreInfoList(scVOList, LoginController.idPass);
			siObList =  FXCollections.observableArrayList(listInfo);
		}
		else
		{
			List<ScoreInfo> listInfo = new ScoreInfo().ScoreInfoList();
			siObList =  FXCollections.observableArrayList(listInfo);
		}
		
		if(!LoginController.idPass.equals("ehr"))
		{
			List<ScoreDetail> listDetail = new ScoreDetail().ScoreDetailList(scVOList, LoginController.idPass);
			sdObList =  FXCollections.observableArrayList(listDetail);
		}
		else
		{
			List<ScoreDetail> listDetail = new ScoreDetail().ScoreDetailList();
			sdObList =  FXCollections.observableArrayList(listDetail);
		}
		
		
//		List<ScoreInfo> listInfo = new ScoreInfo().ScoreInfoList();
//		List<ScoreDetail> listDetail = new ScoreDetail().ScoreDetailList();		
		
		
		

		
		
		//-------------------------TableView---------------------------------------
//		ObservableList<ScoreInfo> scoreListInfo =  FXCollections.observableArrayList(listInfo);
		
		inNumber.setCellValueFactory(new PropertyValueFactory("iNumber"));
		inClass.setCellValueFactory(new PropertyValueFactory("iClass"));
		inPjName.setCellValueFactory(new PropertyValueFactory("iPjName"));
		inDetail.setCellValueFactory(new PropertyValueFactory("iDetail"));
		
//		tableView.setItems(scoreListInfo);
		tableView.setItems(siObList);
		
		tableView.setRowFactory(select ->
		{
			TableRow<ScoreInfo> row = new TableRow<>();
			row.setOnMouseClicked(event1 ->
			{
				if(event1.getClickCount() == 2 && (!row.isEmpty()))
	            {
					tableDetailView.setVisible(true);
	            }
			});
			return row;
		});
		
		//------------------------TableDetailView----------------------------------
		
//		ObservableList<ScoreDetail> scoreListDetail =  FXCollections.observableArrayList(listDetail);

		deMemIdC.setCellValueFactory(new PropertyValueFactory("dmemId"));
		deAssignNoC.setCellValueFactory(new PropertyValueFactory("dAssignNo"));
		deAssignNameC.setCellValueFactory(new PropertyValueFactory("dAssignName"));
		deScoreC.setCellValueFactory(new PropertyValueFactory("dScore"));
		deGradeC.setCellValueFactory(new PropertyValueFactory("dGrade"));
		deCommentC.setCellValueFactory(new PropertyValueFactory("dComment"));
		
//		tableDetailView.setItems(scoreListDetail);
		tableDetailView.setItems(sdObList);
		
		tableDetailView.setRowFactory(select -> 
	      {
	         TableRow<ScoreDetail> row = new TableRow<>();


	         row.setOnMouseClicked(event2-> 
	         {
	        	if(tableDetailView.getSelectionModel().getSelectedIndex()<0) return;
	        	
	        	//
					TablePosition pos = tableDetailView.getSelectionModel().getSelectedCells().get(0);
					int tdrow = pos.getRow();
					int tdcol = pos.getColumn();
					//System.out.println("tdrow : "+tdrow);
					//System.out.println("tdcol : "+tdcol);
		        //
					

				if(tdcol == 5)
				{
					if(event2.getClickCount() == 2 && (!row.isEmpty()))
		            {
		               sd = row.getItem();
		               
		               
		               
		               Stage dialog = new Stage(StageStyle.UTILITY);
		               dialog.initModality(Modality.WINDOW_MODAL);
		               dialog.initOwner(row.getScene().getWindow());
		               dialog.setTitle("코멘트 입력");
		               try
		               {
		                  Parent parent = FXMLLoader
		                        .load(getClass().getResource("inputComment.fxml"));
		                  Scene scene = new Scene(parent);
		                  dialog.setScene(scene);
		                  dialog.setResizable(false);
		                  dialog.show();
		                  
		                  
		                  
		                  TextArea commentTa = (TextArea)parent.lookup("#commentTa");
		                  commentTa.setText(sd.getDComment());
		                  
		         
		                  
		                  //저장
		                  Button btnCommentUpdate = (Button)parent.lookup("#btnCommentUpdate");
		                  if(!LoginController.idPass.equals("ehr"))
		                  {
		                	  btnCommentUpdate.setVisible(false);
		                  }
		               
		                  btnCommentUpdate.setOnAction(e ->
		                  {
		                     ScoreVO scVO = new ScoreVO();
		                     scVO.setMemId(sd.getDmemId());
		                     scVO.setpjSeqNo(sd.getDPjSeqNo());
		                     if(sd.getDAssignNo().equals("출석"))
		                     {
		                    	 scVO.setAssignNo("10");
		                     }
		                     else scVO.setAssignNo("20");
		                     //scVO.setAssignNo(sd.getDAssignNo());
		                     scVO.setScore(sd.getDScore());
		                     scVO.setScSeqNo(sd.getdScSeqNo());
		                     scVO.setScComment(commentTa.getText());
		                  
		                     
		                     //System.out.println(scVO);
		                     
		                     
		                     sdDao.doUpdate(scVO);
		                     
		                     
		                     dialog.close();
		                     sc.setBp(bp);
		                     sc.scoreClick();
		                     
		         
		                     
		                     
		                     
		                  });
		                  //취소
		                  Button btnCommentCancel = (Button)parent.lookup("#btnCommentCancel");
		                  if(!LoginController.idPass.equals("ehr"))
		                  {
		                	  btnCommentCancel.setVisible(false);
		                  }
						   
		                  btnCommentCancel.setOnAction(e ->
						   {
								dialog.close();
						   });
		                  
		                  
		               }
		               catch (IOException e) 
		               {
		                  e.printStackTrace();
		               }
		               
		            }
				}
				else if(tdcol == 3)
				{
					if(event2.getClickCount() == 2 && (!row.isEmpty()))
		            {
						sd = row.getItem();
		               
		                String id;
		                if(!LoginController.idPass.equals("ehr")) 
					 	{
		                	id=LoginController.idPass;
					 	}
					 	else
					 	{
					 		id=sd.getDmemId();
					 	}
					 	
		                //System.out.println("id : "+id);
		                
					 	
					 	SearchVO schVO2 = new SearchVO(10,id);
					 	List<AttendanceVO> adDao = new AttendanceDao().doSelectList(schVO2);
					 	String adDaoCnt = String.valueOf(adDao.size());
					 	//System.out.println("adDaoCnt :"+adDaoCnt);
					 	
					 	SearchVO schVO = new SearchVO(20,id);
					 	List<AssignmentVO> amDao = new AssignmentDao().doSelectList(schVO);
					 	String amDaoCnt = String.valueOf(amDao.size());
					 	
		               
		               
		               
		               Stage dialog = new Stage(StageStyle.UTILITY);
		               dialog.initModality(Modality.WINDOW_MODAL);
		               dialog.initOwner(row.getScene().getWindow());
		               dialog.setTitle("점수 등록");
		               try
		               {
		            	   
		            	  
						   Parent parent = FXMLLoader
						        .load(getClass().getResource("inputScore.fxml"));
						   Scene scene = new Scene(parent);
						   dialog.setScene(scene);
						   dialog.setResizable(false);
						   dialog.show();
				  
				  
						   Label scoreIdLbl = (Label) parent.lookup("#scoreIdLbl");
						   scoreIdLbl.setText(sd.getDmemId());
						   
						   TextField atdTotalTf = (TextField) parent.lookup("#atdTotalTf");
						   atdTotalTf.setText(adDaoTot);
						   TextField atdCountTf = (TextField) parent.lookup("#atdCountTf");
						   atdCountTf.setText(adDaoCnt);
						  
						   TextField asgTotalTf = (TextField) parent.lookup("#asgTotalTf");
						   asgTotalTf.setText(amDaoTot);
						   TextField asgCountTf = (TextField) parent.lookup("#asgCountTf");
						   asgCountTf.setText(amDaoCnt);
						  
						   TextField atdScoreTf = (TextField) parent.lookup("#atdScoreTf");
						   atdScore = (Float.parseFloat(adDaoCnt)/Float.parseFloat(adDaoTot))*100;
						   atdScoreTf.setText(Float.toString(atdScore));
						   
						   TextField asgScoreTf = (TextField) parent.lookup("#asgScoreTf");
						   asgScore = (Float.parseFloat(amDaoCnt)/Float.parseFloat(amDaoTot))*100;
						   asgScoreTf.setText(Float.toString(asgScore));
						   
						   
						   TextField totalScoreTf = (TextField) parent.lookup("#totalScoreTf");
						   totScore = ((atdScore+asgScore)/2);
						   totalScoreTf.setText(Float.toString(totScore));
						  
						   TextField inputScoreTf = (TextField) parent.lookup("#inputScoreTf");
						   //inputScoreTf.setText(sd.getDScore());
						   inputScoreTf.setText(String.valueOf(sd.getDScore()));
				          
				          
				 
				          
						   //저장
						   Button btnScoreUpdate = (Button)parent.lookup("#btnScoreUpdate");
						   if(!LoginController.idPass.equals("ehr"))
			                  {
							   	btnScoreUpdate.setVisible(false);
			                  }
						   
				   
						   btnScoreUpdate.setOnAction(e ->
						   {
								ScoreVO scVO = new ScoreVO();
								scVO.setMemId(sd.getDmemId());
								scVO.setpjSeqNo(sd.getDPjSeqNo());
								if(sd.getDAssignNo().equals("출석"))
			                     {
			                    	 scVO.setAssignNo("10");
			                     }
			                     else scVO.setAssignNo("20");
			                     //scVO.setAssignNo(sd.getDAssignNo());
								scVO.setScore(Integer.parseInt(inputScoreTf.getText()));
								scVO.setScSeqNo(sd.getdScSeqNo());
								scVO.setScComment(sd.getDComment());
				      
			         
								//System.out.println(scVO);
								 
								 
								sdDao.doUpdate(scVO);
								
								sc.setBp(bp);
								sc.scoreClick();
								
								
//								this.tableDetailView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ScoreDetail>()
//								{
//
//									@Override
//									public void changed(ObservableValue<? extends ScoreDetail> observable,
//											ScoreDetail oldValue, ScoreDetail newValue) 
//									{
//										sc.setBp(bp);
//										sc.scoreClick();
//										tableDetailView.setVisible(true);
//									}
//
//			             		});
								 
								dialog.close();
					            
			                   
			                     
						   });
						   //취소
						   Button btnScoreCancel = (Button)parent.lookup("#btnScoreCancel");
						   if(!LoginController.idPass.equals("ehr"))
			                  {
							   		btnScoreCancel.setVisible(false);
			                  }
						   
						   btnScoreCancel.setOnAction(e ->
						   {
								dialog.close();
						   });
		                  
		                  
		               }
		               catch (IOException e) 
		               {
		                  e.printStackTrace();
		               }
		               
		            }
				}
	        	 
	            
	         });
	      return row;
	         
	      });
					
	}
	
}
