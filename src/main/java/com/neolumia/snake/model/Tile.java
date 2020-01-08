
package com.neolumia.snake.model;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.Direction;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public final class Tile extends Rectangle {

  private final int x;
  private final int y;

  public Tile(int x, int y, int size, Paint paint) {
    super(x * size, y * size, size, size);
    this.x = x;
    this.y = y;
    setFill(paint);
  }

  public int getTileX() {
    return x;
  }

  public int getTileY() {
    return y;
  }

  public Optional<Tile> getRelative(Game game, Direction direction) {
    int nextX = x;
    int nextY = y;
    switch (direction) {
      case NORTH:
        nextY--;
        break;
      case SOUTH:
        nextY++;
        break;
      case EAST:
        nextX++;
        break;
      case WEST:
        nextX--;
        break;
    }
    return (Optional<Tile>) game.getTerrain().getTile(nextX, nextY);
  }

  public void setSize(int size) {
    setX(x * size);
    setY(y * size);
    setHeight(size);
    setWidth(size);
  }
}
