package com.neolumia.snake.view;

import java.awt.BorderLayout;

import com.neolumia.snake.GameApp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class Toast {
  public static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay) {
    Stage toastStage = new Stage();
    toastStage.setAlwaysOnTop(true);
    toastStage.initOwner(ownerStage);
    toastStage.setResizable(false);
    toastStage.initStyle(StageStyle.TRANSPARENT);

    Text text = new Text(toastMsg);
    text.setFill(Color.WHITE);

    StackPane root = new StackPane(text);
    root.setStyle("-fx-background-color: black; -fx-padding: 20px;");

    root.setOpacity(0);

    Scene scene = new Scene(root);
    scene.getStylesheets().add("style/styles.css");
    scene.setFill(Color.TRANSPARENT);
    toastStage.setScene(scene);
    toastStage.setX((GameApp.windowManager.getStage().getX()+GameApp.windowManager.getStage().getWidth())/2);
    toastStage.setY((GameApp.windowManager.getStage().getY()+GameApp.windowManager.getStage().getHeight())/2);
    toastStage.show();

    Timeline fadeInTimeline = new Timeline();
    KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
    fadeInTimeline.getKeyFrames().add(fadeInKey1);
    fadeInTimeline.setOnFinished((ae) ->
    {
      new Thread(() -> {
        try {
          Thread.sleep(toastDelay);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        Timeline fadeOutTimeline = new Timeline();
        KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
        fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
        fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
        fadeOutTimeline.play();
      }).start();
    });
    fadeInTimeline.play();
  }
}
