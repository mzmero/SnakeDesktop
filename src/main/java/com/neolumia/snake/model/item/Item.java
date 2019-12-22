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

package com.neolumia.snake.model.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.neolumia.snake.model.game.GameType;
import com.neolumia.snake.model.game.TileObject;
import com.neolumia.snake.model.item.food.Apple;
import com.neolumia.snake.model.item.food.Banana;
import com.neolumia.snake.model.item.food.Pear;
import com.neolumia.snake.model.item.food.RetroFood;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.neolumia.snake.model.item.Item.Arguments.of;

public abstract class Item extends TileObject {

  private static final Map<Arguments, Supplier<Item>> items = Maps.newConcurrentMap();
  private static final Random random = new Random();

  private final ItemType type;
  private final String name;

  protected int x;
  protected int y;
  protected int size;
  protected Color color;

  public Item(ItemType type, String name) {
    this.type = checkNotNull(type, "Item type cannot be null");
    this.name = checkNotNull(name, "Name cannot be null");
  }

  public static void register(Arguments args, Supplier<Item> item) {
    items.put(args, item);
  }

  public static void registerDefaults() {
    register(of(ItemType.FOOD, GameType.CLASSIC), Apple::new);
    register(of(ItemType.FOOD, GameType.CLASSIC), Banana::new);
    register(of(ItemType.FOOD, GameType.CLASSIC), Pear::new);

    register(of(ItemType.FOOD, GameType.RETRO), RetroFood::new);
  }

  public static Optional<Item> random(GameType gameType, ItemType... types) {
    final Map<Arguments, Supplier<Item>> map = ImmutableMap.copyOf(items);

    final List<Supplier<Item>> valid =
      map.entrySet()
        .stream()
        .filter(e -> e.getKey().matches(gameType, types))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());

    if (valid.isEmpty()) {
      return Optional.empty();
    }

    return Optional.ofNullable(valid.get(random.nextInt(valid.size())).get());
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public final ItemType getType() {
    return type;
  }

  public final String getName() {
    return name;
  }

  public static class Arguments {

    private GameType[] gameTypes;
    private ItemType type;

    Arguments(ItemType type, GameType... types) {
      this.type = type;
      this.gameTypes = types;
    }

    public static Arguments of(ItemType type, GameType... types) {
      return new Arguments(type, types);
    }

    boolean matches(GameType gameType, ItemType[] types) {

      boolean match = types.length == 0;
      if (!match) {
        for (ItemType type : types) {
          if (this.type == type) {
            match = true;
            break;
          }
        }
      }

      boolean found = gameTypes.length == 0;
      if (match && !found) {
        for (GameType type : gameTypes) {
          if (gameType == type) {
            found = true;
            break;
          }
        }
      }

      return match && found;
    }
  }
}
