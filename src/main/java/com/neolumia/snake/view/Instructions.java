package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.control.SingleGame;
import com.neolumia.snake.view.option.GameType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class Instructions extends Window {
  GameApp app;
  Game game;

  public Instructions(GameApp app) {
    this.app = app;
    this.game =  new SingleGame(app, GameType.CLASSIC);
  }

  @FXML
  public void enterGame(MouseEvent mouseEvent) throws SQLException {
    synchronized (this) {
      app.getWindowManager().request(new GameWindow(app, game));
      game.init();
      game.run();
    }
  }
}
