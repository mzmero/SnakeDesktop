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

package com.neolumia.snake.util;

public final class Q {

  public static final String TABLE = "CREATE TABLE IF NOT EXISTS games (id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, points INT UNSIGNED NOT NULL);";
  public static final String HIGHSCORE = "SELECT MAX(points) FROM games;";
  public static final String SAVE = "INSERT INTO games (points) VALUES (?);";

  public static final String TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS settings (id BIGINT UNSIGNED NOT NULL PRIMARY KEY, locale INT NOT NULL, difficulty INT NOT NULL, size INT NOT NULL, name VARCHAR(255) NOT NULL, leaderboard TINYINT(1) UNSIGNED NOT NULL);";
  public static final String LOAD_SETTINGS = "SELECT locale, difficulty, size, name, leaderboard FROM settings;";
  public static final String SAVE_SETTINGS = "INSERT INTO settings (id, locale, difficulty, size, name, leaderboard) VALUES (0, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE locale=?, difficulty=?, size=?, name=?, leaderboard=?;";

  public static final String TABLE_STATS = "CREATE TABLE IF NOT EXISTS stats (id INT UNSIGNED NOT NULL PRIMARY KEY, playtime DOUBLE UNSIGNED NOT NULL, games INT UNSIGNED NOT NULL, items INT UNSIGNED NOT NULL, walls INT UNSIGNED NOT NULL);";
  public static final String LOAD_STATS = "SELECT playtime, games, items, walls FROM stats WHERE id = 0;";
  public static final String SAVE_STATS = "INSERT INTO stats (id, playtime, games, items, walls) VALUES (0, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE playtime = playtime + ?, games = games + ?, items = items + ?, walls = walls + ?;";

  public static final String TABLE_DESIGN = "CREATE TABLE IF NOT EXISTS design (id INT UNSIGNED NOT NULL PRIMARY KEY, terrain VARCHAR(32) NOT NULL, snake VARCHAR(32) NOT NULL, background VARCHAR(32) NOT NULL);";
  public static final String LOAD_DESIGN = "SELECT terrain, snake, background FROM design WHERE id = 0;";
  public static final String SAVE_DESIGN = "INSERT INTO design (id, terrain, snake, background) VALUES (0, ?, ?, ?) ON DUPLICATE KEY UPDATE terrain = ?, snake = ?, background = ?;";

  private Q() {}
}
