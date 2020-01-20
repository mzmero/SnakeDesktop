package com.neolumia.snake.model;

import com.neolumia.snake.control.Snake;
import com.neolumia.snake.view.game.SnakePartView;
import javafx.scene.paint.Color;
/*snake part model*/
public class SnakePart {

  private final Tile tile;
  private final Direction direction;

  public SnakePart(Tile tile, Direction direction) {
    this.tile = tile;
    this.direction = direction;
  }

  public Tile getTile() {
    return tile;
  }

  public Direction getDirection() {
    return direction;
  }
}
