/*
 * This file is part of Snake, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 Neolumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.neolumia.snake.model.design.option;

import com.neolumia.snake.model.design.DesignOption;
import com.neolumia.snake.model.design.SnakeSupplier;
import com.neolumia.snake.model.design.snake.FredGucciSnake;
import com.neolumia.snake.model.design.snake.FredSnake;
import com.neolumia.snake.model.design.snake.PixelGucciSnake;
import com.neolumia.snake.model.design.snake.PixelSnake;
import com.neolumia.snake.model.game.SnakePart;

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
