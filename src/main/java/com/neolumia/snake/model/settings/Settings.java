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

package com.neolumia.snake.model.settings;

public final class Settings {

  /**
   * This class is for defining the settings like difficulty, size, player name and more
   */
  public static Locale DEFAULT_LOCALE = Locale.ENGLISH;
  public static Difficulty DEFAULT_DIFFICULTY = Difficulty.MEDIUM;
  public static Size DEFAULT_SIZE = Size.MEDIUM;
  public static String DEFAULT_NAME = "Dieter Bohlen";
  public static boolean DEFAULT_LEADERBOARD = false;

  public Locale locale;
  public Difficulty difficulty;
  public Size size;
  public String playerName;
  public boolean leaderboard;

  public Settings(Locale locale, Difficulty difficulty, Size size, String playerName, boolean leaderboard) {
    this.locale = locale;
    this.difficulty = difficulty;
    this.size = size;
    this.playerName = playerName;
    this.leaderboard = leaderboard;
  }
}
