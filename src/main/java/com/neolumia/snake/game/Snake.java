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

package com.neolumia.snake.game;

import javafx.scene.paint.Color;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class Snake<T extends Game> {

  private final ConcurrentLinkedDeque<SnakePart> parts = new ConcurrentLinkedDeque<>();

  protected final T game;
  private final int speed;

  private Direction direction;
  private int ticks;

  @Nullable
  private Direction next;

  public Snake(T game, Direction direction) {
    this.game = game;
    this.direction = direction;
    this.speed = game.getSettings().difficulty.getSpeed();
  }

  public abstract void init();

  public abstract void onEat(Tile tile, TileObject object);

  public void tick() {
    if (ticks % speed == 0) {
      move();
    }
    ticks++;
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

  protected SnakePart createPart(Tile tile, int size, Direction direction) {
    return new SnakePart(tile, Color.BLACK, size, direction);
  }

  protected void addPart(Tile tile, Direction direction, boolean head) {
    final SnakePart sp = createPart(tile, 32, direction);
    game.getTerrain().put(tile, sp);
    if (head) {
      parts.addFirst(sp);
      return;
    }
    parts.addLast(sp);
  }

  private void move() {

    if (parts.isEmpty()) {
      return;
    }

    if (next != null) {
      direction = next;
      next = null;
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
      // -> Hit the wall
      game.getStats().walls++;
      game.end();
      return;
    }

    boolean eat = false;

    final Optional<TileObject> item = game.getTerrain().get(tile.get());
    if (item.isPresent()) {

      if (item.get() instanceof SnakePart) {
        // Hit himself --> End
        game.end();
        return;
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
  }
}
