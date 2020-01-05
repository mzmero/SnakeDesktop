package com.neolumia.snake.model.solver;

import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.view.game.Tile;

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

  @Override
  public int hashCode() {
    return Objects.hash(tile.getTileX(), tile.getTileY());
  }

  void heuristics(int foodX, int foodY) {
    this.h = Math.abs(foodX - tile.getTileX()) + Math.abs(foodY - tile.getTileY());
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
}
