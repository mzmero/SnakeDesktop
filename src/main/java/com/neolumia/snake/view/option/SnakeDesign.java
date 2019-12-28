
package com.neolumia.snake.view.option;

import com.neolumia.snake.view.design.DesignOption;
import com.neolumia.snake.view.design.SnakeSupplier;
import com.neolumia.snake.view.design.snake.FredGucciSnake;
import com.neolumia.snake.view.design.snake.FredSnake;
import com.neolumia.snake.view.design.snake.PixelGucciSnake;
import com.neolumia.snake.view.design.snake.PixelSnake;
import com.neolumia.snake.view.game.SnakePart;

import java.util.Optional;

public enum SnakeDesign implements DesignOption<SnakeDesign> {

  /**
   * Snake designs : PIXEL ,PIXEL_GUCCI, FRED, FRED_GUCCI
   */
  PIXEL("design.pixel", "/lib/snake_pixel.png", PixelSnake::new),
  PIXEL_GUCCI("design.pixel.gucci", "/lib/snake_pixel_gucci.png", PixelGucciSnake::new),
  FRED("design.fred", "/lib/snake_fred.png", FredSnake::new),
  FRED_GUCCI("design.fred.gucci", "/lib/snake_fred_gucci.png", FredGucciSnake::new);

  private final String name;
  private final String file;
  private final SnakeSupplier<SnakePart> part;

  SnakeDesign(String name, String file, SnakeSupplier<SnakePart> part) {
    this.name = name;
    this.file = file;
    this.part = part;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getFile() {
    return file;
  }

  @Override
  public Optional<SnakeDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  @Override
  public Optional<SnakeDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }

  public SnakeSupplier<SnakePart> getPart() {
    return part;
  }
}
