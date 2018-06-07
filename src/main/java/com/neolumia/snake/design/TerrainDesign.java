package com.neolumia.snake.design;

import com.neolumia.snake.util.Position;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Function;

public enum TerrainDesign {

  SIMPLE("terrain.simple", "/lib/terrain_retro.png", pos -> Color.rgb(157, 213, 3)),
  GRASS("terrain.grass", "/lib/terrain_grass.png", pos -> (pos.getX() + pos.getY()) % 2 == 0 ? Color.rgb(111, 169, 111) : Color.rgb(127, 188, 124)),
  CHESS("terrain.chess", "/lib/terrain_chess.png", pos -> (pos.getX() + pos.getY()) % 2 == 0 ? Color.rgb(207, 137, 72) : Color.rgb(255, 204, 156));

  private final String name;
  private final String file;
  private final Function<Position, Color> color;

  TerrainDesign(String name, String file, Function<Position, Color> color) {
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

  public Function<Position, Color> getColor() {
    return color;
  }

  public Optional<TerrainDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  public Optional<TerrainDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }
}
