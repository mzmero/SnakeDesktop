package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.SingleSnake;
import com.neolumia.snake.model.Direction;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.control.SingleGame;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.text.DecimalFormat;

import static com.neolumia.snake.GameApp.t;

/** This is the class which is responsible for the GAME window */
public final class GameWindow extends Window {

  private final GameApp app;
  private final Game game;
  private final Label pause = new Label("Pause");
  @FXML private GridPane gridRoot;
  @FXML private StackPane root;
  @FXML private Group group;
  @FXML private Label points;
  @FXML private Label highscore;
  @FXML private Label time;
  @FXML private Label timePlaylabel;
  @FXML private ImageView life1;
  @FXML private ImageView life2;
  @FXML private ImageView life3;
  @FXML private ImageView life4;
  @FXML private ImageView life5;
  @FXML private ImageView life6;
  @FXML private ImageView life7;
  @FXML private ImageView life8;
  @FXML private ImageView back;
  public static boolean isQuestion1 = false;
  public static boolean isQuestion2 = false;
  public static boolean isQuestion3 = false;
  Timeline timeline;

  public GameWindow(GameApp app, Game game) {
    this.app = app;
    this.game = game;
        root.setStyle("-fx-background-color: #"+Integer.toHexString(app.getDesign().background.getColor().hashCode()));
//    root.setStyle("-fx-background-color: white");
    group.getChildren().add(game);
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> update()));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    update();
  }

  private void update() {
    switch (game.getLives()) {
      case 1:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(false);
        life3.visibleProperty().setValue(false);
        life4.visibleProperty().setValue(false);
        life5.visibleProperty().setValue(false);
        life6.visibleProperty().setValue(false);
        life7.visibleProperty().setValue(false);
        life8.visibleProperty().setValue(false);
        break;
      case 2:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(false);
        life4.visibleProperty().setValue(false);
        life5.visibleProperty().setValue(false);
        life6.visibleProperty().setValue(false);
        life7.visibleProperty().setValue(false);
        life8.visibleProperty().setValue(false);
        break;
      case 3:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        life4.visibleProperty().setValue(false);
        life5.visibleProperty().setValue(false);
        life6.visibleProperty().setValue(false);
        life7.visibleProperty().setValue(false);
        life8.visibleProperty().setValue(false);
        break;
      case 4:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        life4.visibleProperty().setValue(true);
        life5.visibleProperty().setValue(false);
        life6.visibleProperty().setValue(false);
        life7.visibleProperty().setValue(false);
        life8.visibleProperty().setValue(false);
        break;
      case 5:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        life4.visibleProperty().setValue(true);
        life5.visibleProperty().setValue(true);
        life6.visibleProperty().setValue(false);
        life7.visibleProperty().setValue(false);
        life8.visibleProperty().setValue(false);
        break;
      case 6:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        life4.visibleProperty().setValue(true);
        life5.visibleProperty().setValue(true);
        life6.visibleProperty().setValue(true);
        life7.visibleProperty().setValue(false);
        life8.visibleProperty().setValue(false);
        break;
      case 7:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        life4.visibleProperty().setValue(true);
        life5.visibleProperty().setValue(true);
        life6.visibleProperty().setValue(true);
        life7.visibleProperty().setValue(true);
        life8.visibleProperty().setValue(false);
        break;
      case 8:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        life4.visibleProperty().setValue(true);
        life5.visibleProperty().setValue(true);
        life6.visibleProperty().setValue(true);
        life7.visibleProperty().setValue(true);
        life8.visibleProperty().setValue(true);
        break;
    }
    timePlaylabel.setText(t("stats.playtime", new DecimalFormat("#.###").format(game.getPlaytime())));
    points.setText(t("gameOver.points", game.getPoints()));
    highscore.setText(t("gameOver.highscore", app.getHighscore()));
    // TODO : Get duration from start and update text view - (time) - Note that time is already
    // defined and added to FXML
    // TODO : Handle Timer On Pause
    if (app.getHighscore() == -1) highscore.setVisible(false);
    /*if (isQuestion1){game.setPaused(true);}

    if (isQuestion2){game.setPaused(true);}

    if (isQuestion3){game.setPaused(true);}*/

    app.getWindowManager().getStage().requestFocus();
  }

  @Override
  public void load(Stage stage, Scene scene) {

    scene.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.P) {
            updatePaused(game.setPaused(!game.isPaused()));
            return;
          }
          if (event.getCode() == KeyCode.END) {
            game.setAuto(!game.isAuto());
            System.out.println("Auto: " + game.isAuto());
            return;
          }

          if (game instanceof SingleGame) {
            switch (event.getCode()) {
              case UP:
                ((SingleGame) game).getSnake().setNext(Direction.NORTH);
                break;
              case DOWN:
                ((SingleGame) game).getSnake().setNext(Direction.SOUTH);
                break;
              case LEFT:
                ((SingleGame) game).getSnake().setNext(Direction.WEST);
                break;
              case RIGHT:
                ((SingleGame) game).getSnake().setNext(Direction.EAST);
                break;
            }
          }
        });
    stage.setMinWidth(game.getTerrain().getWidth() + 32);
    stage.setMinHeight(game.getTerrain().getHeight() + 96);
    app.getWindowManager().center();
  }

  public void updatePaused(boolean now) {
    if (now) root.getChildren().add(pause);
    else root.getChildren().remove(pause);
  }

  public static void setQuestions(Game game) {
    isQuestion1 = false;
    isQuestion2 = false;
    isQuestion3 = false;
    game.setPaused(false);
  }
  public void handleBack() throws SQLException {
    game.setPaused(true);
    app.getWindowManager().request(new MenuWindow(app));
  }


}
