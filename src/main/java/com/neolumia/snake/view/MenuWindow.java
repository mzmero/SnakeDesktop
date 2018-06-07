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
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public final class MenuWindow extends Window {

  private final GameApp app;

  @FXML private GridPane root;
  @FXML private ImageView typeView;

  private GameType type = GameType.CLASSIC;

  public MenuWindow(GameApp app) {
    this.app = app;
  }

  @FXML
  public void play() {
    app.newGame(type);
  }

  @FXML
  public void design() {
    app.getWindowManager().request(new DesignWindow(app));
  }

  @FXML
  public void statistics() {
    app.getWindowManager().request(new StatisticsWindow(app));
  }

  @FXML
  public void settings() {
    app.getWindowManager().request(new SettingsWindow(app));
  }

  public void clickTitle() {
    switch(type) {
      case CLASSIC:
        switchType(GameType.RETRO);
        break;
      case RETRO:
        switchType(GameType.CLASSIC);
        break;
    }
  }

  private void switchType(GameType type) {
    if (this.type == type) {
      return;
    }
    this.type = type;
    typeView.setImage(new Image(getClass().getResourceAsStream(type.getFile())));
    typeView.setSmooth(true);
    typeView.setCache(true);
  }
}
