package com.neolumia.snake.model.questions;

/**
 * This ENUM class represents the level of the game
 */
public enum QuestionLevel {

  ONE("1"), TWO("2"), THREE("3");

  private String level;

  public String getLevel() {
    return this.level;
  }

  private QuestionLevel(String level) {
    this.level = level;
  }

}
