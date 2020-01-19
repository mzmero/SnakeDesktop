package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.view.game.SnakePartView;
import com.neolumia.snake.view.item.Mouse;
import com.neolumia.snake.view.item.food.Apple;
import com.neolumia.snake.view.item.food.Banana;
import com.neolumia.snake.view.item.food.Pear;
import com.neolumia.snake.view.item.questions.Questionlvl1;
import com.neolumia.snake.view.item.questions.Questionlvl2;
import com.neolumia.snake.view.item.questions.Questionlvl3;
import com.neolumia.snake.model.Question;
import com.neolumia.snake.model.QuestionLevel;
import com.neolumia.snake.model.Direction;
import com.neolumia.snake.model.Tile;
import com.neolumia.snake.view.item.TileObject;
import com.neolumia.snake.view.item.food.Food;
import com.neolumia.snake.view.*;
import com.neolumia.snake.view.item.questions.SEQuestion;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

public final class SingleSnake extends Snake<SingleGame> {
  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);
  public static MediaPlayer mp;
  public static boolean isMute; // check if the game is muted or not

  SingleSnake(SingleGame game) { //preparing the snake
    super(
        game,
        Direction.WEST,
        node -> {
          final Optional<TileObject> object = game.getTerrain().get(node.getTile());
          return object.isPresent()
              && !(object.get() instanceof Food
                  || object.get() instanceof SEQuestion
                  || object.get() instanceof Mouse); // &&
        });
    isMute=false;
  }

  /** Initiates Snake, and/or Clearing part from previous Life. */
  @Override
  public void init() {

    Iterator iterator = getParts().iterator();

    while (iterator.hasNext()) {
      SnakePartView sp = (SnakePartView) iterator.next();
      Tile tile = game.getTerrain().getTile(sp.getX(), sp.getY()).get();
      game.getTerrain().put(tile, null);
    }
    this.getParts().clear();
    for (int i = 5; i > 0; i--) {
      final int x = game.getTerrain().getTileWidth() / 2 + i;
      final int y = game.getTerrain().getTileHeight() / 2;
      addPart(game.getTerrain().getTile(x, y).get(), Direction.EAST, true);
    }
  }

  /**
   * This method updates info according to the object that was eaten.
   *
   * @param tile - location
   * @param object - object that was eaten
   */
  @Override
  public void onEat(Tile tile, TileObject object) {
    game.getTerrain().put(tile, null);
    game.getFood().put(tile, false);
                            // check which instance we eat , apply a new timer for the next spawn and update the game points
    if (object instanceof Apple) {
      playOnEvent("Food.mp3");

      LOGGER.info(" {} eaten at  x={}, y={}", "Apple", tile.getTileX(), tile.getTileY());
      game.appleTimer = new SingleGame.ActiveTimer(5000);
      game.appleTimer.start();
      game.spawnApple(5000);
      game.setPoints(game.getPoints() + 10);
    }
    if (object instanceof Banana) {
      playOnEvent("Food.mp3");
      LOGGER.info(" {} eaten at  x={}, y={}", "Banana", tile.getTileX(), tile.getTileY());
      game.bananaTimer = new SingleGame.ActiveTimer(10000);
      game.setPoints(game.getPoints() + 15);
      game.bananaTimer.start();
      game.spawnBanana(10000);
    }
    if (object instanceof Pear) {  //pear only spawn in corners
      playOnEvent("Food.mp3");
      LOGGER.info(" {} eaten at  x={}, y={}", "Pear", tile.getTileX(), tile.getTileY());
      game.mouseTimer = new SingleGame.ActiveTimer(60000);
      game.setPoints(game.getPoints() + 20);

      int x = tile.getTileX();
      int y = tile.getTileY();

      if (x == 0 && y == 0) game.spawnPear(1);
      if (x == game.getTerrain().getTileWidth() - 1 && y == 0) {
        game.spawnPear(2);
      }
      if (x == 0 && y == game.getTerrain().getTileHeight() - 1) {
        game.spawnPear(3);
      }
      if (x == game.getTerrain().getTileWidth() - 1 && y == game.getTerrain().getTileHeight() - 1) {
        game.spawnPear(4);
      }
    }
    if (object instanceof Questionlvl1) {
      playOnEvent("Tome.mp3");
      game.setPaused(true);
      // GameWindow.isQuestion1 = true;
      popQuestion(QuestionLevel.ONE);
      game.getTerrain().put(tile, null);
      game.spawnQuestion(QuestionLevel.ONE);
    }
    if (object instanceof Questionlvl2) {
      playOnEvent("Tome.mp3");
      game.setPaused(true);
      // GameWindow.isQuestion2 = true;
      popQuestion(QuestionLevel.TWO);
      game.getTerrain().put(tile, null);
      game.spawnQuestion(QuestionLevel.TWO);
    }
    if (object instanceof Questionlvl3) {
      playOnEvent("Tome.mp3");
      game.setPaused(true);
      // GameWindow.isQuestion3 = true;
      popQuestion(QuestionLevel.THREE);
      game.getTerrain().put(tile, null);
      game.spawnQuestion(QuestionLevel.THREE);
    }
    if (object instanceof Mouse) {
      playOnEvent("Food.mp3");
      LOGGER.info(" {} eaten at  x={}, y={}", "Mouse", tile.getTileX(), tile.getTileY());
      game.setPoints(game.getPoints() + 30);
      lives++;
      game.setLives(lives);
      game.mouseObj = null;
      game.mouse_tile = null;
      game.mouseDirection = null;
      game.counter = 0;
      game.mouseTimer.start();
      game.spawnMouse(60000);
    }
  }

  public void playOnEvent(String fileName) {   //sounds play function
    if (!isMute) {
      String path = getClass().getResource("/sounds/" + fileName).toString();
      Media media = new Media(path);
      mp = new MediaPlayer(media);
      mp.play();
    }
  }
//TODO Move
  private void popQuestion(QuestionLevel level) {
    Popup popup = new Popup();
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            FXMLLoader loader =
                new FXMLLoader(
                    getClass().getResource('/' + "PopUpQuestion.fxml"), GameApp.getBundle());
            Question question = SysData.getInstance().getQuestion(game.getQuestions(), level);
            game.addQuestion(question);
            loader.setController(new PopUpQuestion(game, question));
            try {
              popup.getContent().add((Parent) loader.load());
            } catch (IOException e) {
              e.printStackTrace();
            }
            popup.show(game.app.getWindowManager().getStage());
            popup.requestFocus();
            popup.addEventHandler(
                KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                  @Override
                  public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE||event.getCode() == KeyCode.P) {}
                  }
                });
          }
        });
  }

  @Override
  protected Tile getClosestFood() {   // a function to find the closets food for the snake
    Tile closeTile = null;
    double distance = 10000;
    for (Tile tile : game.getFood().keySet()) {
      if (game.getFood().get(tile)) {
        if (closeTile == null) closeTile = tile;
        else {

          if (game.tilesDistance(tile, this.getParts().getFirst().getTile()) < distance)
            closeTile = tile;
        }
      }
    }
    return closeTile;
  }

  @Override
  protected void onQuestion(Tile tile, TileObject tileObject) {}
}
