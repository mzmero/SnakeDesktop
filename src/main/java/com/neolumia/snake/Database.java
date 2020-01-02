
package com.neolumia.snake;

import com.neolumia.snake.view.design.Design;
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
import java.util.ArrayList;

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
      try (PreparedStatement statement = connection.prepareStatement(Q.TABLE_PlAYER_STATS)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(Q.TABLE_SETTINGS)) {
        statement.executeUpdate();
      }
      try (PreparedStatement statement = connection.prepareStatement(Q.PLAYER_TABLE)) {
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
        statement.setString(2, game.getApp().getPlayerName());
        statement.executeUpdate();
      }
    }
  }

  public Settings getSettings(String playerName) throws SQLException {

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_SETTINGS)) {
        statement.setString(1, playerName);
        try (ResultSet result = statement.executeQuery()) {

          Locale locale = Settings.DEFAULT_LOCALE;
          Difficulty difficulty = Settings.DEFAULT_DIFFICULTY;
          Size size = Settings.DEFAULT_SIZE;
          String name = playerName;
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

        statement.setInt(1,player_ID(settings.playerName));
        statement.setInt(2, settings.locale.getId());
        statement.setInt(3, settings.difficulty.getId());
        statement.setInt(4, settings.size.getId());
        statement.setString(5, settings.playerName);
        statement.setBoolean(6, settings.leaderboard);

        statement.setInt(7,player_ID(settings.playerName));
        statement.setInt(8, settings.locale.getId());
        statement.setInt(9, settings.difficulty.getId());
        statement.setInt(10, settings.size.getId());
        statement.setString(11, settings.playerName);
        statement.setBoolean(12, settings.leaderboard);
        statement.executeUpdate();
      }
    }
  }

 public Design loadDesign(String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_DESIGN)) {
        statement.setInt(1, player_ID(playerName));
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

  void updateDesign(Design design,String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.SAVE_DESIGN)) {
        statement.setInt(1, player_ID(playerName));
        statement.setString(2, design.terrain.name());
        statement.setString(3, design.snake.name());
        statement.setString(4, design.background.name());

        statement.setInt(5, player_ID(playerName));
        statement.setString(6, design.terrain.name());
        statement.setString(7, design.snake.name());
        statement.setString(8, design.background.name());
        statement.executeUpdate();
      }
    }
  }

  public Stats loadStats(String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_PLAYER_STATS)) {
        statement.setInt(1,player_ID(playerName));
        try (ResultSet result = statement.executeQuery()) {
          if (result.next()) {
            return new Stats(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4));
          }
          return new Stats();
        }
      }
    }
  }

  void updateStats(Stats stats,String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.SAVE_PLAYER_STATS)) {
        statement.setInt(1, player_ID(playerName));
        statement.setDouble(2, stats.playtime);
        statement.setInt(3, stats.games);
        statement.setInt(4, stats.items);
        statement.setInt(5, stats.walls);

        statement.setInt(6, player_ID(playerName));
        statement.setDouble(7, stats.playtime);
        statement.setInt(8, stats.games);
        statement.setInt(9, stats.items);
        statement.setInt(10, stats.walls);
        statement.executeUpdate();
      }
    }
  }
  //"CREATE TABLE IF NOT EXISTS settings (id BIGINT UNSIGNED NOT NULL PRIMARY KEY, locale INT NOT NULL, difficulty INT NOT NULL, size INT NOT NULL, name VARCHAR(255) NOT NULL, leaderboard TINYINT(1) UNSIGNED NOT NULL);";

  public ArrayList<Settings> GetAllsetttings() throws SQLException {
    ArrayList<Settings> settings = new ArrayList<Settings>();
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.GET_SETTINGS)) {
        try (ResultSet result = statement.executeQuery()) {
          while(result.next()) {
            Settings setting =  new Settings(
              Locale.fromId(result.getInt(2)),
              Difficulty.fromId(result.getInt(3)),
              Size.fromId(result.getInt(4)),
              result.getString(5),
              result.getBoolean(6)
            );
            settings.add(setting);
          }

        }
      }
    }
    return settings;
  }
  public Settings getPlayerSettings(String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.LOAD_PLAYER_SETTINGS)) {
        statement.setString(1,playerName);
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
  public Integer player_ID(String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.GET_PLAYER_BY_ID)) {
        statement.setString(1, playerName);
        try (ResultSet result = statement.executeQuery()) {

          if (result.next()) {
           int id = result.getInt(1);
           return id;

          }
          return -1;

        }
      }
    }

  }
  public void newPlayer(String playerName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(Q.PLAYER_SAVE)) {
        statement.setString(1, playerName);

        statement.executeUpdate();
      }
    }


  }


}
