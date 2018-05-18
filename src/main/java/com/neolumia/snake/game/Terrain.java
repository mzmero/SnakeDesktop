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

package com.neolumia.snake.game;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

  public Terrain(Game game) {
    this.game = game;
    this.width = 30;
    this.height = 15;
    this.tiles = new Tile[width][height];

    game.getChildren().add(this);
  }

  public void init() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        tiles[x][y] = new Tile(x, y, 32, color(x, y));
        getChildren().add(tiles[x][y]);
      }
    }
  }

  public Optional<Tile> getTile(int x, int y) {
    try {
      return Optional.ofNullable(tiles[x][y]);
    } catch (IndexOutOfBoundsException ex) {
      return Optional.empty();
    }
  }

  public void setSize(int size) {
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
        this.getChildren().add(pane);

        pane.setX(tile.getTileX());
        pane.setY(tile.getTileY());
        pane.setSize(32);
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

  private Color color(int x, int y) {
    if (game.getType() == GameType.RETRO) {
      return Color.rgb(157, 213, 3);
    }
    return (y + x) % 2 == 0 ? Color.rgb(111, 169, 111) : Color.rgb(127, 188, 124);
  }
}
