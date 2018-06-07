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

package com.neolumia.snake;

import com.neolumia.snake.design.BgDesign;
import com.neolumia.snake.design.Design;
import com.neolumia.snake.design.TerrainDesign;
import com.neolumia.snake.game.Game;
import com.neolumia.snake.settings.Difficulty;
import com.neolumia.snake.settings.Locale;
import com.neolumia.snake.settings.Settings;
import com.neolumia.snake.settings.Size;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

  private static final String TABLE = "CREATE TABLE IF NOT EXISTS games (id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, points INT UNSIGNED NOT NULL);";
  private static final String HIGHSCORE = "SELECT MAX(points) FROM games;";
  private static final String SAVE = "INSERT INTO games (points) VALUES (?);";

  private static final String TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS settings (id BIGINT UNSIGNED NOT NULL PRIMARY KEY, locale INT NOT NULL, difficulty INT NOT NULL, size INT NOT NULL, name VARCHAR(255) NOT NULL, leaderboard TINYINT(1) UNSIGNED NOT NULL);";
  private static final String LOAD_SETTINGS = "SELECT locale, difficulty, size, name, leaderboard FROM settings;";
  private static final String SAVE_SETTINGS = "INSERT INTO settings (id, locale, difficulty, size, name, leaderboard) VALUES (0, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE locale=?, difficulty=?, size=?, name=?, leaderboard=?;";

  private static final String TABLE_STATS = "CREATE TABLE IF NOT EXISTS stats (id INT UNSIGNED NOT NULL PRIMARY KEY, playtime DOUBLE UNSIGNED NOT NULL, games INT UNSIGNED NOT NULL, items INT UNSIGNED NOT NULL, walls INT UNSIGNED NOT NULL);";
  private static final String LOAD_STATS = "SELECT playtime, games, items, walls FROM stats WHERE id = 0;";
  private static final String SAVE_STATS = "INSERT INTO stats (id, playtime, games, items, walls) VALUES (0, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE playtime = playtime + ?, games = games + ?, items = items + ?, walls = walls + ?;";

  private static final String TABLE_DESIGN = "CREATE TABLE IF NOT EXISTS design (id INT UNSIGNED NOT NULL PRIMARY KEY, terrain VARCHAR(32) NOT NULL, background VARCHAR(32) NOT NULL);";
  private static final String LOAD_DESIGN = "SELECT terrain, background FROM design WHERE id = 0;";
  private static final String SAVE_DESIGN = "INSERT INTO design (id, terrain, background) VALUES (0, ?, ?) ON DUPLICATE KEY UPDATE terrain = ?, background = ?;";

  private final HikariDataSource dataSource;

  Database(GameApp app) {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:" + app.getRoot().resolve("snake").toAbsolutePath().toString() + ";MODE=MYSQL");
    config.setUsername("snake");
    config.setPassword("snake");
    config.setMaximumPoolSize((Runtime.getRuntime().availableProcessors() * 2) + 1);
    dataSource = new HikariDataSource(config);
  }

  public void init() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(TABLE)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(TABLE_DESIGN)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(TABLE_STATS)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(TABLE_SETTINGS)) {
        statement.executeUpdate();
      }
    }
  }

  int getHighscore() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(HIGHSCORE)) {
        try (ResultSet result = statement.executeQuery()) {
          if (result.next()) {
            return result.getInt(1);
          }
          return -1;
        }
      }
    }
  }

  public void saveGame(Game game) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(SAVE)) {
        statement.setInt(1, game.getPoints());
        statement.executeUpdate();
      }
    }
  }

  Settings getSettings() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(LOAD_SETTINGS)) {
        try (ResultSet result = statement.executeQuery()) {

          Locale locale = Settings.DEFAULT_LOCALE;
          Difficulty difficulty = Settings.DEFAULT_DIFFICULTY;
          Size size = Settings.DEFAULT_SIZE;
          String name = Settings.DEFAULT_NAME;
          boolean leaderboard = Settings.DEFAULT_LEADERBOARD;

          if (result.next()) {
            locale = Locale.fromId(result.getInt(1));
            difficulty = Difficulty.fromId(result.getInt(2));
            size = Size.fromId(result.getInt(3));
            name = result.getString(4);
            leaderboard = result.getBoolean(5);
          }

          return new Settings(locale, difficulty, size, name, leaderboard);
        }
      }
    }
  }

  public void saveSettings(Settings settings) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(SAVE_SETTINGS)) {
        statement.setInt(1, settings.locale.getId());
        statement.setInt(2, settings.difficulty.getId());
        statement.setInt(3, settings.size.getId());
        statement.setString(4, settings.playerName);
        statement.setBoolean(5, settings.leaderboard);

        statement.setInt(6, settings.locale.getId());
        statement.setInt(7, settings.difficulty.getId());
        statement.setInt(8, settings.size.getId());
        statement.setString(9, settings.playerName);
        statement.setBoolean(10, settings.leaderboard);
        statement.executeUpdate();
      }
    }
  }

  Design loadDesign() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(LOAD_DESIGN)) {
        try (ResultSet result = statement.executeQuery()) {
          if (result.next()) {
            return new Design(TerrainDesign.valueOf(result.getString(1)), BgDesign.valueOf(result.getString(2)));
          }
          return new Design();
        }
      }
    }
  }

  void updateDesign(Design design) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(SAVE_DESIGN)) {
        statement.setString(1, design.terrain.name());
        statement.setString(2, design.background.name());

        statement.setString(3, design.terrain.name());
        statement.setString(4, design.background.name());
        statement.executeUpdate();
      }
    }
  }

  Stats loadStats() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(LOAD_STATS)) {
        try (ResultSet result = statement.executeQuery()) {
          if (result.next()) {
            return new Stats(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4));
          }
          return new Stats();
        }
      }
    }
  }

  void updateStats(Stats stats) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(SAVE_STATS)) {
        statement.setDouble(1, stats.playtime);
        statement.setInt(2, stats.games);
        statement.setInt(3, stats.items);
        statement.setInt(4, stats.walls);

        statement.setDouble(5, stats.playtime);
        statement.setInt(6, stats.games);
        statement.setInt(7, stats.items);
        statement.setInt(8, stats.walls);
        statement.executeUpdate();
      }
    }
  }
}
