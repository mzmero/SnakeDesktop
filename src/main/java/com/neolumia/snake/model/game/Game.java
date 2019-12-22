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

import com.neolumia.snake.GameApp;
import com.neolumia.snake.Stats;
import com.neolumia.snake.model.settings.Settings;
import com.neolumia.snake.view.DeadWindow;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Game extends Pane {

  private static final Logger LOGGER = LogManager.getLogger(Game.class);

  protected final Random random = new Random();
  protected final Stats stats = new Stats();

  protected final GameApp app;
  protected final GameType type;
  protected final Terrain terrain;

  private boolean running;
  private boolean paused;
  private boolean auto;
  private int points;
  private int lives;
  private long start;

  public Game(GameApp app, GameType type) {
    this.app = app;
    this.type = type;
    this.lives = 3;
    terrain = new Terrain(this, app.getWindowManager().getStage().isMaximized() ? 43 : 32);
    terrain.init();

    app.getWindowManager().getStage().maximizedProperty().addListener((ob, o, n) -> terrain.setSize(n ? 42 : 32));
  }

  public GameApp getApp() {
    return app;
  }

  public GameType getType() {
    return type;
  }

  public Terrain getTerrain() {
    return terrain;
  }

  public Settings getSettings() {
    return app.getSettings();
  }

  public Stats getStats() {
    return stats;
  }

  public int getPoints() {
    return points;
  }

  public int getLives() {
    return lives;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public void setLives(int lives) { this.lives = lives; }

  public boolean isPaused() {
    return paused;
  }

  public boolean setPaused(boolean paused) {
    return this.paused = paused;
  }

  public boolean isAuto() {
    return auto;
  }

  public void setAuto(boolean auto) {
    this.auto = auto;
  }

  /**
   *   check remaining lives and handle accordingly
   */
  public void end() {

    running = false;
    long stop = System.currentTimeMillis();

    final boolean won = points > app.getHighscore();

    try {
      app.getDatabase().saveGame(this);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    if (won) {
      app.setHighscore(points);
      LOGGER.info("The new highscore is " + points);
    }

    stats.games++;
    stats.playtime += TimeUnit.MILLISECONDS.toSeconds(stop - start);

    try {
      app.updateStats(stats);
    } catch (SQLException ex) {
      LOGGER.error("Could not update statistics", ex);
    }

    LOGGER.info("The game has ended.");
    Platform.runLater(() -> app.getWindowManager().request(new DeadWindow(app, this, won)));
  }

  /**
   *
   */
  public void run() {
    running = true;
    start = System.currentTimeMillis();

    new Thread(() -> {
      try {
        long start;
        long diff;
        long sleep;
        while (running) {
          start = System.currentTimeMillis();
          if (!paused) {
            tick();
          }
          diff = System.currentTimeMillis() - start;
          sleep = (int) (1000 / 60 - diff);
          if (sleep > 0) {
            Thread.sleep(sleep);
          }
        }
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }).start();
  }

  public abstract void init();

  public abstract void tick();
}
