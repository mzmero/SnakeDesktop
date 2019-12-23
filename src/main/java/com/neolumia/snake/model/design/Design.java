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

package com.neolumia.snake.model.design;

import com.google.common.base.MoreObjects;
import com.neolumia.snake.model.design.option.BgDesign;
import com.neolumia.snake.model.design.option.SnakeDesign;
import com.neolumia.snake.model.design.option.TerrainDesign;

import java.util.Objects;

public final class Design {

  /**
   * Defines the design of the game (with defaults) and option to customize
   */

  public TerrainDesign terrain = TerrainDesign.SIMPLE;
  public SnakeDesign snake = SnakeDesign.FRED;
  public BgDesign background = BgDesign.CYAN;

  public Design() {}

  public Design(TerrainDesign terrain, SnakeDesign snake, BgDesign background) {
    this.terrain = terrain;
    this.snake = snake;
    this.background = background;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Design)) {
      return false;
    }
    final Design design = (Design) o;
    return terrain == design.terrain && background == design.background;
  }

  @Override
  public int hashCode() {
    return Objects.hash(terrain, background);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("terrain", terrain)
      .add("background", background)
      .toString();
  }
}
