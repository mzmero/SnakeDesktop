package com.neolumia.snake.view;

import javafx.beans.property.SimpleStringProperty;

public class TableItem {

    private final SimpleStringProperty player;
    private final SimpleStringProperty points;
    private final SimpleStringProperty lives;

    public TableItem(String player, String points, String lives) {
      this.player = new SimpleStringProperty(player);
      this.points = new SimpleStringProperty(points);
      this.lives = new SimpleStringProperty(lives);
    }

    public String getPlayer() {
      return player.get();
    }

    public SimpleStringProperty playerProperty() {
      return player;
    }

    public void setPlayer(String player) {
      this.player.set(player);
    }

    public String getPoints() {
      return points.get();
    }

    public SimpleStringProperty pointsProperty() {
      return points;
    }

    public void setPoints(String points) {
      this.points.set(points);
    }

    public String getLives() {
      return lives.get();
    }

    public SimpleStringProperty livesProperty() {
      return lives;
    }

    public void setLives(String lives) {
      this.lives.set(lives);
    }
  }
