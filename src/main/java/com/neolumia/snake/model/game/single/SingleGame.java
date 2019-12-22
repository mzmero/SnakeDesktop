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

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.game.Game;
import com.neolumia.snake.model.game.GameType;
import com.neolumia.snake.model.game.Tile;
import com.neolumia.snake.model.item.Item;
import com.neolumia.snake.model.item.ItemType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public final class SingleGame extends Game {

  private static final Logger LOGGER = LogManager.getLogger(SingleGame.class);

  private final SingleSnake snake = new SingleSnake(this);
  private Tile food;

  public SingleGame(GameApp app, GameType type) {
    super(app, type);
  }

  @Override
  public void init() {
    snake.init();
    spawnFood();
  }

  @Override
  public void tick() {
    snake.tick();
  }

  public SingleSnake getSnake() {
    return snake;
  }


  public Tile getFood() {
    return food;
  }


  //TODO
  /**
   * spawn various foods corresponding to thier diffenernt timers and
   * restrictions
   */
  public void spawnFood() {
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
    final Optional<Item> item = Item.random(type, ItemType.FOOD);
    getTerrain().put(food = tile, item.orElseThrow(IllegalStateException::new));
    LOGGER.info("Item spawned x={}, y={}", tile.getTileX(), tile.getTileY());
  }
}
