package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is the class which is responsible for the GAME OVER view - which is shown when user loses
 */
public final class DeadWindow extends Window {

  private static final Logger LOGGER = LogManager.getLogger(DeadWindow.class);

  private final GameApp app;
  private final Game game;

  @FXML private Group group;
  @FXML private Label headLost;
  @FXML private Label headWon;
  @FXML private Label points;
  @FXML private Label highscore;

  public DeadWindow(GameApp app, Game game, boolean won) {
    this.app = checkNotNull(app);
    this.game = checkNotNull(game);

    group.getChildren().add(game);
    group.setOpacity(0.4);

    points.setText(MessageFormat.format(points.getText(), game.getPoints()));
    highscore.setText(MessageFormat.format(highscore.getText(), app.getHighscore()));

    if (won) {
      headLost.setVisible(false);
    } else {
      headWon.setVisible(false);
    }
  }

  @FXML
  public void newGame() {
    app.newGame(game.getType());
  }

  @FXML
  public void back() {
    app.getWindowManager().request(new MenuWindow(app));
  }

  @FXML
  public void exit() {
    Platform.exit();
  }
}
