package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;

import com.neolumia.snake.control.SysData;
import com.neolumia.snake.model.Question;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class Login extends Window {
  GameApp app;
  @FXML TextField playerName;
  @FXML PasswordField password;
  @FXML Text password_text;
  @FXML AnchorPane ap;
  @FXML Button login;
  @FXML Button next;
  Boolean isNew = false;
  Boolean isValidPassword = false;

  public Login(GameApp app) {
    this.app = app;

    password_text.setVisible(false);
    password.setVisible(false);
    login.setVisible(false);

    playerName
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              next.setDisable(false);
            });
  }

  @FXML
  public void login(MouseEvent mouseEvent) throws SQLException {
    if (isNew) {
      if (password.getText().length() < 6 || password.getText().length() > 12)
        Toast.toast("Invalid Password Length, Length must be 6-12 chars", Color.RED);
      else {
        Toast.toast("Registered Successfully " + playerName.getText(),Color.GREEN);
        isValidPassword = true;
      }

    } else {
      if (password.getText().equals("a")) // TODO : Check PW from DB
      {
        Toast.toast("Logged In Successfully " + playerName.getText(), Color.GREEN);
        isValidPassword = true;
      } else {
        Toast.toast("Invalid Password ! Please Try Again", Color.RED);
        isValidPassword = false;
      }
    }

    if (isValidPassword) {
      String playerName = this.playerName.getText();
      app.setPlayerName(playerName);
      app.getWindowManager().request(new MenuWindow(app));
    }
  }

  @FXML
  public void next(MouseEvent mouseEvent) throws SQLException {
    String playerName = "";

    if (!this.playerName.getText().equals("")) playerName = this.playerName.getText();
    if (app.getDatabase().player_ID(playerName) == -1) {
      /** New User */
      isNew = true;
      app.getDatabase().newPlayer(playerName);
      password_text.setText("Please Choose A Password");
      password.setVisible(true);
      password_text.setVisible(true);
      login.setVisible(true);

    } else {
      /** Registered User */
      isNew = false;
      password_text.setText(
          "Welcome Back "
              + playerName
              + ", please Type Your Password."
              + "\n Note that if u dont have account yet,this player name is taken : Change username and set your own PW");
      password.setVisible(true);
      password_text.setVisible(true);
      login.setVisible(true);
    }
    next.setDisable(true);
  }
}
