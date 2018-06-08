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

package com.neolumia.snake.design.snake;

import com.neolumia.snake.util.Direction;
import com.neolumia.snake.game.SnakePart;
import com.neolumia.snake.game.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.annotation.Nullable;

public class PixelSnake extends SnakePart {

  private static final int MARGIN = 6;
  private static final int CON_SIZE = 6;

  private final Rectangle snake = new Rectangle(0, 0, Color.BLACK);
  private final Rectangle connector = new Rectangle(0, 0, Color.BLACK);

  public PixelSnake(SnakePart parent, Tile tile, Direction direction, @Nullable Color color) {
    super(parent, tile, direction, color == null ? Color.BLACK : color);
    getChildren().addAll(snake, connector);
  }

  @Override
  public void update() {}

  @Override
  public void init() {
    snake.setFill(getColor());
    connector.setFill(getColor());
    updateSize();
    updateConnector();
  }

  private void updateSize() {
    snake.setX(getX() * getSize() + MARGIN / 2);
    snake.setY(getY() * getSize() + MARGIN / 2);
    snake.setWidth(getSize() - MARGIN);
    snake.setHeight(getSize() - MARGIN);
  }

  private void updateConnector() {

    int x;
    int y;

    switch (getDirection()) {
      case NORTH:
        x = getX() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        y = getY() * getSize() - (CON_SIZE / 2) + MARGIN / 2;
        break;
      case EAST:
        x = getX() * getSize() + getSize() - CON_SIZE - (CON_SIZE / 2) + MARGIN / 2;
        y = getY() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        break;
      case SOUTH:
        x = getX() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        y = getY() * getSize() + getSize() - CON_SIZE - (CON_SIZE / 2) + MARGIN / 2;
        break;
      case WEST:
        x = getX() * getSize() - (CON_SIZE / 2) + MARGIN / 2;
        y = getY() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        break;
      default:
        throw new RuntimeException("Illegal direction");
    }

    connector.setX(x);
    connector.setY(y);
    connector.setWidth(CON_SIZE);
    connector.setHeight(CON_SIZE);
  }
}
