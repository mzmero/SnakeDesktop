package com.neolumia.snake.model;

import com.neolumia.snake.view.item.TileObject;

import java.util.HashMap;
import java.util.Map;
/*board model class*/
public class Terrain {

  private final int width;
  private final int height;
  private final Tile[][] tiles;
  private final Map<Tile, TileObject> objects = new HashMap<>(); // all the object on the board

  public Terrain(int width, int height) {
    this.width = width;
    this.height = height;
    this.tiles = new Tile[width][height];
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public Map<Tile, TileObject> getObjects() {
    return objects;
  }


}
