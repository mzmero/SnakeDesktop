/*
 * This file is part of Snake, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 Neolumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.neolumia.snake.view;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.annotation.Nullable;

public final class WindowManager {

  private static final String CSS_FILE = "style/styles.css";
  private final Stage stage = new Stage();
  private final Scene scene = new Scene(new Group(), 1000, 520);

  @Nullable
  private Window window;

  public void request(Window window) {
    if (this.window != null) {
      this.window.unload();
    }
    this.window = window;
    scene.setRoot(window.getLoader().getRoot());
    window.load(stage, scene);
  }

  public void init() {
    scene.getStylesheets().add(CSS_FILE);
    scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.F11) {
        stage.setFullScreen(!stage.isFullScreen());
        return;
      }
      if (e.getCode() == KeyCode.MINUS) {
        center();
      }
    });
    stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        new Timeline(new KeyFrame(Duration.seconds(1), t -> center())).play();
      }
    });
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setScene(scene);
    stage.setTitle("Project Snake");
    stage.show();
  }

  public Stage getStage() {
    return stage;
  }

  private void center() {
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
  }
}
