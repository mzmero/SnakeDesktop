package com.neolumia.snake.model;

import com.google.common.base.MoreObjects;

/**
 * Stats class is used to save game details in order to be shown later on in stats window
 */
public final class Stats {

  public double playtime;
  public int games;
  public int items;
  public int walls;

  public Stats() {}

  public Stats(double playtime, int games, int items, int walls) {
    this.playtime = playtime;
    this.games = games;
    this.items = items;
    this.walls = walls;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("playtime", playtime)
        .add("games", games)
        .add("items", items)
        .add("walls", walls)
        .toString();
  }
}
