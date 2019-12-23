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

import com.neolumia.snake.model.util.Direction;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public final class Tile extends Rectangle {

  /**
   * Tile defines the location of the object of board by two coordinates X and Y and used for mapping objects in game
   */
  private final int x;
  private final int y;

  Tile(int x, int y, int size, Paint paint) {
    super(x * size, y * size, size, size);
    this.x = x;
    this.y = y;
    setFill(paint);
  }

  public int getTileX() {
    return x;
  }

  public int getTileY() {
    return y;
  }

  /**
   * This method returns the suitable tile according to the game and direction received
   * @param game - the current game
   * @param direction - the desired direction
   * @return a tile object which is the result of the move - direction requested
   */
  public Optional<Tile> getRelative(Game game, Direction direction) {
    int nextX = x;
    int nextY = y;
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
    return game.getTerrain().getTile(nextX, nextY);
  }

  /**
   * Setting the size of the tile
   * @param size - desired size
   */
  public void setSize(int size) {
    setX(x * size);
    setY(y * size);
    setHeight(size);
    setWidth(size);
  }
}
