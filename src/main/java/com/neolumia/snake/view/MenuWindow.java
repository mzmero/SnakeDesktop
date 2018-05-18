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
import com.neolumia.snake.game.GameType;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public final class MenuWindow extends Window implements Initializable {

  private final GameApp app;

  @FXML GridPane root;
  @FXML private ImageView menuEdgeRight, menuEdgeLeft;

  private GameType gameType = GameType.RETRO;

  public MenuWindow(GameApp app) {
    this.app = app;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    menuEdgeRight.setImage(new Image("/lib/menu_edge.png"));
    menuEdgeLeft.setImage(new Image("/lib/menu_edge.png"));
    root.setStyle("-fx-background: #FFFFFF;");
  }

  @FXML
  public void start() {
    app.newGame(gameType);
  }

  @FXML
  public void settings() {
    app.getWindowManager().request(new SettingsWindow(app));
  }

  @FXML
  public void change() {
    switch (gameType) {
      case RETRO:
        this.gameType = GameType.CLASSIC;
        break;
      case CLASSIC:
        this.gameType = GameType.RETRO;
        break;
    }

    final Animation animation =
        new Transition() {
          {
            setCycleDuration(Duration.millis(2000));
            setInterpolator(Interpolator.LINEAR);
          }

          @Override
          protected void interpolate(double frac) {
            final Color c = (Color) root.getBackground().getFills().get(0).getFill();
            root.setBackground(
                new Background(
                    new BackgroundFill(
                        c.interpolate(gameType.getBackground(), frac),
                        CornerRadii.EMPTY,
                        Insets.EMPTY)));
          }
        };
    animation.play();
  }
}
