/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dj.media;

import java.awt.DisplayMode;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.scene.media.*;
import javafx.scene.paint.Color;


public class FXMLDocumentController implements Initializable {
    
    
    private MediaPlayer mediaplayer;
    
      @FXML
      public MediaView mediaview;
    
   private String filepath;
   @FXML
   private Slider slider;
   
   @FXML
   private Slider seekslider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
      FileChooser filechooser= new FileChooser();
       FileChooser.ExtensionFilter filter= new FileChooser.ExtensionFilter("Select a file (*.mp4)","*.mp4","*.mp3","*.MKV","*.FlV");
       filechooser.getExtensionFilters().add(filter);
       File file =filechooser.showOpenDialog(null);
       filepath=file.toURI().toString();
       
       if(filepath != null){
       Media media=new Media(filepath);
       mediaplayer =new MediaPlayer(media);
       mediaview.setMediaPlayer(mediaplayer);
       filepath = file.getAbsolutePath();
       filepath = filepath.replace("\\", "/");
       media = new Media(new File(filepath).toURI().toString());
mediaplayer.stop();
        mediaplayer.setAutoPlay(true);
           DoubleProperty width = mediaview.fitWidthProperty();
        DoubleProperty height = mediaview.fitHeightProperty();
      
        width.bind(Bindings.selectDouble(mediaview.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaview.sceneProperty(),"height"));
        slider.setValue(mediaplayer.getVolume()*100);
        
        slider.valueProperty().addListener(new InvalidationListener() {
           @Override
           public void invalidated(Observable observable) {
              mediaplayer.setVolume(slider.getValue()/100);
           }
       });
        mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
           @Override
           public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
               seekslider.setValue(newValue.toSeconds());
              
               
           }
       });
        seekslider.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               mediaplayer.seek(Duration.seconds(seekslider.getValue()/100));
              mediaplayer.getCurrentTime().greaterThanOrEqualTo(Duration.ZERO);
               
           }
       });
       
        
   
       }
    }
    
    @FXML
    
    private void playVideo(ActionEvent event){
        mediaplayer.play();
        mediaplayer.setRate(1);
    }
    
    @FXML
    private void pauseVideo(ActionEvent event){
        mediaplayer.pause();
        
    }
    @FXML
    private void stopVideo(ActionEvent event){
     mediaplayer.stop();   
    }
    @FXML
    private void forwardVideo(ActionEvent event){
        mediaplayer.setRate(1.5);
    }
   
    @FXML
    private void rewindVideo(ActionEvent event){
        mediaplayer.setRate(.75);
       
    }
   
    @FXML
      private void exitVideo(ActionEvent event){
        System.exit(0);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private static class duration {

        public duration() {
        }
    }
    
}
