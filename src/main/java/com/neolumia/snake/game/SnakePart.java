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
import javafx.scene.shape.Rectangle;

public class SnakePart extends TileObject {

  private static final int CON_SIZE = 6;
  private final Tile tile;
  private final Rectangle snake = new Rectangle();
  private final Rectangle connector;

  private Color color;
  private int size;
  private Direction direction;

  public SnakePart(Tile tile, Color color, int size, Direction direction) {
    this.tile = tile;
    this.color = color;
    this.size = size;
    this.direction = direction;

    this.connector = new Rectangle(0, 0, Color.BLACK);

    getChildren().add(snake);
    getChildren().add(connector);

    update();
  }

  @Override
  public void setSize(int size) {
    this.size = size;
    super.setSize(size);
    update();
  }

  Tile getTile() {
    return tile;
  }

  protected void update() {
    snake.setFill(color);
    connector.setFill(color);

    snake.setX(tile.getTileX() * size + 3);
    snake.setY(tile.getTileY() * size + 3);
    snake.setWidth(size - 6);
    snake.setHeight(size - 6);

    direction(direction, size);
  }

  private void direction(Direction direction, int size) {
    this.direction = direction;

    int x;
    int y;

    switch (direction) {
      case NORTH:
        x = tile.getTileX() * size + (size / 2) - (CON_SIZE / 2);
        y = tile.getTileY() * size;
        break;
      case EAST:
        x = tile.getTileX() * size + 32 - CON_SIZE;
        y = tile.getTileY() * size + (size / 2) - (CON_SIZE / 2);
        break;
      case SOUTH:
        x = tile.getTileX() * size + (size / 2) - (CON_SIZE / 2);
        y = tile.getTileY() * size + size - CON_SIZE;
        break;
      case WEST:
        x = tile.getTileX() * size;
        y = tile.getTileY() * size + (size / 2) - (CON_SIZE / 2);
        break;
      default:
        throw new RuntimeException();
    }

    connector.setX(x);
    connector.setY(y);
    connector.setWidth(CON_SIZE);
    connector.setHeight(CON_SIZE);
  }
}
