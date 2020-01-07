
package com.neolumia.snake.view.design.snake;

import com.neolumia.snake.control.Snake;
import com.neolumia.snake.model.Tile;
import com.neolumia.snake.model.Direction;
import javafx.scene.paint.Color;

import javax.annotation.Nullable;

public final class FredGucciSnakeView extends FredSnakeView {

  /**
   * Constructs the FredGucciSnake which is inherited from FredSnake
   * @param snake - default snake
   * @param tile - Location
   * @param direction - snake direction
   * @param color - color of the snake
   */
  public FredGucciSnakeView(Snake snake, Tile tile, Direction direction, @Nullable Color color) {
    super(snake, tile, direction, color == null ? Color.GOLD : color);
  }
}
