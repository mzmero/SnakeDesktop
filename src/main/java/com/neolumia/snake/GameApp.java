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
import com.neolumia.snake.view.GameWindow;
import com.neolumia.snake.view.LoadingWindow;
import com.neolumia.snake.view.MenuWindow;
import com.neolumia.snake.view.WindowManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GameApp extends Application {

  private static final Logger LOGGER = LogManager.getLogger(GameApp.class);
  private final Path root = new File("").toPath();

  private Database database;
  private WindowManager windowManager;

  private Settings settings;
  private int highscore = -1;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    run(stage);
  }

  public synchronized void newGame(GameType type) {
    LOGGER.info("Creating a new game with type " + type.name());
    final Game game = new SingleGame(this, type);
    getWindowManager().request(new GameWindow(this, game));
    game.init();
    game.run();
  }

  public Path getRoot() {
    return root;
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

  private void run(Stage stage) {
    LOGGER.info("Starting application..");
    try {
      final long start = System.currentTimeMillis();

      final LoadingWindow window = new LoadingWindow();
      final Scene loading = new Scene(window.getLoader().getRoot());
      loading.setFill(Color.TRANSPARENT);

      stage.setScene(loading);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.show(); // Jap, das ist notwendig
      stage.hide();
      final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
      stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
      stage.setOpacity(1);
      stage.show();

      Item.registerDefaults();

      LOGGER.info("Using \"{}\" as root folder", root.toAbsolutePath());
      Files.createDirectories(root.resolve("cache"));

      database = new Database(this);
      database.init();

      settings = database.getSettings();
      highscore = database.getHighscore();

      windowManager = new WindowManager();
      windowManager.init();

      final long end = System.currentTimeMillis();
      LOGGER.info("Application initialized ({}ms)", end - start);

      stage.hide();

      windowManager.request(new MenuWindow(this));
    } catch (Throwable throwable) {
      LOGGER.error("Failed to start application", throwable);
    }
  }
}
