package com.neolumia.snake.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.neolumia.snake.Database;
import com.neolumia.snake.GameApp;

import com.neolumia.snake.control.SysData;
import com.neolumia.snake.model.Question;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
import org.h2.util.New;

import java.io.IOException;
import java.sql.SQLException;

/** this class controls the login windows that is shown in order to manage users */
public class Login extends Window {
  GameApp app;
  @FXML
  JFXTextField playerName;
  @FXML
  JFXPasswordField password;
  @FXML Text password_text;
  @FXML AnchorPane ap;
  @FXML
  JFXButton login;
  @FXML JFXButton next;
  Boolean isNew = false;
  Boolean isValidPassword = false;
  @FXML
  private JFXButton loginSignup;

  @FXML
  private JFXButton Back;

  @FXML
  private JFXTextField NewPlayerName;

  @FXML
  private Text password_text1;

  @FXML
  private JFXPasswordField NewPassword;

  @FXML
  private JFXPasswordField NewPassword1;

  @FXML
  private JFXButton signup;
  @FXML
  private Group grpSignup;
  @FXML
  private Group grpLogin;
  RequiredFieldValidator reqUserValidator = new RequiredFieldValidator();
  RequiredFieldValidator reqPasswordValidator = new RequiredFieldValidator();

  /**
   * Initializer method for the login window
   *
   * @param app
   */
  public Login(GameApp app) {
    this.app = app;

    password.setVisible(false);
    login.setVisible(false);

    playerName
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              next.setDisable(false);
            });

    reqUserValidator.setMessage("User Name is Required");
    reqPasswordValidator.setMessage("Password is Required");

    playerName.getValidators().add(reqUserValidator);
    password.getValidators().add(reqPasswordValidator);
    NewPlayerName.getValidators().add(reqUserValidator);
    NewPassword.getValidators().add(reqPasswordValidator);
    NewPassword1.getValidators().add(reqPasswordValidator);
  }

  /**
   * login - responsible to authernticate the password of the user and validate password length and
   * redirecting to the menu window
   *
   * @param mouseEvent
   * @throws SQLException
   */
  @FXML
  public void login(MouseEvent mouseEvent) throws SQLException {


    if(playerName.validate()){
      if(password.validate()){
        if(app.getDatabase().playerPassword(playerName.getText()).equals(password.getText())){
          Toast.toast("Logged In Successfully " + playerName.getText(), Color.GREEN);
          String playerName = this.playerName.getText();
          app.setPlayerName(playerName);
          app.getWindowManager().request(new MenuWindow(app));

        }else{
          Toast.toast("Invalid Password", Color.RED);

        }



      }
    }



    /*
    if (isNew) {
      if (password.getText().length() < 6 || password.getText().length() > 12)
        Toast.toast("Invalid Password Length, Length must be 6-12 chars", Color.RED);
      else {
        Toast.toast("Registered Successfully " + playerName.getText(), Color.GREEN);
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
    }*/
  }

  /**
   * next - handles the click on next in the login window which is responsible to show password
   * field to user and authenticate
   *
   * @param mouseEvent
   * @throws SQLException
   */
  @FXML
  public void next(MouseEvent mouseEvent) throws SQLException {
    String name = "";


    if (playerName.validate()) {
      name = this.playerName.getText();
      if (app.getDatabase().player_ID(playerName.getText()) != -1) {
        /** Registered User */
        System.out.print(app.getDatabase().player_ID(playerName.getText()));
        next.setDisable(true);
        password.setVisible(true);
        login.setVisible(true);
      } else {//username does not exist
        Toast.toast("User does Not Exist", Color.RED);

      }
    }
  }



  @FXML
  void backToLogin(MouseEvent event) {
    grpSignup.setVisible(false);
    grpLogin.setVisible(true);
    playerName.setText(NewPlayerName.getText());
    NewPlayerName.clear();
    NewPassword.clear();
    NewPassword1.clear();
  }


  @FXML
  void navSignup(MouseEvent event) {
    grpLogin.setVisible(false);
    grpSignup.setVisible(true);
    playerName.clear();
    password.clear();


  }


  @FXML
  void signupNewPlayer(MouseEvent event) throws SQLException {
      if(NewPlayerName.validate()) {
        String playername = NewPlayerName.getText();
        if (app.getDatabase().player_ID(playername) != -1) {
          //user already Exist
          Toast.toast("User Already Exists ", Color.RED);


        } else {// username do not exist
          if (NewPassword.validate()) {
            if (NewPassword1.validate()) {
              if (NewPassword.getText().equals(NewPassword1.getText())) {
                app.getDatabase().newPlayer(playername, NewPassword.getText());
                grpSignup.setVisible(false);
                grpLogin.setVisible(true);
                playerName.setText(NewPlayerName.getText());
                NewPlayerName.clear();
                NewPassword.clear();
                NewPassword1.clear();

              } else { //passwords do not match
                Toast.toast("Passwords Do Not Match !", Color.RED);

              }


            }
          }


        }


      }}





}
