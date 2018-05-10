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

import static com.google.common.base.Preconditions.checkNotNull;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.game.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DeadWindow extends Window {

  private static final Logger LOGGER = LogManager.getLogger(DeadWindow.class);

  private final GameApp app;
  private final Game game;

  @FXML private Group group;
  @FXML private Label head;
  @FXML private Label points;
  @FXML private Label highscore;

  public DeadWindow(GameApp app, Game game, boolean won) {
    this.app = checkNotNull(app);
    this.game = checkNotNull(game);

    group.getChildren().add(game);
    group.setOpacity(0.4);

    points.setText(String.format(points.getText(), game.getPoints()));
    highscore.setText(String.format(highscore.getText(), app.getHighscore()));

    if (won) {
      head.setText("Rekord!");
    }
  }

  @FXML
  public void newGame() {
    app.newGame(game.getType());
  }

  @FXML
  public void back() {
    app.getWindowManager().request(new MenuWindow(app));
  }

  @FXML
  public void exit() {
    Platform.exit();
  }
}
