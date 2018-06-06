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

import com.neolumia.snake.GameApp;
import com.neolumia.snake.game.Direction;
import com.neolumia.snake.game.Game;
import com.neolumia.snake.game.duo.DuoGame;
import com.neolumia.snake.game.single.SingleGame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.neolumia.snake.GameApp.t;

public final class GameWindow extends Window {

  private final GameApp app;
  private final Game game;
  private final Label pause = new Label("Pause");

  @FXML private GridPane gridRoot;

  @FXML private StackPane root;
  @FXML private Group group;
  @FXML private Label points;
  @FXML private Label highscore;

  public GameWindow(GameApp app, Game game) {
    this.app = app;
    this.game = game;
    root.setStyle("-fx-background-color: #E0FFFF;");
    group.getChildren().add(game);

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> update()));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    update();
  }

  private void update() {
    points.setText(t("gameOver.points", game.getPoints()));
    highscore.setText(t("gameOver.highscore", app.getHighscore()));
  }

  @Override
  public void load(Stage stage, Scene scene) {
    scene.setOnKeyPressed(event -> {

      if (event.getCode() == KeyCode.ESCAPE) {
        updatePaused(game.setPaused(!game.isPaused()));
        return;
      }

      if (game instanceof SingleGame) {
        switch (event.getCode()) {
          case UP:
            ((SingleGame) game).getSnake().setNext(Direction.NORTH);
            break;
          case DOWN:
            ((SingleGame) game).getSnake().setNext(Direction.SOUTH);
            break;
          case LEFT:
            ((SingleGame) game).getSnake().setNext(Direction.WEST);
            break;
          case RIGHT:
            ((SingleGame) game).getSnake().setNext(Direction.EAST);
            break;
        }
      }

      if (game instanceof DuoGame) {
        switch (event.getCode()) {
          case UP:
            ((DuoGame) game).getSecond().setNext(Direction.NORTH);
            break;
          case DOWN:
            ((DuoGame) game).getSecond().setNext(Direction.SOUTH);
            break;
          case LEFT:
            ((DuoGame) game).getSecond().setNext(Direction.WEST);
            break;
          case RIGHT:
            ((DuoGame) game).getSecond().setNext(Direction.EAST);
            break;
          case W:
            ((DuoGame) game).getFirst().setNext(Direction.NORTH);
            break;
          case S:
            ((DuoGame) game).getFirst().setNext(Direction.SOUTH);
            break;
          case A:
            ((DuoGame) game).getFirst().setNext(Direction.WEST);
            break;
          case D:
            ((DuoGame) game).getFirst().setNext(Direction.EAST);
            break;
        }
      }
    });

    stage.setMinWidth(game.getTerrain().getWidth() + 32);
    stage.setMinHeight(game.getTerrain().getHeight() + 96);
    app.getWindowManager().center();
  }

  private void updatePaused(boolean now) {
    if (now) {
      root.getChildren().add(pause);
    } else {
      root.getChildren().remove(pause);
    }
  }
}
