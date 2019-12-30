
package com.neolumia.snake.view.design.snake;

import com.neolumia.snake.control.Snake;
import com.neolumia.snake.view.game.SnakePart;
import com.neolumia.snake.view.game.Tile;
import com.neolumia.snake.model.util.Direction;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.annotation.Nullable;

public class PixelSnake extends SnakePart {


  private static final int MARGIN = 6;
  private static final int CON_SIZE = 6;

  private final Rectangle snake = new Rectangle(0, 0, Color.BLACK);
  private final Rectangle connector = new Rectangle(0, 0, Color.BLACK);

  public PixelSnake(Snake snake2, Tile tile, Direction direction, @Nullable Color color) {
    super(snake2, tile, direction, color == null ? Color.BLACK : color);
    getChildren().addAll(snake, connector);
  }

  @Override
  public void update() {
    snake.setFill(getColor());
    connector.setFill(getColor());
    updateSize();
    updateConnector();
  }

  @Override
  public void init() {
    snake.setFill(getColor());
    connector.setFill(getColor());
    updateSize();
    updateConnector();
  }

  private void updateSize() {
    snake.setX(getX() * getSize() + MARGIN / 2);
    snake.setY(getY() * getSize() + MARGIN / 2);
    snake.setWidth(getSize() - MARGIN);
    snake.setHeight(getSize() - MARGIN);
  }

  private void updateConnector() {

    int x;
    int y;

    switch (getDirection()) {
      case NORTH:
        x = getX() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        y = getY() * getSize() - (CON_SIZE / 2) + MARGIN / 2;
        break;
      case EAST:
        x = getX() * getSize() + getSize() - CON_SIZE - (CON_SIZE / 2) + MARGIN / 2;
        y = getY() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        break;
      case SOUTH:
        x = getX() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        y = getY() * getSize() + getSize() - CON_SIZE - (CON_SIZE / 2) + MARGIN / 2;
        break;
      case WEST:
        x = getX() * getSize() - (CON_SIZE / 2) + MARGIN / 2;
        y = getY() * getSize() + (getSize() / 2) - CON_SIZE + MARGIN / 2;
        break;
      default:
        throw new RuntimeException("Illegal direction");
    }

    connector.setX(x);
    connector.setY(y);
    connector.setWidth(CON_SIZE);
    connector.setHeight(CON_SIZE);
  }
}
