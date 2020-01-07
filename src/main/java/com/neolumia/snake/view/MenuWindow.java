package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.view.option.GameType;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

/** This is the class which is responsible of the GAME view */
public final class MenuWindow extends Window {

  private final GameApp app;
  @FXML private GridPane root;
  @FXML private ImageView typeView;

  private GameType type = GameType.CLASSIC;

  public MenuWindow(GameApp app) throws SQLException {

    this.app = app;
    //TODO move to login
    System.out.print(app.getDatabase().getSettings(app.getPlayerName()).toString());
    this.app.setSettings(app.getDatabase().getSettings(app.getPlayerName()));
    this.app.setDesign(app.getDatabase().loadDesign(app.getPlayerName()));
    this.app.setStats(app.getDatabase().loadStats(app.getPlayerName()));






  }

  @FXML
  public void play() {
    /*app.newGame(type);*/
    app.getWindowManager().request(new Instructions(app));
  }

  @FXML
  public void questions() {
    app.getWindowManager().request(new QuestionsWindow(app));
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
    switchType(type.next());
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
