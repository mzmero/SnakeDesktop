
package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;
import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.control.SingleGame;
import com.sun.javafx.sg.prism.NGAmbientLight;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.neolumia.snake.GameApp.t;

/**
 * This is the class which is responsible for the GAME window
 */
public final class GameWindow extends Window {

  private final GameApp app;
  private final Game game;
  private final Label pause = new Label("Pause");

  @FXML
  private GridPane gridRoot;

  @FXML
  private StackPane root;
  @FXML
  private Group group;
  @FXML
  private Label points;
  @FXML
  private Label highscore;
  @FXML
  private ImageView life1;
  @FXML
  private ImageView life2;
  @FXML
  private ImageView life3;
  public static boolean isQuestion1 = false;
  public static boolean isQuestion2 = false;
  public static boolean isQuestion3 = false;
  public static Question question1;
  public static Question question2;
  public static Question question3;

  public GameWindow(GameApp app, Game game) {
    this.app = app;
    this.game = game;
    root.setStyle("-fx-background-color: #" + Integer.toHexString(app.getDesign().background.getColor().hashCode()) + "");
    group.getChildren().add(game);

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> update()));
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
        break;
      case 2:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(false);
        break;
      case 3:
        life1.visibleProperty().setValue(true);
        life2.visibleProperty().setValue(true);
        life3.visibleProperty().setValue(true);
        break;
    }
    points.setText(t("gameOver.points", game.getPoints()));
    highscore.setText(t("gameOver.highscore", app.getHighscore()));
    if (isQuestion1)
      game.setPaused(true);
    if(isQuestion2)
      game.setPaused(true);
    if(isQuestion3)
      game.setPaused(true);

    app.getWindowManager().getStage().requestFocus();

  }

  @Override
  public void load(Stage stage, Scene scene) {
    scene.setOnKeyPressed(event -> {

      if (event.getCode() == KeyCode.ESCAPE) {
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
//
//      if (game instanceof DuoGame) {
//        switch (event.getCode()) {
//          case UP:
//            ((DuoGame) game).getSecond().setNext(Direction.NORTH);
//            break;
//          case DOWN:
//            ((DuoGame) game).getSecond().setNext(Direction.SOUTH);
//            break;
//          case LEFT:
//            ((DuoGame) game).getSecond().setNext(Direction.WEST);
//            break;
//          case RIGHT:
//            ((DuoGame) game).getSecond().setNext(Direction.EAST);
//            break;
//          case W:
//            ((DuoGame) game).getFirst().setNext(Direction.NORTH);
//            break;
//          case S:
//            ((DuoGame) game).getFirst().setNext(Direction.SOUTH);
//            break;
//          case A:
//            ((DuoGame) game).getFirst().setNext(Direction.WEST);
//            break;
//          case D:
//            ((DuoGame) game).getFirst().setNext(Direction.EAST);
//            break;
//        }
//      }
    });

    stage.setMinWidth(game.getTerrain().getWidth() + 32);
    stage.setMinHeight(game.getTerrain().getHeight() + 96);
    app.getWindowManager().center();
  }


  public void updatePaused(boolean now) {
    if (now) {
      root.getChildren().add(pause);
    } else {
      root.getChildren().remove(pause);
    }
  }

  public static void setQuestions(Game game) {
    isQuestion1 = false;
    isQuestion2 = false;
    isQuestion3 = false;
    game.setPaused(false);
  }
}
