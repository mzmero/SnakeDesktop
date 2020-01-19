package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.control.SingleGame;
import com.neolumia.snake.control.SingleSnake;
import com.neolumia.snake.view.option.GameType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

/** this class is used to control the instructions FXML which is shown prior to game start */
public class Instructions extends Window {
  GameApp app;
  Game game;
  @FXML ToggleButton mute;

  public Instructions(GameApp app) {
    this.app = app;
    this.game = new SingleGame(app, GameType.CLASSIC);

  }

  /**
   * this method initializes a new game
   *
   * @param mouseEvent
   * @throws SQLException
   */
  @FXML
  public void enterGame(MouseEvent mouseEvent) throws SQLException {
    synchronized (this) {
      app.getWindowManager().request(new GameWindow(app, game));
      game.init();
      game.run();
    }
  }

  /**
   * this method is responsible to stop media player
   *
   * @param mouseEvent
   */
  @FXML
  public void muteMediaPlayer(MouseEvent mouseEvent) {
    if (mute.isSelected()) game.setMuted(true);
    else game.setMuted(false);
  }
}
