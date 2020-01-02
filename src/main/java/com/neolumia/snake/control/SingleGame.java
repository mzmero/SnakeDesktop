package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.questions.QuestionLevel;
import com.neolumia.snake.view.item.TileObject;
import com.neolumia.snake.view.item.questions.Questionlvl1;
import com.neolumia.snake.view.item.questions.Questionlvl2;
import com.neolumia.snake.view.item.questions.Questionlvl3;
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

import java.util.*;

/**
 * This class represents a single game - one player - and the snake associated with
 */
public final class SingleGame extends Game {

  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);
  private final SingleSnake snake = new SingleSnake(this);
  private HashMap<Tile,Boolean> food = new HashMap<>() ;
  private Tile question;
  private Tile mouse_tile;
  private Mouse mouseObj;
  private ArrayList<Question> questions;

  public SingleGame(GameApp app, GameType type) {
    super(app, type);
    questions = new ArrayList<>();
  }

  @Override
  protected void moveMouse() {
    if(mouse_tile!=null){
      int Y=mouse_tile.getTileY();
      int X=mouse_tile.getTileX();
      int nextY=Y;
      int nextX=X;
      // final Optional<Tile> tile = getTerrain().getTile(nextX, nextY);
       Optional<Tile> tile = null;
      Optional<TileObject> item = null;
      Tile t=snake.getParts().getFirst().getTile();
      if(calculateDistanceBetweenPoints(X,Y,t.getTileX(),t.getTileY())>10){
        ArrayList<Integer> arr= new ArrayList<Integer>();
        tile=getTerrain().getTile(++nextX,nextY);
        nextY=Y;
        nextX=X;
        if(tile.isPresent()) {
          item=getTerrain().get(tile.get());
           if(!item.isPresent())  arr.add(0);
        }

          tile=getTerrain().getTile(nextX,++nextY);
        nextY=Y;
        nextX=X;
          if(tile.isPresent()) {
            item = getTerrain().get(tile.get());
            if (!item.isPresent()) arr.add(1);
          }
          tile=getTerrain().getTile(--nextX,nextY);
        nextY=Y;
        nextX=X;
          if(tile.isPresent()) {
            item = getTerrain().get(tile.get());
            if (!item.isPresent()) arr.add(2);
          }
        tile=getTerrain().getTile(nextX,--nextY);
        nextY=Y;
        nextX=X;
        if(tile.isPresent()) {
          item = getTerrain().get(tile.get());
          if (!item.isPresent())arr.add(3);
        }

        if(!arr.isEmpty()) {
          Random rand= new Random();
          int num= rand.nextInt(3);

          while(!arr.contains(num)) num=rand.nextInt(3);

          switch (num){
            case 0: X++; break;
            case 1:Y++; break;
            case 2:X--; break;
            case 3:Y--; break;
          }
         getTerrain().put(mouse_tile, null);

          tile =getTerrain().getTile(X,Y);
          getTerrain().put(mouse_tile=tile.get(), mouseObj);

        }


      }


    }
  }

  @Override
  public void init() {
    snake.init();
    initSpawnFood();
    intSpawnMouse();
  }

  private void intSpawnMouse() {
    Tile tile = getTile();
    mouseObj = new Mouse();
    getTerrain().put(mouse_tile = tile, mouseObj);
    LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
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
        && !(Item.getItems().get(argument).get() instanceof Pear) &&  !(Item.getItems().get(argument).get() instanceof Mouse)) {
        Item item = Item.getItems().get(argument).get();
        tile = getTile();
        food.put(tile,true);
        getTerrain().put(tile, item);
        LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
      }
    }
    {
      int corner = 1 + random.nextInt(4);
      int x = 0, y = 0;
      switch (corner) {
        case 1: { // UpperRight
          x = 0;
          y = 0;
          break;
        }
        case 2: { // UpperLeft
          x = terrain.getTileWidth() - 1;
          y = 0;
          break;
        }
        case 3: { // LowerRight
          x = 0;
          y = terrain.getTileHeight() - 1;
          break;
        }
        case 4: {
          x = terrain.getTileWidth() - 1;
          y = terrain.getTileHeight() - 1;
          break;
        }
      }
      final Optional<Tile> next = terrain.getTile(x, y);
      if (next.isPresent() && !getTerrain().get(next.get()).isPresent()) {
        tile = next.get();
        food.put(tile,true);
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

  public HashMap<Tile,Boolean> getFood() {
    return food;
  }

  public void spawnApple() {
    Tile tile = getTile();
    food.put(tile,true);
    getTerrain().put(tile, new Apple());
    LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
    /*
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
      5000);*/
  }

  public void spawnBanana() {
    Timer t = new java.util.Timer();
    t.schedule(
      new java.util.TimerTask() {
        @Override
        public void run() {
          Tile tile = getTile();
          food.put(tile,true);
          getTerrain().put(tile, new Banana());
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

    int x = 0, y = 0;
    switch (corner) {
      case 1: { // UpperRight
        x = 0;
        y = 0;
        break;
      }
      case 2: { // UpperLeft
        x = terrain.getTileWidth() - 1;
        y = 0;
        break;
      }
      case 3: { // LowerRight
        x = 0;
        y = terrain.getTileHeight() - 1;
        break;
      }
      case 4: {
        x = terrain.getTileWidth() - 1;
        y = terrain.getTileHeight() - 1;
        break;
      }
    }
    final Optional<Tile> next = terrain.getTile(x, y);
    if (next.isPresent() && !getTerrain().get(next.get()).isPresent()) {
      tile = next.get();
      food.put(tile,true);
      getTerrain().put(tile, new Pear());
    }
    LOGGER.info("Item spawned at x={}, y={}", x, y);
  }

  public void spawnQuestion(QuestionLevel level) {
    Tile tileQuestion = getTile();
    final Optional<Item> itemQuestion = Item.random(type, ItemType.QUESTION);
    if (level.equals(QuestionLevel.ONE)) {
      getTerrain().put(question = tileQuestion, new Questionlvl1());
      food.put(tileQuestion,true);
    }
    if (level.equals(QuestionLevel.TWO)){
      food.put(tileQuestion,true);
      getTerrain().put(question = tileQuestion, new Questionlvl2());
    }
    if (level.equals(QuestionLevel.THREE)) {
      food.put(tileQuestion,true);
      getTerrain().put(question = tileQuestion, new Questionlvl3());
    }
    LOGGER.info("Question spawned x={}, y={}", tileQuestion.getTileX(), tileQuestion.getTileY());
  }

  public void spawnMouse() {
    Timer t = new java.util.Timer();
    mouseObj=null;
    mouse_tile=null;
    t.schedule(
      new java.util.TimerTask() {
        @Override
        public void run() {
          Tile tile = getTile();
          mouseObj=new Mouse();
          getTerrain().put(mouse_tile= tile, mouseObj);
          food.put(tile,true);
          LOGGER.info("mouse spawned x={}, y={}", tile.getTileX(), tile.getTileY());
          // close the thread
          t.cancel();
        }
      },
      60000);

  }

  public void addQuestion(Question question) {
    questions.add(question);
  }
  public double calculateDistanceBetweenPoints(
    double x1,
    double y1,
    double x2,
    double y2) {
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
  }
  public double tilesDistance( Tile a , Tile b) {
    return Math.sqrt(b.getTileY() - a.getTileY()) * (((b.getTileY() - a.getTileY()) + ((b.getTileX() - a.getTileX()) * b.getTileX())) - a.getTileX());

  }
}
