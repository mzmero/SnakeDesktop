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
import com.neolumia.snake.model.util.Position;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Function;

public enum TerrainDesign implements DesignOption<TerrainDesign> {
  /**
   * Terrain designs : SIMPLE , CHESS , GRASS
   */
  SIMPLE("terrain.simple", "/lib/terrain_retro.png", pos -> Color.rgb(157, 213, 3)),
  GRASS("terrain.grass", "/lib/terrain_grass.png", pos -> (pos.getX() + pos.getY()) % 2 == 0 ? Color.rgb(111, 169, 111) : Color.rgb(127, 188, 124)),
  CHESS("terrain.chess", "/lib/terrain_chess.png", pos -> (pos.getX() + pos.getY()) % 2 == 0 ? Color.rgb(207, 137, 72) : Color.rgb(255, 204, 156));

  private final String name;
  private final String file;
  private final Function<Position, Color> color;

  /**
   * Constructs the terrain design
   * @param name - name of design
   * @param file - image associated with design
   * @param color - suitable color for the design
   */
  TerrainDesign(String name, String file, Function<Position, Color> color) {
    this.name = name;
    this.file = file;
    this.color = color;
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
  public Optional<TerrainDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  @Override
  public Optional<TerrainDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }

  public Function<Position, Color> getColor() {
    return color;
  }
}
