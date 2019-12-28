
package com.neolumia.snake.model.design;

import com.google.common.base.MoreObjects;
import com.neolumia.snake.view.option.BgDesign;
import com.neolumia.snake.view.option.SnakeDesign;
import com.neolumia.snake.view.option.TerrainDesign;

import java.util.Objects;

public final class Design {

  /**
   * Defines the design of the game (with defaults) and option to customize
   */

  public TerrainDesign terrain = TerrainDesign.SIMPLE;
  public SnakeDesign snake = SnakeDesign.FRED;
  public BgDesign background = BgDesign.CYAN;

  public Design() {}

  public Design(TerrainDesign terrain, SnakeDesign snake, BgDesign background) {
    this.terrain = terrain;
    this.snake = snake;
    this.background = background;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Design)) {
      return false;
    }
    final Design design = (Design) o;
    return terrain == design.terrain && background == design.background;
  }

  @Override
  public int hashCode() {
    return Objects.hash(terrain, background);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("terrain", terrain)
      .add("background", background)
      .toString();
  }
}
