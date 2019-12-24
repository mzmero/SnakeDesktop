
package com.neolumia.snake.model.design.option;

import com.neolumia.snake.model.design.DesignOption;
import javafx.scene.paint.Color;

import java.util.Optional;

public enum BgDesign implements DesignOption<BgDesign> {
  /**
   * Background designs : CYAN and WHITE in order to let users customize the design of their game as they want
   */
  CYAN("color.aqua", "/lib/bg_cyan.png", Color.rgb(224, 255, 255)),
  WHITE("color.white", "/lib/bg_white.png", Color.WHITE);


  private final String name;
  private final String file;
  private final Color color;

  /**
   * Constructor for the design
   * @param name - name of the design
   * @param file - the suitable image from the lib folder
   * @param color - specific color associated
   */
  BgDesign(String name, String file, Color color) {
    this.name = name;
    this.file = file;
    this.color = color;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getFile() {
    return file;
  }

  /**
   * Changes the design of the game according to the user's selection
   * @return
   */
  @Override
  public Optional<BgDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  @Override
  public Optional<BgDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }

  public Color getColor() {
    return color;
  }
}
