package com.neolumia.snake.control;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.Stats;
import com.neolumia.snake.model.History;
import com.neolumia.snake.view.option.GameType;
import com.neolumia.snake.view.game.TerrainView;
import com.neolumia.snake.model.Settings;
import com.neolumia.snake.view.DeadWindow;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Game extends Pane {

  /** Logger for documenting actions in app */
  private static final Logger LOGGER = LogManager.getLogger(Game.class);
  /** Random instance for generating random numbers */
  protected final Random random = new Random();
  /** Stats indicates the info */
  protected final Stats stats = new Stats();

  protected final GameApp app;
  protected final GameType type;
  protected final TerrainView terrain;
  static String playerName;

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
    playerName = app.getSettings().playerName;
    terrain = new TerrainView(this, app.getWindowManager().getStage().isMaximized() ? 43 : 32);
    terrain.init();
    app.getWindowManager()
        .getStage()
        .maximizedProperty()
        .addListener((ob, o, n) -> terrain.setSize(n ? 42 : 32));
  }

  public GameApp getApp() {
    return app;
  }

  public GameType getType() {
    return type;
  }

  public TerrainView getTerrain() {
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

  public void setLives(int lives) {
    this.lives = lives;
  }

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

  /** check remaining lives and handle accordingly */
  public void end() {

    running = false;
    long stop = System.currentTimeMillis();

    final boolean won = points > app.getHighscore();

    try {
      app.getDatabase().saveGame(this);
      SysData.getInstance().addGameToHistory(new History(getSettings().playerName, points, lives));
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
      //TODO update player stats
      //TODO save stats to Json

      app.updateStats(stats);
    } catch (SQLException ex) {
      LOGGER.error("Could not update statistics", ex);
    }

    LOGGER.info("The game has ended.");
    Platform.runLater(() -> app.getWindowManager().request(new DeadWindow(app, this, won)));
  }

  /** This method handles running the game */
  public void run() {
    running = true;
    start = System.currentTimeMillis();

    new Thread(
            () -> {
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
            })
        .start();
  }

  protected abstract void moveMouse();

  public abstract void init();

  public abstract void tick();
}
