package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.Game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.sql.SQLException;


public class Login extends Window {
  GameApp app;
  @FXML
  TextField playerName;
  public Login(GameApp app) {
    this.app = app;
  }
  @FXML
  public void login(MouseEvent mouseEvent) throws SQLException {
    String playerName="";
    if(!this.playerName.getText().equals(""))
       playerName = this.playerName.getText();

    app.setPlayerName(playerName);

    app.getWindowManager().request(new MenuWindow(app));
  }
}
