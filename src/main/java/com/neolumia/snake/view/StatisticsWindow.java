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
import com.neolumia.snake.Stats;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.control.SysData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.neolumia.snake.GameApp.t;

/**
 * This class is responsible for the statistics - games leaderboard and more
 */
public final class StatisticsWindow extends Window {

  private final GameApp app;
  private final ToggleGroup menu = new ToggleGroup();

  @FXML
  private GridPane grid;
  @FXML
  private ToggleButton stats;
  @FXML
  private ToggleButton history;
  @FXML
  private ToggleButton leaderboard;

  /**
   * Stats Controls
   */
  @FXML
  private Label statsGames;
  @FXML
  private Label statsItems;
  @FXML
  private Label statsPlaytime;
  @FXML
  private Label statsWall;
  @FXML
  private Label board;
  @FXML
  private Label board1;

  /**
   * History Controls
   */

  @FXML
  private TableView<TableItem> historyTable = new TableView<TableItem>();
  @FXML
  private Label historyTitle;


  StatisticsWindow(GameApp app) {
    this.app = app;

    stats.setToggleGroup(menu);
    history.setToggleGroup(menu);
    leaderboard.setToggleGroup(menu);

    menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.equals(stats)) {
        grid.add(render("stats"), 0, 0);
        menu.selectToggle(stats);
        update(app.getStats());
      }
      if (newValue.equals(history)) {
        grid.add(render("history"), 0, 0);
        menu.selectToggle(history);
        fillHistory();
      }
      if (newValue.equals(leaderboard)) {
        grid.add(render("leaderboard"), 0, 0);
        menu.selectToggle(leaderboard);
        fillboard();
      }
    });


  }


  @FXML
  public void cancel() {
    app.getWindowManager().request(new MenuWindow(app));
  }

  private void fillHistory() {
    if (statsGames != null && statsItems != null && statsPlaytime != null && statsWall != null) {
      statsGames.setVisible(false);
      statsItems.setVisible(false);
      statsPlaytime.setVisible(false);
      statsWall.setVisible(false);
    }
    historyTitle.setVisible(true);
    historyTable.setVisible(true);
    fillTable();
  }


  public void fillboard() {
    if (statsGames != null && statsItems != null && statsPlaytime != null && statsWall != null) {
      statsGames.setVisible(false);
      statsItems.setVisible(false);
      statsPlaytime.setVisible(false);
      statsWall.setVisible(false);
    }
    if (historyTable != null) historyTable.setVisible(false);
    if (historyTitle != null) historyTitle.setVisible(false);

  }

  private void update(Stats stats) {

    if (historyTable != null) historyTable.setVisible(false);
    if (historyTitle != null) historyTitle.setVisible(false);
    statsGames.setText(t("stats.games", stats.games));
    statsItems.setText(t("stats.items", stats.items));
    statsPlaytime.setText(t("stats.playtime", new DecimalFormat("#.##").format(stats.playtime)));
    statsWall.setText(t("stats.walls", stats.walls));
    statsGames.setVisible(true);
    statsItems.setVisible(true);
    statsPlaytime.setVisible(true);
    statsWall.setVisible(true);
  }

  /**
   * ------------------------------------------ Helper Methods -----------------------------------------------------
   */
  private void fillTable() {

    TableColumn player = new TableColumn("player");
    player.setMinWidth(100);
    player.setCellValueFactory(
      new PropertyValueFactory<TableItem, String>("player"));

    TableColumn points = new TableColumn("points");
    points.setMinWidth(100);
    points.setCellValueFactory(
      new PropertyValueFactory<TableItem, String>("points"));

    TableColumn lives = new TableColumn("lives");
    lives.setMinWidth(200);
    lives.setCellValueFactory(
      new PropertyValueFactory<TableItem, String>("lives"));
    final ObservableList<TableItem> data =
      FXCollections.observableArrayList(SysData.getInstance().getPlayerHistory(app.getSettings().playerName));
    historyTable.setItems(data);
    historyTable.getColumns().addAll(player, points, lives);
  }


}
