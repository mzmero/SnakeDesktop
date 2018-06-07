package com.neolumia.snake.util;

public final class Position {

  private final int x;
  private final int y;

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
