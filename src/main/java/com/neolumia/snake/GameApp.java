
package com.neolumia.snake;

import com.neolumia.snake.model.design.Design;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.game.GameType;
import com.neolumia.snake.control.SingleGame;
import com.neolumia.snake.model.item.Item;
import com.neolumia.snake.model.settings.Settings;
import com.neolumia.snake.view.GameWindow;
import com.neolumia.snake.view.MenuWindow;
import com.neolumia.snake.view.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is the main class of the application which runs all modules
 */
public final class GameApp extends Application {

  private static final Logger LOGGER = LogManager.getLogger(GameApp.class);
  private static final Path ROOT = new File("").toPath();

  private static GameApp instance;

  private Database database;
  private WindowManager windowManager;

  private Design design = new Design();
  private Stats stats = new Stats();

  private Settings settings;
  private int highscore = -1;

  public static void main(String[] args) {
    launch(args);
  }

  public static String t(String key, Object... args) {
    try {
      return MessageFormat.format(getBundle().getString(key), args);
    } catch (MissingResourceException ex) {
      return '!' + key.toUpperCase();
    }
  }

  public static ResourceBundle getBundle() {
    try {
      return ResourceBundle.getBundle("snake", instance.getSettings().locale.getLocale());
    } catch (Exception ex) {
      return ResourceBundle.getBundle("snake");
    }
  }

  @Override
  public void start(Stage stage) {
    instance = this;
    run();
  }

  public void newGame(GameType type) {
    synchronized (this) {
      LOGGER.info("Creating a new game with type " + type.name());
      Game game = (type == GameType.DUO) ?new SingleGame(this, type) : new SingleGame(this, type);
      getWindowManager().request(new GameWindow(this, game));
      game.init();
      game.run();
    }
  }

  public void updateDesign(Design design) {
    try {
      database.updateDesign(this.design = design);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void updateStats(Stats stats) throws SQLException {
    synchronized (this) {
      this.stats.playtime += stats.playtime;
      this.stats.games += stats.games;
      this.stats.items += stats.items;
      this.stats.walls += stats.walls;
    }
    database.updateStats(stats);
    System.out.println(this.stats.toString());
  }

  public Design getDesign() {
    return design;
  }

  public Stats getStats() {
    return stats;
  }

  public Settings getSettings() {
    return settings;
  }

  public int getHighscore() {
    return highscore;
  }

  public void setHighscore(int highscore) {
    this.highscore = highscore;
  }

  public Database getDatabase() {
    return database;
  }

  public WindowManager getWindowManager() {
    return windowManager;
  }

  Path getRoot() {
    return ROOT;
  }

  private void run() {
    LOGGER.info("Starting application..");
    try {
      final long start = System.currentTimeMillis();

      LOGGER.info("Using \"{}\" as root folder", ROOT.toAbsolutePath());

      database = new Database(this);
      database.init();

      settings = database.getSettings();
      highscore = database.getHighscore();

      design = database.loadDesign();
      stats = database.loadStats();

      LOGGER.info(stats);

      windowManager = new WindowManager();
      windowManager.init();

      Item.registerDefaults();

      final long end = System.currentTimeMillis();
      LOGGER.info("Application initialized ({}ms)", end - start);

      windowManager.request(new MenuWindow(this));
      LOGGER.info("Application is now ready");
    } catch (Throwable throwable) {
      LOGGER.error("Could not start application", throwable);
    }
  }
}
