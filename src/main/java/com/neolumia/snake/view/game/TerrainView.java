package com.neolumia.snake.view.game;

import com.neolumia.snake.control.Game;
import com.neolumia.snake.model.Position;
import com.neolumia.snake.model.Terrain;
import com.neolumia.snake.model.Tile;
import com.neolumia.snake.view.item.TileObject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/** This class represents board matrix */
public final class TerrainView extends Pane {

  private final Game game;

  Terrain terrain;
  private int size;

  public TerrainView(Game game, int size) {
    this.game = game;
    int width = game.getSettings().size.getWidth();
    int height = game.getSettings().size.getHeight();
    this.terrain = new Terrain(width, height);
    this.size = size;
    game.getChildren().add(this);
  }

  public void init() {
    int width = terrain.getWidth();
    int height = terrain.getHeight();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        terrain.getTiles()[x][y] = new Tile(x, y,size, game.getApp().getDesign().terrain.getColor().apply(Position.of(x, y)));
        getChildren().add(terrain.getTiles()[x][y]);
      }
    }
    setWidth(size * width);
    setHeight(size * height);
  }

  public Optional<Tile> getTile(int x, int y) {
    try {
      return Optional.ofNullable(terrain.getTiles()[x][y]);
    } catch (IndexOutOfBoundsException ex) {
      return Optional.empty();
    }
  }

  public void setSize(int size) {
    this.size = size;
    Arrays.stream(terrain.getTiles()).flatMap(Arrays::stream).forEach(t -> t.setSize(size));
    terrain.getObjects().values().stream().filter(Objects::nonNull).forEach(o -> o.setSize(size));
  }

  public void put(Tile tile, TileObject pane) {

    final Pane old = terrain.getObjects().put(tile, pane);

    Platform.runLater(
        () -> {
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
    return Optional.ofNullable(terrain.getObjects().get(tile));
  }

  public int getTileWidth() {
    return terrain.getWidth();
  }

  public int getTileHeight() {
    return terrain.getHeight();
  }
}
