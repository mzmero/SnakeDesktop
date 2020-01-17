package com.neolumia.snake.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.annotation.Nullable;

/** This class manages JavaFX Stage and Scene displayed */
public final class WindowManager {

  private static final String CSS_FILE = "style/styles.css";
  private final Stage stage = new Stage();
  private final Scene scene = new Scene(new Group(), 1000, 520);

  @Nullable private Window window;

  /**
   * Request for a new scene to be set
   *
   * @param window
   */
  public void request(Window window) {
    if (this.window != null) {
      this.window.unload();
    }
    this.window = window;
    scene.setRoot(window.getLoader().getRoot());
    window.load(stage, scene);
  }

  /** Building a new screen and assigning style sheet and basic functionality */
  public void init() {
    scene.getStylesheets().add(CSS_FILE);
    scene.addEventHandler(
        KeyEvent.KEY_PRESSED,
        e -> {
          if (e.getCode() == KeyCode.F11) {
            stage.setFullScreen(!stage.isFullScreen());
            return;
          }
          if (e.getCode() == KeyCode.MINUS) {
            center();
          }
        });
    stage
        .onCloseRequestProperty()
        .addListener(
            (ob, ov, nv) -> {
              Platform.exit();
              System.exit(0);
            });
    stage
        .fullScreenProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue) {
                new Timeline(new KeyFrame(Duration.seconds(1), t -> center())).play();
              }
            });
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setScene(scene);
    stage.setTitle("Snake");
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
    stage.show();
  }

  /**
   * Returns the stage
   *
   * @return
   */
  public Stage getStage() {
    return stage;
  }

  /** Locates in the center of the screen */
  public void center() {
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
  }
}
