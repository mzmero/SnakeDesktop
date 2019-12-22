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

package com.neolumia.snake.model.game.single;

import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.model.game.Snake;
import com.neolumia.snake.model.game.Tile;
import com.neolumia.snake.model.game.TileObject;
import com.neolumia.snake.model.item.food.Food;

import java.util.Optional;

public final class SingleSnake extends Snake<SingleGame> {

  SingleSnake(SingleGame game) {
    super(game, Direction.WEST, node -> {
      final Optional<TileObject> object = game.getTerrain().get(node.getTile());
      return object.isPresent() && !(object.get() instanceof Food);
    });
  }

  @Override
  public void init() {
    for (int i = 5; i > 0; i--) {
      final int x = game.getTerrain().getTileWidth() / 2 + i;
      final int y = game.getTerrain().getTileHeight() / 2;
      addPart(game.getTerrain().getTile(x, y).get(), Direction.EAST, true);
    }
  }

  @Override
  public void onEat(Tile tile, TileObject object) {
    game.spawnFood();
    game.getTerrain().put(tile, null);
    game.setPoints(game.getPoints() + 1);
  }

  @Override
  protected int getFoodX() {
    return game.getFood().getTileX();
  }

  @Override
  protected int getFoodY() {
    return game.getFood().getTileY();
  }
}
