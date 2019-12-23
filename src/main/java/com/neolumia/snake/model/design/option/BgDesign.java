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
import javafx.scene.paint.Color;

import java.util.Optional;

public enum BgDesign implements DesignOption<BgDesign> {
  /**
   * Background designs : CYAN and WHITE in order to let users customize the design of their game as they want
   */
  CYAN("color.aqua", "/lib/bg_cyan.png", Color.rgb(224, 255, 255)),
  WHITE("color.white", "/lib/bg_white.png", Color.WHITE);


  private final String name;
  private final String file;
  private final Color color;

  /**
   * Constructor for the design
   * @param name - name of the design
   * @param file - the suitable image from the lib folder
   * @param color - specific color associated
   */
  BgDesign(String name, String file, Color color) {
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

  /**
   * Changes the design of the game according to the user's selection
   * @return
   */
  @Override
  public Optional<BgDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  @Override
  public Optional<BgDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }

  public Color getColor() {
    return color;
  }
}
