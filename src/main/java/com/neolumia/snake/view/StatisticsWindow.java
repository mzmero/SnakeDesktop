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
import com.neolumia.snake.model.Stats;
import com.neolumia.snake.control.SysData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.text.DecimalFormat;

import static com.neolumia.snake.GameApp.t;

/** This class is responsible for the statistics - games leaderboard and more */
public final class StatisticsWindow extends Window {

  private final GameApp app;
  private final ToggleGroup menu = new ToggleGroup();

  @FXML private GridPane grid;
  @FXML private ToggleButton stats;
  @FXML private ToggleButton history;
  @FXML private ToggleButton leaderboard;

  /** Stats Controls */
  @FXML private Label statsGames;

  @FXML private Label statsItems;
  @FXML private Label statsPlaytime;
  @FXML private Label statsWall;
  @FXML private Label board;
  @FXML private Label board1;

  /** History Controls */
  private final ObservableList<TableItem> data;    //table data

  @FXML private TableView<TableItem> historyTable = new TableView<TableItem>();
  @FXML private Label historyTitle;
  /** Board Controls */
  private final ObservableList<TableItem> boardData;

  @FXML private TableView<TableItem> boardTable = new TableView<TableItem>();
  @FXML private Label boardTitle;

  StatisticsWindow(GameApp app) throws SQLException {
    this.app = app;

    stats.setToggleGroup(menu);
    history.setToggleGroup(menu);
    leaderboard.setToggleGroup(menu);
                                             // the page button listener
    menu.selectedToggleProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.equals(stats)) {               //stat button
                grid.add(render("stats"), 0, 0);
                menu.selectToggle(stats);
                update(app.getStats());
              }

              if (newValue.equals(history)) {      //history button
                grid.add(render("history"), 0, 0);
                menu.selectToggle(history);
                fillHistory();
              }

              if (newValue.equals(leaderboard)) {      //leaderboard button
                grid.add(render("leaderboard"), 0, 0);
                menu.selectToggle(leaderboard);
                fillBoard();
              }
            });

    data =
        FXCollections.observableArrayList(
            SysData.getInstance().getPlayerHistory(app.getPlayerName()));

    boardData =
      FXCollections.observableArrayList(SysData.getInstance().getHistoryTableItems(app));
  }

  @FXML
  public void cancel() throws SQLException {
    app.getWindowManager().request(new MenuWindow(app));
  }
   /*for every button in the  main page(3 buttons) open a mini-page .every method  check if other mini-pages are opened
   then disabling the visibility of other mini-pages if its true  */

  private void fillHistory() {   //updating the history table method
    if (statsGames != null && statsItems != null && statsPlaytime != null && statsWall != null) {
      statsGames.setVisible(false);
      statsItems.setVisible(false);
      statsPlaytime.setVisible(false);
      statsWall.setVisible(false);
    }
    if (boardTable != null) boardTable.setVisible(false);
    if (boardTitle != null) boardTitle.setVisible(false);
    historyTitle.setVisible(true);
    historyTable.setVisible(true);
    fillTable();
  }

  public void fillBoard() {   // updating the leaderboard table method
    if (statsGames != null && statsItems != null && statsPlaytime != null && statsWall != null) {
      statsGames.setVisible(false);
      statsItems.setVisible(false);
      statsPlaytime.setVisible(false);
      statsWall.setVisible(false);
    }
    if (historyTable != null) historyTable.setVisible(false);
    if (historyTitle != null) historyTitle.setVisible(false);
    boardTitle.setVisible(true);
    boardTable.setVisible(true);
    fillBoardTable();
  }

  private void update(Stats stats) {   // fill the stats page data

    if (historyTable != null) historyTable.setVisible(false);
    if (historyTitle != null) historyTitle.setVisible(false);
    if (boardTable !=null)  boardTable.setVisible(false);
    if (boardTitle !=null)  boardTitle.setVisible(false);
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
   * ------------------------------------------ Helper Methods
   * -----------------------------------------------------
   */
  private void fillTable() {  // updating the history table method

    TableColumn player = new TableColumn("player");
    player.setMinWidth(100);
    player.setCellValueFactory(new PropertyValueFactory<TableItem, String>("player"));

    TableColumn points = new TableColumn("points");
    points.setMinWidth(100);
    points.setCellValueFactory(new PropertyValueFactory<TableItem, String>("points"));

    TableColumn lives = new TableColumn("lives");
    lives.setMinWidth(200);
    lives.setCellValueFactory(new PropertyValueFactory<TableItem, String>("lives"));
    historyTable.setItems(data);
    historyTable.getColumns().addAll(player, points, lives);
  }

  private void fillBoardTable() {   //updating the leaderboard method
    TableColumn player = new TableColumn("player");
    player.setMinWidth(100);
    player.setCellValueFactory(new PropertyValueFactory<TableItem, String>("player"));

    TableColumn points = new TableColumn("points");
    points.setMinWidth(100);
    points.setCellValueFactory(new PropertyValueFactory<TableItem, String>("points"));

    TableColumn lives = new TableColumn("lives");
    lives.setMinWidth(200);
    lives.setCellValueFactory(new PropertyValueFactory<TableItem, String>("lives"));
    boardTable.setItems(boardData);
    boardTable.getColumns().addAll(player, points, lives);
  }
}
