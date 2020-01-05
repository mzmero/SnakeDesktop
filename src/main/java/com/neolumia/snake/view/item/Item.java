package com.neolumia.snake.view.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.neolumia.snake.view.option.GameType;
import com.neolumia.snake.view.item.food.Apple;
import com.neolumia.snake.view.item.food.Banana;
import com.neolumia.snake.view.item.food.Pear;
import com.neolumia.snake.view.item.food.RetroFood;
import com.neolumia.snake.view.item.questions.Questionlvl1;
import com.neolumia.snake.view.item.questions.Questionlvl2;
import com.neolumia.snake.view.item.questions.Questionlvl3;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.neolumia.snake.view.item.Item.Arguments.of;

/** An abstract representation of an item in the game */
public abstract class Item extends TileObject {

  private static final Map<Arguments, Supplier<Item>> items = Maps.newConcurrentMap();
  private static final Random random = new Random();

  private final ItemType type;
  private final String name;

  protected int x;
  protected int y;
  protected int size;

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
    register(of(ItemType.QUESTION, GameType.CLASSIC), Questionlvl1::new);
    register(of(ItemType.QUESTION, GameType.CLASSIC), Questionlvl2::new);
    register(of(ItemType.QUESTION, GameType.CLASSIC), Questionlvl3::new);
    register(of(ItemType.MOUSE, GameType.CLASSIC), Mouse::new);
    register(of(ItemType.FOOD, GameType.RETRO), RetroFood::new);
  }

  public static Map<Arguments, Supplier<Item>> getItems() {
    return items;
  }

  public static Optional<Item> random(GameType gameType, ItemType... types) {
    final Map<Arguments, Supplier<Item>> map = ImmutableMap.copyOf(items);

    final List<Supplier<Item>> valid =
        map.entrySet().stream()
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

  public final ItemType getType() {
    return type;
  }

  public final String getName() {
    return name;
  }

  public static class Arguments {

    private GameType[] gameTypes;
    private ItemType type;

    public GameType[] getGameTypes() {
      return gameTypes;
    }

    public void setGameTypes(GameType[] gameTypes) {
      this.gameTypes = gameTypes;
    }

    public ItemType getType() {
      return type;
    }

    public void setType(ItemType type) {
      this.type = type;
    }

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
