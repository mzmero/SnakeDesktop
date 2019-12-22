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

package com.neolumia.snake.model.solver;

import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.model.game.Tile;

import java.util.Objects;

public final class Node {

  private final Tile tile;

  private Node parent;
  private Direction direction;

  private int g;
  private int f;
  private int h;

  Node(Tile tile) {
    this.tile = tile;
  }

  public Tile getTile() {
    return tile;
  }

  public Direction getDirection() {
    return direction;
  }

  Node getParent() {
    return parent;
  }

  int getF() {
    return f;
  }

  void update(Node from, Direction direction, int cost) {
    this.parent = from;
    this.direction = direction;
    g = g + cost;
    f = g + h;
  }

  void better(Node from, Direction direction, int cost) {
    int gCost = from.g + cost;
    if (gCost < g) {
      update(from, direction, cost);
    }
  }

  void heuristics(int foodX, int foodY) {
    this.h = Math.abs(foodX - tile.getTileX()) + Math.abs(foodY - tile.getTileY());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Node)) {
      return false;
    }
    final Node node = (Node) o;
    return tile.getTileY() == node.tile.getTileY() && tile.getTileX() == node.tile.getTileX();
  }

  @Override
  public int hashCode() {
    return Objects.hash(tile.getTileX(), tile.getTileY());
  }
}
