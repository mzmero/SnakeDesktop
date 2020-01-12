package com.neolumia.snake.view.game;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.neolumia.snake.control.Snake;
import com.neolumia.snake.model.Direction;
import com.neolumia.snake.model.SnakePart;
import com.neolumia.snake.model.Tile;
import com.neolumia.snake.view.item.TileObject;
import javafx.scene.paint.Color;

public abstract class SnakePartView extends TileObject {

  private Snake snake;
  private SnakePartView parent;
  private SnakePart snakePart;
  private final Color color;

  public SnakePartView(Snake snake, Tile tile, Direction direction, Color color) {
    this.snake = snake;
    snakePart= new SnakePart(tile,direction);
    this.color = color;
  }

  public abstract void update();

  public Tile getTile() {
    return snakePart.getTile();
  }

  public Direction getDirection() {
    return snakePart.getDirection();
  }

  public Color getColor() {
    return color;
  }

  protected SnakePartView getP() {
    return parent;
  }

  protected boolean isHead() {
    return parent == null;
  }

  protected boolean isTail() {
    return snake.getParts().getLast().equals(this);
  }

  public void setP(SnakePartView parent) {
    this.parent = parent;
  }

  @Override
  protected ToStringHelper toStringHelper() {
    return super.toStringHelper().add("tile", snakePart.getTile()).add("direction", snakePart.getDirection()).add("color", color);
  }

  @Override
  public final String toString() {
    return toStringHelper().toString();
  }
}
