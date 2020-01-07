
package com.neolumia.snake.view.option;

import com.neolumia.snake.view.design.DesignOption;
import com.neolumia.snake.model.Position;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Function;

public enum TerrainDesign implements DesignOption<TerrainDesign> {
  /**
   * Terrain designs : SIMPLE , CHESS , GRASS
   */
  SIMPLE("terrain.simple", "/lib/terrain_retro.png", pos -> Color.rgb(157, 213, 3)),
  GRASS("terrain.grass", "/lib/terrain_grass.png", pos -> (pos.getX() + pos.getY()) % 2 == 0 ? Color.rgb(111, 169, 111) : Color.rgb(127, 188, 124)),
  CHESS("terrain.chess", "/lib/terrain_chess.png", pos -> (pos.getX() + pos.getY()) % 2 == 0 ? Color.rgb(207, 137, 72) : Color.rgb(255, 204, 156));

  private final String name;
  private final String file;
  private final Function<Position, Color> color;

  /**
   * Constructs the terrain design
   * @param name - name of design
   * @param file - image associated with design
   * @param color - suitable color for the design
   */
  TerrainDesign(String name, String file, Function<Position, Color> color) {
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

  @Override
  public Optional<TerrainDesign> before() {
    if (ordinal() - 1 < 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() - 1]);
  }

  @Override
  public Optional<TerrainDesign> next() {
    if (ordinal() + 1 >= values().length) {
      return Optional.empty();
    }
    return Optional.ofNullable(values()[ordinal() + 1]);
  }

  public Function<Position, Color> getColor() {
    return color;
  }
}
