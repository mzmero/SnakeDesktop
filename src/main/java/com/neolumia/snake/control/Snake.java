
package com.neolumia.snake.control;

import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.game.SnakePart;
import com.neolumia.snake.model.game.Tile;
import com.neolumia.snake.model.game.TileObject;
import com.neolumia.snake.model.item.Item;
import com.neolumia.snake.model.item.ItemType;
import com.neolumia.snake.model.item.food.Food;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.solver.Node;
import com.neolumia.snake.model.solver.Solver;
import com.neolumia.snake.model.util.Direction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;

public abstract class Snake<T extends Game> {

  private final ConcurrentLinkedDeque<SnakePart> parts = new ConcurrentLinkedDeque<>();

  protected final T game;
  private final Predicate<Node> blocking;
  private final int speed;

  private Direction direction;
  private int ticks = 0;
  private int lives;
  @Nullable
  private Direction next;


  public Snake(T game, Direction direction, Predicate<Node> blocking) {
    this.game = game;
    this.direction = direction;
    this.blocking = blocking;
    this.speed = game.getSettings().difficulty.getSpeed();
    //TODO lives must be in game
    this.lives = 3;
  }

  public abstract void init();

  public abstract void onEat(Tile tile, TileObject object);

  public void tick() {
    while (true) {

      if (ticks % (game.isAuto() ? speed / 2 : speed) == 0) {
        move();
      }
      ticks++;
      break;
    }


  }

  public Deque<SnakePart> getParts() {
    return parts;
  }

  public void setNext(@Nullable Direction next) {
    if (direction != null && direction.opposite() == next) {
      return;
    }
    this.next = next;
  }

  void setSize(int size) {
    parts.forEach(p -> p.setSize(size));
  }

  protected SnakePart createPart(Tile tile, Direction direction) {
    return game.getApp().getDesign().snake.getPart().get(this, tile, direction, null);
  }

  protected void addPart(Tile tile, Direction direction, boolean head) {
    final SnakePart sp = createPart(tile, direction);
    if (!parts.isEmpty()) {
      parts.getFirst().setP(sp);
      parts.forEach(SnakePart::update);
    }
    game.getTerrain().put(tile, sp);
    if (head) {
      parts.addFirst(sp);
      return;
    }
    parts.addLast(sp);
  }

  protected abstract int getFoodX();

  protected abstract int getFoodY();

  private boolean move() {

    if (parts.isEmpty()) {
      return false;
    }

    if (game.isAuto()) {
      Direction best = findBest();
      if (best == null) {
        best = findTail();
      }
      if (best == null) {
        for (Direction direction : Direction.values()) {
          if (moveable(direction)) {
            best = direction;
            break;
          }
        }
      }
      if (best != null) {
        direction = best;
      }
    } else {
      if (next != null) {
        direction = next;
        next = null;
      }
    }

    final Tile current = parts.getFirst().getTile();

    int nextX = current.getTileX();
    int nextY = current.getTileY();
    switch (direction) {
      case NORTH:
        nextY--;
        break;
      case SOUTH:
        nextY++;
        break;
      case EAST:
        nextX++;
        break;
      case WEST:
        nextX--;
        break;
    }

    final Optional<Tile> tile = game.getTerrain().getTile(nextX, nextY);

    if (!tile.isPresent()) {
      // TODO
      // -> Hit the wall -> check lived
      lives--;
      game.getStats().walls++;
      game.setLives(lives);
      if (lives == 0)
        game.end();
      else {

        game.getTerrain();
        this.init();

        game.setPaused(true);
      }
      return false;
    }

    boolean eat = false;
    boolean quest = false;

    final Optional<TileObject> item = game.getTerrain().get(tile.get());
    if (item.isPresent()) {

      if (item.get() instanceof SnakePart) {
        // Hit himself --> End
        lives--;
        game.setLives(lives);
        game.getStats().walls++;
        if (lives == 0)
          game.end();
        else {
          this.init();
          game.setPaused(true);
        }
        return false;

      }
/*      if (item.get() instanceof Item) {
        Item i = (Item) item.get();
        if (i.getType().equals(ItemType.QUESTION)) {
          quest = true;
        }

      }*/ else {
        eat = true;
      }
    } else {
      game.getTerrain().put(parts.removeLast().getTile(), null);
    }

    if (eat) {
      game.getStats().items++;
      onEat(tile.get(), item.get());
    } else if (quest) {
      onQuestion(tile.get(), item.get());
      //  game.getStats().items++;
    }


    addPart(tile.get(), direction.opposite(), true);
    return true;
  }

  protected abstract void onQuestion(Tile tile, TileObject tileObject);

  private boolean moveable(Direction direction) {
    final Optional<Tile> next = parts.getFirst().getTile().getRelative(game, direction);
    if (!next.isPresent()) {
      return false;
    }
    final Optional<TileObject> obj = game.getTerrain().get(next.get());
    return !obj.isPresent() || obj.get() instanceof Food;
  }

  private Direction findBest() {
    final Solver solver = new Solver(game, parts.getFirst().getTile(), getFoodX(), getFoodY(), blocking);
    return solver.solve();
  }

  private Direction findTail() {
    final Solver solver = new Solver(game, parts.getFirst().getTile(), parts.getLast().getTile().getTileX(), parts.getLast().getTile().getTileY(), blocking);
    return solver.solve();
  }
}

