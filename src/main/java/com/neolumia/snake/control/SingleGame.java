package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.questions.QuestionLevel;
import com.neolumia.snake.model.util.Direction;
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

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;

/**
 * This class represents a single game - one player - and the snake associated with
 */
public final class SingleGame extends Game {

  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);
  private final SingleSnake snake = new SingleSnake(this);
  private Tile food;
  private Tile question;
  private Tile mouse_tile;
  private Mouse mouseObj;
  private Direction mouseDirection;
  private ArrayList<Question> questions;
  static private int counter=0;

  public SingleGame(GameApp app, GameType type) {
    super(app, type);
    questions = new ArrayList<>();
  }
   protected void RandomMove(){
    int Y=mouse_tile.getTileY();
    int X=mouse_tile.getTileX();
    int nextY=Y;
    int nextX=X;
    // final Optional<Tile> tile = getTerrain().getTile(nextX, nextY);
    Optional<Tile> tile = null;
    Optional<TileObject> item = null;
      ArrayList<Integer> arr= new ArrayList<Integer>();
      tile=getTerrain().getTile(++nextX,nextY);
      if(checkifTileIsValid(tile)) arr.add(0);
     nextY=Y;
     nextX=X;
      tile=getTerrain().getTile(nextX,++nextY);
      if(checkifTileIsValid(tile)) arr.add(1);
     nextY=Y;
     nextX=X;
      tile=getTerrain().getTile(--nextX,nextY);
      if(checkifTileIsValid(tile)) arr.add(2);
     nextY=Y;
     nextX=X;
      tile=getTerrain().getTile(nextX,--nextY);
      if(checkifTileIsValid(tile)) arr.add(3);
      if(!arr.isEmpty()) {
        System.out.println(arr);
        Random rand= new Random();
        int num= rand.nextInt(4);

        while(!arr.contains(num)) num=rand.nextInt(3);
        switch (num){
          case 0: X++; mouseDirection=Direction.EAST; break;
          case 1:Y++; mouseDirection=Direction.SOUTH; break;
          case 2:X--; mouseDirection=Direction.WEST; break;
          case 3:Y--; mouseDirection=Direction.NORTH; break;
        }
        getTerrain().put(mouse_tile, null);
        tile =getTerrain().getTile(X,Y);
        getTerrain().put(mouse_tile=tile.get(), mouseObj);

      }
    }
    public boolean checkifTileIsValid(Optional<Tile> t){
    if(!t.isPresent()) return false;
      Optional<TileObject> item =  getTerrain().get(t.get());
      if(item.isPresent()) return false;
      return true;
    }

  @Override
  protected void moveMouse() {
    if(mouse_tile!=null){
       if(mouseDirection==null){
         RandomMove();
         counter++;
       }else {
         int x= mouse_tile.getTileX();
         int y=mouse_tile.getTileY();
         Tile head=snake.getParts().getFirst().getTile();
         Optional<Tile> t =null;
         if(calculateDistanceBetweenPoints(x,y,head.getTileX(),head.getTileY())>7){
           if(counter%10!=0){
         switch (mouseDirection){
           case NORTH:
             t=getTerrain().getTile(x,--y);
             MouseTransfer(t);
             counter++;
             break;
           case SOUTH:
             t=getTerrain().getTile(x,++y);
             MouseTransfer(t);
             counter++;
             break;
           case EAST:
             t=getTerrain().getTile(++x,y);
             MouseTransfer(t);
             counter++;
             break;
           case WEST:
             t=getTerrain().getTile(--x,y);
             MouseTransfer(t);
             counter++;
             break;
         } } else {RandomMove(); counter=1;
             }


         }
         else {
           Direction dire=null;
           double temp,maxDistance=-1;
            t= getTerrain().getTile(x,y-1);
           if(checkifTileIsValid(t)){
              temp=calculateDistanceBetweenPoints(x,y-1,head.getTileX(),head.getTileY());
             if(temp>maxDistance) {
               maxDistance=temp;
               dire=Direction.NORTH;
             } }

           t= getTerrain().getTile(x,y+1);
           if(checkifTileIsValid(t)){
              temp=calculateDistanceBetweenPoints(x,y+1,head.getTileX(),head.getTileY());
             if(temp>maxDistance) {
               maxDistance=temp;
               dire=Direction.SOUTH;
             } }

           t= getTerrain().getTile(x+1,y);
           if(checkifTileIsValid(t)){
              temp=calculateDistanceBetweenPoints(x+1,y,head.getTileX(),head.getTileY());
             if(temp>maxDistance) {
               maxDistance=temp;
               dire=Direction.EAST;
             } }
           t= getTerrain().getTile(x-1,y);
           if(checkifTileIsValid(t)){
              temp=calculateDistanceBetweenPoints(x-1,y,head.getTileX(),head.getTileY());
             if(temp>maxDistance) {
               maxDistance=temp;
               dire=Direction.WEST;
             } }

           if(calculateDistanceBetweenPoints(0,0,x,y)< calculateDistanceBetweenPoints(x,y,head.getTileX(),head.getTileY())){



           }else  if(calculateDistanceBetweenPoints(0,getTerrain().getTileHeight(),x,y)< calculateDistanceBetweenPoints(x,y,head.getTileX(),head.getTileY())){


           }else  if(calculateDistanceBetweenPoints(getTerrain().getTileWidth(),0,x,y)< calculateDistanceBetweenPoints(x,y,head.getTileX(),head.getTileY())){


           }else  if(calculateDistanceBetweenPoints(getTerrain().getTileWidth(),getTerrain().getTileHeight(),x,y)< calculateDistanceBetweenPoints(x,y,head.getTileX(),head.getTileY())){


           }

           if(dire!=null){
             switch (dire){
               case NORTH: mouseDirection=dire;
               MouseTransfer(getTerrain().getTile(x,y-1));
                 break;
               case SOUTH:
                 mouseDirection=dire;
                 MouseTransfer(getTerrain().getTile(x,y+1));
                 break;
               case EAST: mouseDirection=dire;
                 MouseTransfer(getTerrain().getTile(x+1,y));
                 break;
               case WEST:
                 mouseDirection=dire;
                 MouseTransfer(getTerrain().getTile(x-1,y));
                 break;
               default:break;
             } }




         }
       }}
  }


  private void MouseTransfer(Optional<Tile> t) {
     if(!t.isPresent()) {
       RandomMove();
       return;
     }
    Optional<TileObject> item =  getTerrain().get(t.get());
     if(item.isPresent()) {
       RandomMove();
       return;
     }
       getTerrain().put(mouse_tile, null);
       getTerrain().put(mouse_tile=t.get(), mouseObj);
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
        getTerrain().put(food = tile, item);
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
      getTerrain().put(tile, new Pear());
    }
    LOGGER.info("Item spawned at x={}, y={}", x, y);
  }

  public void spawnQuestion(QuestionLevel level) {
    Tile tileQuestion = getTile();
    final Optional<Item> itemQuestion = Item.random(type, ItemType.QUESTION);
    if (level.equals(QuestionLevel.ONE))
      getTerrain().put(question = tileQuestion, new Questionlvl1());
    if (level.equals(QuestionLevel.TWO))
      getTerrain().put(question = tileQuestion, new Questionlvl2());
    if (level.equals(QuestionLevel.THREE))
      getTerrain().put(question = tileQuestion, new Questionlvl3());
    LOGGER.info("Question spawned x={}, y={}", tileQuestion.getTileX(), tileQuestion.getTileY());
  }

  public void spawnMouse() {
    Timer t = new java.util.Timer();
    mouseObj=null;
    mouse_tile=null;
    mouseDirection=null;
    counter=0;
    t.schedule(
      new java.util.TimerTask() {
        @Override
        public void run() {
          Tile tile = getTile();
          mouseObj=new Mouse();
          getTerrain().put(mouse_tile= tile, mouseObj);
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
}
