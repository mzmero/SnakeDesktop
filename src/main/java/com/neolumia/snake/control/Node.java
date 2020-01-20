package com.neolumia.snake.control;

import com.neolumia.snake.model.Direction;
import com.neolumia.snake.model.Tile;

import java.util.Objects;

/** G F H are used for implementing A* algorithm for path traversal in auto mode */
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

  /**
   * heuristics estimates the cost of the cheapest path from n to the target node
   * @param foodX
   * @param foodY
   */
  void heuristics(int foodX, int foodY) {
    this.h = Math.abs(foodX - tile.getTileX()) + Math.abs(foodY - tile.getTileY());
  }

  /**
   * Updating G and F in each move
   * @param from
   * @param direction
   * @param cost
   */
  void update(Node from, Direction direction, int cost) {
    this.parent = from;
    this.direction = direction;
    g = g + cost;
    f = g + h;
  }
 /* if we can get from node to another in lower cost , update the new low cost for the node (work like Dijkstra )*/
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
