
package com.neolumia.snake.model.game;

import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.util.Direction;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public final class Tile extends Rectangle {

  /**
   * Tile defines the location of the object of board by two coordinates X and Y and used for mapping objects in game
   */
  private final int x;
  private final int y;

  Tile(int x, int y, int size, Paint paint) {
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

  /**
   * This method returns the suitable tile according to the game and direction received
   * @param game - the current game
   * @param direction - the desired direction
   * @return a tile object which is the result of the move - direction requested
   */
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
    return game.getTerrain().getTile(nextX, nextY);
  }

  /**
   * Setting the size of the tile
   * @param size - desired size
   */
  public void setSize(int size) {
    setX(x * size);
    setY(y * size);
    setHeight(size);
    setWidth(size);
  }
}
