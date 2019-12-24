
package com.neolumia.snake.model.game;

import javafx.scene.paint.Color;

public enum GameType {

  /**
   * ENUM types of the game : CLASSIC, RETRO, DUO . CLASSIC is the default option in the game
   */
  CLASSIC(Color.LIGHTGREEN, "/lib/title_classic.png"),
  RETRO(Color.WHITE, "/lib/title_retro.png"),
  DUO(Color.WHITE, "/lib/title_duo.png");

  /**
   * background : background color of the game
   * file : image background of the game
   */
  private final Color background;
  private final String file;

  GameType(Color background, String file) {
    this.background = background;
    this.file = file;
  }

  public Color getBackground() {
    return background;
  }

  public String getFile() {
    return file;
  }

  public GameType next() {
    return values()[(ordinal() + 1) % values().length];
  }
}
