
package com.neolumia.snake.model.settings;

public enum Size {

  /**
   * Enum for customizing snake size
   */
  SMALL(0, 20, 10), MEDIUM(1, 30, 15), BIG(2, 45, 23);

  private final int id;
  private final int width;
  private final int height;

  Size(int id, int width, int height) {
    this.id = id;
    this.width = width;
    this.height = height;
  }

  public static Size fromId(int id) {
    for (Size size : values()) {
      if (size.id == id) {
        return size;
      }
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
