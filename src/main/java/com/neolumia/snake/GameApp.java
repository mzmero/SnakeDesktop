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

import com.neolumia.snake.game.Game;
import com.neolumia.snake.game.GameType;
import com.neolumia.snake.game.single.SingleGame;
import com.neolumia.snake.item.Item;
import com.neolumia.snake.settings.Settings;
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

public final class GameApp extends Application {

  private static final Logger LOGGER = LogManager.getLogger(GameApp.class);
  private static final Path ROOT = new File("").toPath();

  private static GameApp instance;

  private Database database;
  private WindowManager windowManager;

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

  public synchronized void newGame() {

    GameType type = GameType.RETRO;

    LOGGER.info("Creating a new game with type " + type.name());
    final Game game = new SingleGame(this, type);
    getWindowManager().request(new GameWindow(this, game));
    game.init();
    game.run();
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
