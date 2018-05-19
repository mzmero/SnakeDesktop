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
import com.neolumia.snake.Locales;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.sql.SQLException;

public final class SettingsWindow extends Window {

  private final GameApp app;

  private final ToggleGroup locale = new ToggleGroup();
  private final ToggleGroup difficulty = new ToggleGroup();
  private final ToggleGroup terrain = new ToggleGroup();

  @FXML private ToggleButton localeGerman;
  @FXML private ToggleButton localeEnglish;
  @FXML private ToggleButton difficultyEasy;
  @FXML private ToggleButton difficultyMedium;
  @FXML private ToggleButton difficultyHard;
  @FXML private ToggleButton terrainSmall;
  @FXML private ToggleButton terrainMedium;
  @FXML private ToggleButton terrainBig;

  @FXML private TextField playerName;
  @FXML private CheckBox leaderboard;

  SettingsWindow(GameApp app) {
    this.app = app;

    localeGerman.setToggleGroup(locale);
    localeEnglish.setToggleGroup(locale);

    difficultyEasy.setToggleGroup(difficulty);
    difficultyMedium.setToggleGroup(difficulty);
    difficultyHard.setToggleGroup(difficulty);

    terrainSmall.setToggleGroup(terrain);
    terrainMedium.setToggleGroup(terrain);
    terrainBig.setToggleGroup(terrain);

    //playerName.setText(app.getSettings().playerName);
    //leaderboard.setSelected(app.getSettings().leaderboard);

    //locale.selectToggle(app.getSettings().locale == Locales.GERMAN ? localeGerman : localeEnglish);
  }

  @FXML
  public void cancel() {
    app.getWindowManager().request(new MenuWindow(app));
  }

  public void save() throws SQLException {
    app.getSettings().playerName = playerName.getText();
    app.getSettings().leaderboard = leaderboard.isSelected();
    app.getSettings().locale = locale.getSelectedToggle().equals(localeGerman) ? Locales.GERMAN : Locales.ENGLISH;
    app.getDatabase().saveSettings(app.getSettings());
    cancel();
  }
}
