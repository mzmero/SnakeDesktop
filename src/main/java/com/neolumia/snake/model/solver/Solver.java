
package com.neolumia.snake.model.solver;

import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class Solver {

  private Node[][] nodes;
  private ArrayList<Node> openList = new ArrayList<>();
  private ArrayList<Node> closedList = new ArrayList<>();

  private Game game;
  private Node start;
  private int foodX;
  private int foodY;
  private Predicate<Node> blocking;

  public Solver(Game game, Tile start, int foodX, int foodY, Predicate<Node> blocking) {
    this.game = game;
    this.start = new Node(start);
    this.foodX = foodX;
    this.foodY = foodY;
    this.blocking = blocking;
    init();
  }

  public Direction solve() {
    openList.add(start);
    while (!openList.isEmpty()) {
      final Node current = lowestF();

      closedList.add(current);
      openList.remove(current);
      if (isFinal(current)) {
        return getDirection(current);
      } else {
        Optional<Tile> tileNorth = game.getTerrain().getTile(current.getTile().getTileX(), current.getTile().getTileY() - 1);
        Optional<Tile> tileEast = game.getTerrain().getTile(current.getTile().getTileX() + 1, current.getTile().getTileY());
        Optional<Tile> tileSouth = game.getTerrain().getTile(current.getTile().getTileX(), current.getTile().getTileY() + 1);
        Optional<Tile> tileWest = game.getTerrain().getTile(current.getTile().getTileX() - 1, current.getTile().getTileY());
        tileNorth.ifPresent(tile -> checkNode(current, nodes[tile.getTileX()][tile.getTileY()], Direction.NORTH, 10));
        tileEast.ifPresent(tile -> checkNode(current, nodes[tile.getTileX()][tile.getTileY()], Direction.EAST, 10));
        tileSouth.ifPresent(tile -> checkNode(current, nodes[tile.getTileX()][tile.getTileY()], Direction.SOUTH, 10));
        tileWest.ifPresent(tile -> checkNode(current, nodes[tile.getTileX()][tile.getTileY()], Direction.WEST, 10));
      }
    }
    return null;
  }

  private void init() {
    nodes = new Node[game.getTerrain().getTileWidth()][game.getTerrain().getTileHeight()];
    for (int x = 0; x < game.getTerrain().getTileWidth(); x++) {
      for (int y = 0; y < game.getTerrain().getTileHeight(); y++) {
        nodes[x][y] = new Node(game.getTerrain().getTile(x, y).get());
        nodes[x][y].heuristics(foodX, foodY);
      }
    }
  }

  private List<Node> getPath(Node current) {
    final List<Node> path = new ArrayList<>();
    path.add(current);
    Node parent;
    while ((parent = current.getParent()) != null) {
      path.add(0, parent);
      current = parent;
    }
    return path;
  }

  private Direction getDirection(Node current) {
    return getPath(current).get(1).getDirection();
  }

  private void checkNode(Node from, Node adjacent, Direction direction, int cost) {
    if (!blocking.test(adjacent) && !closedList.contains(adjacent)) {
      if (!openList.contains(adjacent)) {
        adjacent.update(from, direction, cost);
        openList.add(adjacent);
      } else {
        adjacent.better(from, direction, cost);
      }
    }
  }

  private boolean isFinal(Node node) {
    return node.getTile().getTileX() == foodX && node.getTile().getTileY() == foodY;
  }

  private Node lowestF() {
    Node lowest = null;
    for (Node node : openList) {
      if (lowest == null) {
        lowest = node;
      } else {
        if (node.getF() < lowest.getF()) {
          lowest = node;
        }
      }
    }
    return lowest;
  }
}
