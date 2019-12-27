
package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.game.SnakePart;
import com.neolumia.snake.model.item.food.Apple;
import com.neolumia.snake.model.item.food.Banana;
import com.neolumia.snake.model.item.food.Pear;
import com.neolumia.snake.model.item.questions.Questionlvl1;
import com.neolumia.snake.model.item.questions.Questionlvl2;
import com.neolumia.snake.model.item.questions.Questionlvl3;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;
import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.model.game.Tile;
import com.neolumia.snake.model.game.TileObject;
import com.neolumia.snake.model.item.food.Food;
import com.neolumia.snake.view.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Popup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

public final class SingleSnake extends Snake<SingleGame> {
  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);

  SingleSnake(SingleGame game) {
    super(game, Direction.WEST, node -> {
      final Optional<TileObject> object = game.getTerrain().get(node.getTile());
      return object.isPresent() && !(object.get() instanceof Food);
    });
  }

  /**
   * Initiates Snake, and/or Clearing part from previous Life.
   */
  @Override
  public void init() {

    Iterator iterator = getParts().iterator();

    while (iterator.hasNext()) {
      SnakePart sp = (SnakePart) iterator.next();
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
   * @param tile   - location
   * @param object - object that was eaten
   */
  @Override
  public void onEat(Tile tile, TileObject object) {
    game.getTerrain().put(tile, null);

    if (object instanceof Apple) {
      LOGGER.info(" {} eaten at  x={}, y={}", "Apple", tile.getTileX(), tile.getTileY());
      game.spawnApple();
      game.setPoints(game.getPoints() + 10);
    }
    if (object instanceof Banana) {
      LOGGER.info(" {} eaten at  x={}, y={}", "Banana", tile.getTileX(), tile.getTileY());
      game.setPoints(game.getPoints() + 15);
      game.spawnBanana();
    }
    if (object instanceof Pear) {
      LOGGER.info(" {} eaten at  x={}, y={}", "Pear", tile.getTileX(), tile.getTileY());
      game.setPoints(game.getPoints() + 20);


      int x = tile.getTileX();
      int y = tile.getTileY();
      System.out.print(x + " " + y);
      if (x == 0 && y == 0)
        game.spawnPear(1);
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
      GameWindow.isQuestion1 = true;
      popQuestion(QuestionLevel.ONE);
    }
    if (object instanceof Questionlvl2) {
      GameWindow.isQuestion2 = true;
      popQuestion(QuestionLevel.TWO);
    }
    if (object instanceof Questionlvl3) {
      GameWindow.isQuestion3 = true;
      popQuestion(QuestionLevel.THREE);
    }
  }

  private void popQuestion(QuestionLevel level) {
    Popup popup = new Popup();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource('/' + "PopUpQuestion.fxml"), GameApp.getBundle());
        Question question = SysData.getInstance().getQuestion(game.getQuestions(), level);
        game.addQuestion(question);
        loader.setController(new PopUpQuestion(question));

        try {
          popup.getContent().add((Parent) loader.load());
        } catch (IOException e) {
          e.printStackTrace();
        }
        popup.show(game.app.getWindowManager().getStage());
      }
    });
  }

  @Override
  public void onQuestion(Tile tile, TileObject object) {
    game.spawnQuestion();
    game.getTerrain().put(tile, null);
//    if (object instanceof Apple)
//      game.setPoints(game.getPoints() + 10);
//    if (object instanceof Banana)
//      game.setPoints(game.getPoints() + 15);
//    if (object instanceof Pear)
//      game.setPoints(game.getPoints() + 20);
  }

  @Override
  protected int getFoodX() {
    return game.getFood().getTileX();
  }

  @Override
  protected int getFoodY() {
    return game.getFood().getTileY();
  }
}
