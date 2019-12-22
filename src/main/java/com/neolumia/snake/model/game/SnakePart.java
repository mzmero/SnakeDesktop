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

import com.google.common.base.MoreObjects.ToStringHelper;
import com.neolumia.snake.model.util.Direction;
import javafx.scene.paint.Color;

public abstract class SnakePart extends TileObject {

  private Snake snake;
  private SnakePart parent;

  private final Tile tile;
  private final Direction direction;
  private final Color color;

  public SnakePart(Snake snake, Tile tile, Direction direction, Color color) {
    this.snake = snake;
    this.tile = tile;
    this.direction = direction;
    this.color = color;
  }

  public abstract void update();

  public Tile getTile() {
    return tile;
  }

  public Direction getDirection() {
    return direction;
  }

  public Color getColor() {
    return color;
  }

  protected SnakePart getP() {
    return parent;
  }

  protected boolean isHead() {
    return parent == null;
  }

  protected boolean isTail() {
    return snake.getParts().getLast().equals(this);
  }

  void setP(SnakePart parent) {
    this.parent = parent;
  }

  @Override
  protected ToStringHelper toStringHelper() {
    return super.toStringHelper()
      .add("tile", tile)
      .add("direction", direction)
      .add("color", color);
  }

  @Override
  public final String toString() {
    return toStringHelper().toString();
  }
}
