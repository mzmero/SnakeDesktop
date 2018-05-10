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

package com.neolumia.snake.game.duo;

import com.neolumia.snake.game.Direction;
import com.neolumia.snake.game.GameType;
import com.neolumia.snake.game.Snake;
import com.neolumia.snake.game.SnakePart;
import com.neolumia.snake.game.Tile;
import com.neolumia.snake.game.TileObject;
import com.neolumia.snake.item.Item;
import javafx.scene.paint.Color;

public final class DuoSnake extends Snake<DuoGame> {

  private final boolean first;
  private Color color;

  DuoSnake(DuoGame game, boolean first, Color color, Direction direction) {
    super(game, direction);
    this.first = first;
    this.color = color;
  }

  @Override
  public void init() {
    for (int i = 0; i < 5; i++) {
      final int y = first ? game.getTerrain().getTileHeight() / 2 - 3 : game.getTerrain().getTileHeight() / 2 + 3;
      if (first) {
        final int x = game.getTerrain().getTileWidth() / 2 + i;
        addPart(game.getTerrain().getTile(x, y).get(), Direction.EAST, false);
      } else {
        final int x = game.getTerrain().getTileWidth() / 2 - i;
        addPart(game.getTerrain().getTile(x, y).get(), Direction.WEST, false);
      }
    }
  }

  @Override
  protected SnakePart createPart(Tile tile, int size, Direction direction) {
    return new SnakePart(tile, color, size, direction);
  }

  @Override
  public void onEat(Tile tile, TileObject object) {
    if (game.getType() == GameType.RETRO && object instanceof Item) {
      if (!((Item) object).getColor().equals(color)) {
        game.end();
        return;
      }
    }
    game.spawnFood(first);
    game.getTerrain().put(tile, null);
    game.setPoints(game.getPoints() + 1);
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
