
package com.neolumia.snake.model.design;

import com.neolumia.snake.control.Snake;
import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.model.game.SnakePart;
import com.neolumia.snake.model.game.Tile;
import javafx.scene.paint.Color;

import javax.annotation.Nullable;

public interface SnakeSupplier<T extends SnakePart> {

  T get(Snake snake, Tile tile, Direction direction, @Nullable Color color);
}
