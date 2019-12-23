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

package com.neolumia.snake.model.game;

import javafx.scene.paint.Color;

public enum GameType {

  /**
   * ENUM types of the game : CLASSIC, RETRO, DUO . CLASSIC is the default option in the game
   */
  CLASSIC(Color.LIGHTGREEN, "/lib/title_classic.png"),
  RETRO(Color.WHITE, "/lib/title_retro.png"),
  DUO(Color.WHITE, "/lib/title_duo.png");

  /**
   * background : background color of the game
   * file : image background of the game
   */
  private final Color background;
  private final String file;

  GameType(Color background, String file) {
    this.background = background;
    this.file = file;
  }

  public Color getBackground() {
    return background;
  }

  public String getFile() {
    return file;
  }

  public GameType next() {
    return values()[(ordinal() + 1) % values().length];
  }
}
