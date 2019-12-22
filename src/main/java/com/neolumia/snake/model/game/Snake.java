/*
 * This file is part of Snake, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 Neolumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.neolumia.snake.model.game;

import com.neolumia.snake.model.item.food.Food;
import com.neolumia.snake.model.solver.Node;
import com.neolumia.snake.model.solver.Solver;
import com.neolumia.snake.model.util.Direction;

import javax.annotation.Nullable;
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
  private int ticks;
  private int lives;
  @Nullable
  private Direction next;

  public Snake(T game, Direction direction, Predicate<Node> blocking) {
    this.game = game;
    this.direction = direction;
    this.blocking = blocking;
    this.speed = game.getSettings().difficulty.getSpeed();
    this.lives = 3;
  }

  public abstract void init();

  public abstract void onEat(Tile tile, TileObject object);

  public boolean tick() {
    while(true) {
      if (ticks % (game.isAuto() ? speed / 2 : speed) == 0) {
        return move();
      }
      ticks++;
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
      if(lives == 0)
      game.end();
      else
      {

        game.getTerrain();
        this.init();

        game.setPaused(true);
      }
      return false;
    }

    boolean eat = false;

    final Optional<TileObject> item = game.getTerrain().get(tile.get());
    if (item.isPresent()) {

      if (item.get() instanceof SnakePart) {
        // Hit himself --> End
        lives--;
        game.setLives(lives);
        game.getStats().walls++;
        if(lives == 0)
          game.end();
        else
        {
          this.init();
          game.setPaused(true);
        }
        return false;

      }

      eat = true;
    } else {
      game.getTerrain().put(parts.removeLast().getTile(), null);
    }

    if (eat) {
      game.getStats().items++;
      onEat(tile.get(), item.get());
    }


    addPart(tile.get(), direction.opposite(), true);
    return true;
  }

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
