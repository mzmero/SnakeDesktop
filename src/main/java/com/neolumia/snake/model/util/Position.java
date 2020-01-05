package com.neolumia.snake.model.util;

/** Represents the coordinates of an item in game by two coordinates */
public final class Position {
  private final int x;
  private final int y;

  /**
   * Defines an position
   *
   * @param x - x coordinate
   * @param y - y coordinate
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Position of(int x, int y) {
    return new Position(x, y);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
