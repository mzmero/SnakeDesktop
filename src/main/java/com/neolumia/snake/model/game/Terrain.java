/*
 * This file is part of Snake, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 Neolumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.neolumia.snake.model.game;

import com.neolumia.snake.model.util.Position;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
