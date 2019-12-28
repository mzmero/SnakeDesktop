
package com.neolumia.snake;

import com.neolumia.snake.model.design.Design;
import com.neolumia.snake.view.option.BgDesign;
import com.neolumia.snake.view.option.SnakeDesign;
import com.neolumia.snake.view.option.TerrainDesign;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.settings.Difficulty;
import com.neolumia.snake.model.settings.Locale;
import com.neolumia.snake.model.settings.Settings;
import com.neolumia.snake.model.settings.Size;
import com.neolumia.snake.model.util.Q;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

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
      try (PreparedStatement statement = connection.prepareStatement(Q.TABLE)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(Q.TABLE_DESIGN)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(Q.TABLE_STATS)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(Q.TABLE_SETTINGS)) {
        statement.executeUpdate();
      }
    }
  }

  int getHighscore() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.HIGHSCORE)) {
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
      try (PreparedStatement statement = connection.prepareStatement(Q.SAVE)) {
        statement.setInt(1, game.getPoints());
        statement.executeUpdate();
      }
    }
  }

  Settings getSettings() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_SETTINGS)) {
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
      try (PreparedStatement statement = connection.prepareStatement(Q.SAVE_SETTINGS)) {
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
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_DESIGN)) {
        try (ResultSet result = statement.executeQuery()) {
          if (result.next()) {
            return new Design(
              TerrainDesign.valueOf(result.getString(1)),
              SnakeDesign.valueOf(result.getString(2)),
              BgDesign.valueOf(result.getString(3))
            );
          }
          return new Design();
        }
      }
    }
  }

  void updateDesign(Design design) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.SAVE_DESIGN)) {
        statement.setString(1, design.terrain.name());
        statement.setString(2, design.snake.name());
        statement.setString(3, design.background.name());

        statement.setString(4, design.terrain.name());
        statement.setString(5, design.snake.name());
        statement.setString(6, design.background.name());
        statement.executeUpdate();
      }
    }
  }

  Stats loadStats() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_STATS)) {
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
      try (PreparedStatement statement = connection.prepareStatement(Q.SAVE_STATS)) {
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
