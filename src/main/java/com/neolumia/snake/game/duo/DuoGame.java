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

import com.neolumia.snake.GameApp;
import com.neolumia.snake.game.Direction;
import com.neolumia.snake.game.Game;
import com.neolumia.snake.game.GameType;
import com.neolumia.snake.game.Tile;
import com.neolumia.snake.item.Item;
import com.neolumia.snake.item.ItemType;

import java.util.Optional;

import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DuoGame extends Game {

  private static final Logger LOGGER = LogManager.getLogger(DuoGame.class);
  private static final Color FIRST = Color.BLUE;
  private static final Color SECOND = Color.RED;

  private final DuoSnake first = new DuoSnake(this, true, FIRST, Direction.WEST);
  private final DuoSnake second = new DuoSnake(this, false, SECOND, Direction.EAST);

  private Tile foodFirst;
  private Tile foodSecond;

  public DuoGame(GameApp app) {
    super(app, GameType.DUO);
  }

  @Override
  public void init() {
    first.init();
    second.init();

    spawnFood(true);
    spawnFood(false);
  }

  @Override
  public void tick() {
    first.tick();
    second.tick();
  }

  public DuoSnake getFirst() {
    return first;
  }

  public DuoSnake getSecond() {
    return second;
  }

  public Tile getFoodFirst() {
    return foodFirst;
  }

  public Tile getFoodSecond() {
    return foodSecond;
  }

  public void spawnFood(boolean first) {
    Tile tile;
    while (true) {
      int x = random.nextInt(terrain.getTileWidth());
      int y = random.nextInt(terrain.getTileHeight());
      final Optional<Tile> next = terrain.getTile(x, y);
      if (next.isPresent() && !getTerrain().get(next.get()).isPresent()) {
        tile = next.get();
        break;
      }
    }
    final Item item = Item.random(GameType.RETRO, ItemType.FOOD).orElseThrow(IllegalStateException::new);
    item.setColor(first ? FIRST : SECOND);
    if (first) {
      foodFirst = tile;
    } else {
      foodSecond = tile;
    }
    getTerrain().put(tile, item);
    LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
  }
}
