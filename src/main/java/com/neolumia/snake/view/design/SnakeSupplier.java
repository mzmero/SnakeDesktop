package com.neolumia.snake.view.design;

import com.neolumia.snake.control.Snake;
import com.neolumia.snake.model.Direction;
import com.neolumia.snake.view.game.SnakePartView;
import com.neolumia.snake.model.Tile;
import javafx.scene.paint.Color;

import javax.annotation.Nullable;

public interface SnakeSupplier<T extends SnakePartView> {

  T get(Snake snake, Tile tile, Direction direction, @Nullable Color color);
}
