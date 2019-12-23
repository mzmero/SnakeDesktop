
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

/**
 * This class represents a single game - one player - and the snake associated with
 */
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
