package com.neolumia.snake.model.questions;

public enum QuestionLevel {
  /**
   * This ENUM class represents the level of the game
   */

  ONE("1"), TWO("2"), THREE("3");

  private String level;

  public String getLevel() {
    return this.level;
  }

  private QuestionLevel(String level) {
    this.level = level;
  }


}
