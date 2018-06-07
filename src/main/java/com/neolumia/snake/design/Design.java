package com.neolumia.snake.design;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class Design {

  public TerrainDesign terrain = TerrainDesign.SIMPLE;
  public BgDesign background = BgDesign.CYAN;

  public Design() {}

  public Design(TerrainDesign terrain, BgDesign background) {
    this.terrain = terrain;
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
