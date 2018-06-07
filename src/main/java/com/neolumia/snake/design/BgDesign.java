package com.neolumia.snake.design;

import javafx.scene.paint.Color;

import java.util.Optional;

public enum BgDesign {

  CYAN("color.aqua", "/lib/bg_cyan.png", Color.rgb(224, 255, 255)),
  WHITE("color.white", "/lib/bg_white.png", Color.WHITE);

  private final String name;
  private final String file;
  private final Color color;

  BgDesign(String name, String file, Color color) {
    this.name = name;
    this.file = file;
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public String getFile() {
    return file;
  }

  public Color getColor() {
    return color;
  }

  public Optional<BgDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  public Optional<BgDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }
}
