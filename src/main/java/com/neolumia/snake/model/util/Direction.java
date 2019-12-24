
package com.neolumia.snake.model.util;

public enum Direction {

  /**
   * Directions available : North, East, South, West - which represents the move that the user requested
   */
  NORTH, EAST, SOUTH, WEST;

  public Direction opposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      default:
        throw new RuntimeException();
    }
  }
}
