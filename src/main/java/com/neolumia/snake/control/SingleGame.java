package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.view.option.GameType;
import com.neolumia.snake.view.game.Tile;
import com.neolumia.snake.view.item.Item;
import com.neolumia.snake.view.item.ItemType;
import com.neolumia.snake.view.item.Mouse;
import com.neolumia.snake.view.item.food.Apple;
import com.neolumia.snake.view.item.food.Banana;
import com.neolumia.snake.view.item.food.Pear;
import com.neolumia.snake.model.questions.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;

/** This class represents a single game - one player - and the snake associated with */
public final class SingleGame extends Game {

  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);
  private final SingleSnake snake = new SingleSnake(this);
  private Tile food;
  private Tile question;
  private Tile mouse_tile;
  private Mouse mouseObj;
  private ArrayList<Question> questions;

  public SingleGame(GameApp app, GameType type) {
    super(app, type);
    questions = new ArrayList<>();
  }

  @Override
  public void init() {
    snake.init();
    initSpawnFood();
  }

  public ArrayList<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(ArrayList<Question> questions) {
    this.questions = questions;
  }

  public void initSpawnFood() {
    Tile tile;
    for (Item.Arguments argument : Item.getItems().keySet()) {
      if (argument.getGameTypes()[0] == GameType.CLASSIC
          && !(Item.getItems().get(argument).get() instanceof Pear)) {
        Item item = Item.getItems().get(argument).get();
        tile = getTile();
        getTerrain().put(food = tile, item);
        LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
      }
    }
    {
      int corner = 1 + random.nextInt(4);
      int x = 0, y = 0;
      switch (corner) {
        case 1:
          { // UpperRight
            x = 0;
            y = 0;
            break;
          }
        case 2:
          { // UpperLeft
            x = terrain.getTileWidth() - 1;
            y = 0;
            break;
          }
        case 3:
          { // LowerRight
            x = 0;
            y = terrain.getTileHeight() - 1;
            break;
          }
        case 4:
          {
            x = terrain.getTileWidth() - 1;
            y = terrain.getTileHeight() - 1;
            break;
          }
      }
      final Optional<Tile> next = terrain.getTile(x, y);
      if (next.isPresent() && !getTerrain().get(next.get()).isPresent()) {
        tile = next.get();
        getTerrain().put(tile, new Pear());
        LOGGER.info(
            "Item spawned x={}, y={} , corener={} , corner.x={},corner.y={}",
            tile.getTileX(),
            tile.getTileY(),
            corner,
            x,
            y);
      }
    }
  }

  private Tile getTile() {
    Tile tile;
    while (true) {
      int x = random.nextInt(terrain.getTileWidth());
      int y = random.nextInt(terrain.getTileHeight());
      final Optional<Tile> next = terrain.getTile(x, y);
      if (next.isPresent() && !getTerrain().get(next.get()).isPresent()) {
        tile = next.get();
        break;
      }
    }
    return tile;
  }

  @Override
  public void tick() {
    snake.tick();
  }

  public SingleSnake getSnake() {
    return snake;
  }

  public Tile getFood() {
    return food;
  }

  public void spawnApple() {
    Timer t = new java.util.Timer();
    t.schedule(
        new java.util.TimerTask() {
          @Override
          public void run() {
            Tile tile = getTile();
            getTerrain().put(food = tile, new Apple());
            LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
            // close the thread
            t.cancel();
          }
        },
        5000);
  }

  public void spawnBanana() {
    Timer t = new java.util.Timer();
    t.schedule(
        new java.util.TimerTask() {
          @Override
          public void run() {
            Tile tile = getTile();
            getTerrain().put(food = tile, new Banana());
            LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
            // close the thread
            t.cancel();
          }
        },
        10000);
  }

  /**
   * Spawn a Pear givin previouse Corner
   *
   * @param corner 1 for UpperRight , 2 UpperLeft , 3 LowerRight , 4 LowerLeft
   */
  public void spawnPear(int corner) {
    Tile tile;
    while (true) {
      int x = 1 + random.nextInt(4);
      if (x != corner) {
        corner = x;
        break;
      }
    }
    System.out.print("new corner :" + corner);
    int x = 0, y = 0;
    switch (corner) {
      case 1:
        { // UpperRight
          x = 0;
          y = 0;
          break;
        }
      case 2:
        { // UpperLeft
          x = terrain.getTileWidth() - 1;
          y = 0;
          break;
        }
      case 3:
        { // LowerRight
          x = 0;
          y = terrain.getTileHeight() - 1;
          break;
        }
      case 4:
        {
          x = terrain.getTileWidth() - 1;
          y = terrain.getTileHeight() - 1;
          break;
        }
    }
    final Optional<Tile> next = terrain.getTile(x, y);
    if (next.isPresent() && !getTerrain().get(next.get()).isPresent()) {
      tile = next.get();
      getTerrain().put(tile, new Pear());
    }
    LOGGER.info("Item spawned at x={}, y={}", x, y);
  }

  public void spawnQuestion() {
    Tile tileQuestion = getTile();
    final Optional<Item> itemQuestion = Item.random(type, ItemType.FOOD);
    getTerrain().put(question = tileQuestion, itemQuestion.orElseThrow(IllegalStateException::new));
    LOGGER.info("Question spawned x={}, y={}", tileQuestion.getTileX(), tileQuestion.getTileY());
  }

  public void addQuestion(Question question) {
    questions.add(question);
  }
}
