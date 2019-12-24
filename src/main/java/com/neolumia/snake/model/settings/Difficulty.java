
package com.neolumia.snake.model.settings;

public enum Difficulty {

  /**
   * Represents the difficulty of the game : EASY, MEDIUM, HARD
   */
  EASY(0, 9), MEDIUM(1, 6), HARD(2, 4);

  private final int id;
  private final int speed;

  Difficulty(int id, int speed) {
    this.id = id;
    this.speed = speed;
  }

  /**
   * Returns Difficulty object by the id that was passed
   * @param id
   * @return
   */
  public static Difficulty fromId(int id) {
    for (Difficulty difficulty : values()) {
      if (difficulty.id == id) {
        return difficulty;
      }
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public int getSpeed() {
    return speed;
  }
}
