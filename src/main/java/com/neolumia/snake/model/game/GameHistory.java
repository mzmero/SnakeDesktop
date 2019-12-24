package com.neolumia.snake.model.game;

import java.util.ArrayList;

public class GameHistory implements Comparable {
  private String player;
  private int points;
  private int lives;


  public GameHistory(String player, int points, int lives) {
    this.lives = lives;
    this.player = player;
    this.points = points;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public int getLives() {
    return lives;
  }

  public int getPoints() {
    return points;
  }

  public String getPlayer() {
    return player;
  }

  @Override
  public String toString() {
    return
      "player='" + player + '\'' +
        ", points=" + points +
        ", lives=" + lives +
        "\n";
  }

  @Override
  public int compareTo(Object o) {
    GameHistory g = (GameHistory) o;
    if (this.getPoints() < g.getPoints()) return 1;
    else if (this.getPoints() == g.getPoints()) return 0;
    else return -1;
  }
}
