
package com.neolumia.snake.model.game;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.neolumia.snake.control.Snake;
import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.view.item.TileObject;
import javafx.scene.paint.Color;

public abstract class SnakePart extends TileObject {

  private Snake snake;
  private SnakePart parent;

  private final Tile tile;
  private final Direction direction;
  private final Color color;

  public SnakePart(Snake snake, Tile tile, Direction direction, Color color) {
    this.snake = snake;
    this.tile = tile;
    this.direction = direction;
    this.color = color;
  }

  public abstract void update();

  public Tile getTile() {
    return tile;
  }

  public Direction getDirection() {
    return direction;
  }

  public Color getColor() {
    return color;
  }

  protected SnakePart getP() {
    return parent;
  }

  protected boolean isHead() {
    return parent == null;
  }

  protected boolean isTail() {
    return snake.getParts().getLast().equals(this);
  }

  public void setP(SnakePart parent) {
    this.parent = parent;
  }

  @Override
  protected ToStringHelper toStringHelper() {
    return super.toStringHelper()
      .add("tile", tile)
      .add("direction", direction)
      .add("color", color);
  }

  @Override
  public final String toString() {
    return toStringHelper().toString();
  }
}
