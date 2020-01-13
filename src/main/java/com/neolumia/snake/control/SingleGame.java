package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.QuestionLevel;
import com.neolumia.snake.model.Direction;
import com.neolumia.snake.view.item.TileObject;
import com.neolumia.snake.view.item.questions.Questionlvl1;
import com.neolumia.snake.view.item.questions.Questionlvl2;
import com.neolumia.snake.view.item.questions.Questionlvl3;
import com.neolumia.snake.view.option.GameType;
import com.neolumia.snake.model.Tile;
import com.neolumia.snake.view.item.Item;
import com.neolumia.snake.view.item.ItemType;
import com.neolumia.snake.view.item.Mouse;
import com.neolumia.snake.view.item.food.Apple;
import com.neolumia.snake.view.item.food.Banana;
import com.neolumia.snake.view.item.food.Pear;
import com.neolumia.snake.model.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
/** This class represents a single game - one player - and the snake associated with */
public final class SingleGame extends Game {

  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);
  private final SingleSnake snake = new SingleSnake(this);
  private HashMap<Tile, Boolean> food = new HashMap<>();
  private Tile question;
  public Tile mouse_tile;
  public Mouse mouseObj;
  public Direction mouseDirection;
  private ArrayList<Question> questions;
  public static int counter = 0;
  public static ActiveTimer bananaTimer = new ActiveTimer(10000);
  public static ActiveTimer appleTimer = new ActiveTimer(5000);
  public static ActiveTimer mouseTimer = new ActiveTimer(60000);

  public SingleGame(GameApp app, GameType type) {
    super(app, type);
    questions = new ArrayList<>();
  }

  /**
   * this method is responsible to stop the game
   *
   * @param paused - true -> pause , false -> resume
   * @return
   */
  @Override
  public boolean setPaused(boolean paused) {
    LOGGER.info("game setPaused {}", paused);

    if (paused == true) { // stop active timer;
      if (appleTimer.isActive()) {
        appleTimer.stop();
      }
      if (bananaTimer.isActive()) {
        bananaTimer.stop();
      }
      if (mouseTimer.isActive()) {
        mouseTimer.stop();
      }
    }
    if (paused == false) {
      // resume game
      // resume active timer;

      if (appleTimer.isActive()) {
        long timeRemain = appleTimer.getRemainingTime();
        LOGGER.info("ini new timer with  {}", timeRemain);
        appleTimer = new ActiveTimer(timeRemain);
        appleTimer.start();
        spawnApple(timeRemain);
      }
      if (bananaTimer.isActive()) {
        long timeRemain = bananaTimer.getRemainingTime();
        LOGGER.info("ini new timer with  {}", timeRemain);
        bananaTimer = new ActiveTimer(timeRemain);
        bananaTimer.start();
        spawnBanana(timeRemain);
      }
      if (mouseTimer.isActive()) {
        long timeRemain = mouseTimer.getRemainingTime();
        LOGGER.info("ini new timer with  {}", timeRemain);
        mouseTimer = new ActiveTimer(timeRemain);
        mouseTimer.start();
        spawnMouse(timeRemain);
      }
    }
    return this.paused = paused;
  }

  @Override
  public void init() {

    initSpawnFood();
    intSpawnMouse();
    snake.init(); // TODO delay 2 sec
  }

  protected void RandomMove() {
    int Y = mouse_tile.getTileY();
    int X = mouse_tile.getTileX();
    int nextY = Y;
    int nextX = X;
    Optional<Tile> tile = null;
    Optional<TileObject> item = null;
    ArrayList<Integer> arr = new ArrayList<Integer>();
    tile = getTerrain().getTile(nextX + 1, nextY);
    if (checkifTileIsValid(tile)) arr.add(0);

    tile = getTerrain().getTile(nextX, nextY + 1);
    if (checkifTileIsValid(tile)) arr.add(1);

    tile = getTerrain().getTile(nextX - 1, nextY);
    if (checkifTileIsValid(tile)) arr.add(2);

    tile = getTerrain().getTile(nextX, nextY - 1);
    if (checkifTileIsValid(tile)) arr.add(3);

    if (!arr.isEmpty()) {
      Random rand = new Random();
      int num = rand.nextInt(4);
      while (!arr.contains(num)) num = rand.nextInt(3);
      switch (num) {
        case 0:
          X++;
          mouseDirection = Direction.EAST;
          break;
        case 1:
          Y++;
          mouseDirection = Direction.SOUTH;
          break;
        case 2:
          X--;
          mouseDirection = Direction.WEST;
          break;
        case 3:
          Y--;
          mouseDirection = Direction.NORTH;
          break;
      }
      getTerrain().put(mouse_tile, null);
      tile = getTerrain().getTile(X, Y);
      getTerrain().put(mouse_tile = tile.get(), mouseObj);
    }
  }

  public boolean checkifTileIsValid(Optional<Tile> t) {
    if (!t.isPresent()) return false;
    Optional<TileObject> item = getTerrain().get(t.get());
    if (item.isPresent()) return false;
    return true;
  }

  /** this method is responsible to direct the mouse to move on board */
  @Override
  protected void moveMouse() {
    if (mouse_tile != null) {
      if (mouseDirection == null) {
        RandomMove();
        counter++;
      } else {
        int x = mouse_tile.getTileX();
        int y = mouse_tile.getTileY();
        Tile head = snake.getParts().getFirst().getTile();
        Optional<Tile> t = null;
        if (calculateDistanceBetweenPoints(x, y, head.getTileX(), head.getTileY()) > 4) {
          if (counter % 10 != 0) {
            switch (mouseDirection) {
              case NORTH:
                t = getTerrain().getTile(x, --y);
                MouseTransfer(t);
                counter++;
                break;
              case SOUTH:
                t = getTerrain().getTile(x, ++y);
                MouseTransfer(t);
                counter++;
                break;
              case EAST:
                t = getTerrain().getTile(++x, y);
                MouseTransfer(t);
                counter++;
                break;
              case WEST:
                t = getTerrain().getTile(--x, y);
                MouseTransfer(t);
                counter++;
                break;
            }
          } else {
            RandomMove();
            counter = 1;
          }

        } else {

          if (calculateDistanceBetweenPoints(0, 0, x, y)
              < calculateDistanceBetweenPoints(x, y, head.getTileX(), head.getTileY())) {
            bestDirectionForaGivenCordinates(x, y, 0, 0);

          } else if (calculateDistanceBetweenPoints(0, getTerrain().getTileHeight(), x, y)
              < calculateDistanceBetweenPoints(x, y, head.getTileX(), head.getTileY())) {
            bestDirectionForaGivenCordinates(x, y, 0, getTerrain().getTileHeight());

          } else if (calculateDistanceBetweenPoints(getTerrain().getTileWidth(), 0, x, y)
              < calculateDistanceBetweenPoints(x, y, head.getTileX(), head.getTileY())) {
            bestDirectionForaGivenCordinates(x, y, getTerrain().getTileWidth(), 0);

          } else if (calculateDistanceBetweenPoints(
                  getTerrain().getTileWidth(), getTerrain().getTileHeight(), x, y)
              < calculateDistanceBetweenPoints(x, y, head.getTileX(), head.getTileY())) {
            bestDirectionForaGivenCordinates(
                x, y, getTerrain().getTileWidth(), getTerrain().getTileHeight());

          } else {
            bestDirectionForaGivenCordinates(x, y, head.getTileX(), head.getTileY());
          }
        }
      }
    }
  }

  private void MouseTransfer(Optional<Tile> t) {
    if (!t.isPresent()) {
      RandomMove();
      return;
    }
    Optional<TileObject> item = getTerrain().get(t.get());
    if (item.isPresent()) {
      RandomMove();
      return;
    }
    getTerrain().put(mouse_tile, null);
    getTerrain().put(mouse_tile = t.get(), mouseObj);
  }

  /**
   * The best direction is for the mouse to move on the board
   *
   * @param x
   * @param y
   * @param againtsX
   * @param againtsY
   */
  public void bestDirectionForaGivenCordinates(int x, int y, int againtsX, int againtsY) {
    Direction dire = null;
    double temp, maxDistance = -1;
    Optional<Tile> t = getTerrain().getTile(x, y - 1);
    if (checkifTileIsValid(t)) {
      temp = calculateDistanceBetweenPoints(x, y - 1, againtsX, againtsY);
      if (temp > maxDistance) {
        maxDistance = temp;
        dire = Direction.NORTH;
      }
    }

    t = getTerrain().getTile(x, y + 1);
    if (checkifTileIsValid(t)) {
      temp = calculateDistanceBetweenPoints(x, y + 1, againtsX, againtsY);
      if (temp > maxDistance) {
        maxDistance = temp;
        dire = Direction.SOUTH;
      }
    }

    t = getTerrain().getTile(x + 1, y);
    if (checkifTileIsValid(t)) {
      temp = calculateDistanceBetweenPoints(x + 1, y, againtsX, againtsY);
      if (temp > maxDistance) {
        maxDistance = temp;
        dire = Direction.EAST;
      }
    }
    t = getTerrain().getTile(x - 1, y);
    if (checkifTileIsValid(t)) {
      temp = calculateDistanceBetweenPoints(x - 1, y, againtsX, againtsY);
      if (temp > maxDistance) {
        maxDistance = temp;
        dire = Direction.WEST;
      }
    }

    if (dire != null) {
      switch (dire) {
        case NORTH:
          mouseDirection = dire;
          MouseTransfer(getTerrain().getTile(x, y - 1));
          break;
        case SOUTH:
          mouseDirection = dire;
          MouseTransfer(getTerrain().getTile(x, y + 1));
          break;
        case EAST:
          mouseDirection = dire;
          MouseTransfer(getTerrain().getTile(x + 1, y));
          break;
        case WEST:
          mouseDirection = dire;
          MouseTransfer(getTerrain().getTile(x - 1, y));
          break;
        default:
          break;
      }
    }
  }

  /** Init spawn the mouse to move */
  private void intSpawnMouse() {
    Tile tile = getTile();
    mouseObj = new Mouse();
    getTerrain().put(mouse_tile = tile, mouseObj);
    LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
  }

  public ArrayList<Question> getQuestions() {
    return questions;
  }

  /**
   * Initiates the questions of the game
   *
   * @param questions
   */
  public void setQuestions(ArrayList<Question> questions) {
    this.questions = questions;
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

  /** this method initiates food on the board */
  public void initSpawnFood() {
    Tile tile;
    for (Item.Arguments argument : Item.getItems().keySet()) {
      if (argument.getGameTypes()[0] == GameType.CLASSIC
          && !(Item.getItems().get(argument).get() instanceof Pear)
          && !(Item.getItems().get(argument).get() instanceof Mouse)) {
        Item item = Item.getItems().get(argument).get();
        tile = getTile();
        food.put(tile, true);
        getTerrain().put(tile, item);
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
        food.put(tile, true);
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

  public HashMap<Tile, Boolean> getFood() {
    return food;
  }

  /**
   * This method initiates the apple of the board in a random tile on board
   *
   * @param remainingTime
   */
  public void spawnApple(long remainingTime) {
    appleTimer.setActive(true);
    appleTimer.schedule(
        new java.util.TimerTask() {
          @Override
          public void run() {

            Tile tile = getTile();
            food.put(tile, true);
            getTerrain().put(tile, new Apple());
            LOGGER.info("apple spawned x={}, y={}", tile.getTileX(), tile.getTileY());
            // close the thread
            appleTimer.setActive(false);
            appleTimer.cancel();
          }
        },
        remainingTime);
  }

  /**
   * this method locates the banana in a random empty tile on board
   *
   * @param remainingTime
   */
  public void spawnBanana(long remainingTime) {
    bananaTimer.setActive(true);
    bananaTimer.schedule(
        new java.util.TimerTask() {
          @Override
          public void run() {
            Tile tile = getTile();
            food.put(tile, true);
            getTerrain().put(tile, new Banana());
            LOGGER.info("Banana spawned x={}, y={}", tile.getTileX(), tile.getTileY());
            // close the thread
            bananaTimer.setActive(false);
            bananaTimer.cancel();
          }
        },
        remainingTime);
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
      food.put(tile, true);
      getTerrain().put(tile, new Pear());
    }
    LOGGER.info("Item spawned at x={}, y={}", x, y);
  }


  public void spawnQuestion(QuestionLevel level) {
    Tile tileQuestion = getTile();
    final Optional<Item> itemQuestion = Item.random(type, ItemType.QUESTION);
    if (level.equals(QuestionLevel.ONE)) {
      getTerrain().put(question = tileQuestion, new Questionlvl1());
      food.put(tileQuestion, true);
    }
    if (level.equals(QuestionLevel.TWO)) {
      food.put(tileQuestion, true);
      getTerrain().put(question = tileQuestion, new Questionlvl2());
    }
    if (level.equals(QuestionLevel.THREE)) {
      food.put(tileQuestion, true);
      getTerrain().put(question = tileQuestion, new Questionlvl3());
    }
    LOGGER.info("Question spawned x={}, y={}", tileQuestion.getTileX(), tileQuestion.getTileY());
  }

  public void spawnMouse(long remainTime) {
    mouseTimer.setActive(true);
    mouseTimer.schedule(
        new java.util.TimerTask() {
          @Override
          public void run() {
            Tile tile = getTile();
            mouseObj = new Mouse();
            getTerrain().put(mouse_tile = tile, mouseObj);
            food.put(tile, true);
            LOGGER.info("mouse spawned x={}, y={}", tile.getTileX(), tile.getTileY());
            // close the thread
            mouseTimer.setActive(false);
            mouseTimer.cancel();
          }
        },
        remainTime);
  }

  public void addQuestion(Question question) {
    questions.add(question);
  }

  public double calculateDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
  }

  public double tilesDistance(Tile a, Tile b) {
    return Math.sqrt(b.getTileY() - a.getTileY())
        * (((b.getTileY() - a.getTileY()) + ((b.getTileX() - a.getTileX()) * b.getTileX()))
            - a.getTileX());
  }
  /**
   * This class is implemented to allow a timer to be stopped and resume remaining timer when ever
   * game is paused or when even a wild question appear
   */
  static class ActiveTimer extends Timer {

    private boolean isActive;
    private long timerDuration;
    private long startTime;
    private long remainTime;

    ActiveTimer(long timerDuration) {
      isActive = false;
      this.timerDuration = timerDuration;
    }

    public boolean isActive() {
      return isActive;
    }

    public long getRemainingTime() {
      return remainTime;
    }

    public void setTimerDuration(int timerDuration) {
      this.timerDuration = timerDuration;
    }

    public void setActive(boolean active) {
      isActive = active;
    }

    public void start() {
      startTime = System.currentTimeMillis();
      LOGGER.info("Timer Started at {}", startTime);
      setActive(true);
    }

    public void stop() {

      this.remainTime = (timerDuration - (System.currentTimeMillis() - this.startTime));
      LOGGER.info(
          "Remaining time is {} - ( {} - {} ) = {} ",
          timerDuration,
          System.currentTimeMillis(),
          this.startTime,
          this.remainTime);

      this.cancel();
    }
  }
}
