
package com.neolumia.snake.model.game;

import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.util.Position;
import com.neolumia.snake.view.item.TileObject;
import javafx.application.Platform;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This class represents board matrix
 */
public final class Terrain extends Pane {

  private final Game game;
  private final int width;
  private final int height;
  private final Tile[][] tiles;
  private final Map<Tile, TileObject> objects = new HashMap<>();

  private int size;

  public Terrain(Game game, int size) {
    this.game = game;
    this.width = game.getSettings().size.getWidth();
    this.height = game.getSettings().size.getHeight();
    this.tiles = new Tile[width][height];
    this.size = size;

    game.getChildren().add(this);
  }

  public void init() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        tiles[x][y] = new Tile(x, y, size, game.getApp().getDesign().terrain.getColor().apply(Position.of(x, y)));
        getChildren().add(tiles[x][y]);
      }
    }
    setWidth(size * width);
    setHeight(size * height);
  }

  public Optional<Tile> getTile(int x, int y) {
    try {
      return Optional.ofNullable(tiles[x][y]);
    } catch (IndexOutOfBoundsException ex) {
      return Optional.empty();
    }
  }

  public void setSize(int size) {
    this.size = size;
    Arrays.stream(tiles).flatMap(Arrays::stream).forEach(t -> t.setSize(size));
    objects.values().stream().filter(Objects::nonNull).forEach(o -> o.setSize(size));
  }

  public void put(Tile tile, TileObject pane) {

    final Pane old = objects.put(tile, pane);

    Platform.runLater(() -> {
      if (old != null) {
        this.getChildren().remove(old);
      }

      if (pane != null) {

        pane.setX(tile.getTileX());
        pane.setY(tile.getTileY());
        pane.setSize(size);
        pane.init();

        this.getChildren().add(pane);
      }
    });
  }

  public Optional<TileObject> get(Tile tile) {
    return Optional.ofNullable(objects.get(tile));
  }

  public int getTileWidth() {
    return width;
  }

  public int getTileHeight() {
    return height;
  }
}
