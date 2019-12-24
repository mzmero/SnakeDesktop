
package com.neolumia.snake.control;

import com.neolumia.snake.model.game.SnakePart;
import com.neolumia.snake.model.item.food.Apple;
import com.neolumia.snake.model.item.food.Banana;
import com.neolumia.snake.model.item.food.Pear;
import com.neolumia.snake.model.util.Direction;
import com.neolumia.snake.model.game.Tile;
import com.neolumia.snake.model.game.TileObject;
import com.neolumia.snake.model.item.food.Food;

import java.util.Iterator;
import java.util.Optional;

public final class SingleSnake extends Snake<SingleGame> {


  SingleSnake(SingleGame game) {
    super(game, Direction.WEST, node -> {
      final Optional<TileObject> object = game.getTerrain().get(node.getTile());
      return object.isPresent() && !(object.get() instanceof Food);
    });
  }

  /**
   * Initiates Snake, and/or Clearing part from previous Life.
   */
  @Override
  public void init() {

    Iterator iterator = getParts().iterator();

    while (iterator.hasNext()) {
      SnakePart sp = (SnakePart) iterator.next();
      Tile tile = game.getTerrain().getTile(sp.getX(), sp.getY()).get();
      game.getTerrain().put(tile, null);
    }
    this.getParts().clear();
    for (int i = 5; i > 0; i--) {
      final int x = game.getTerrain().getTileWidth() / 2 + i;
      final int y = game.getTerrain().getTileHeight() / 2;
      addPart(game.getTerrain().getTile(x, y).get(), Direction.EAST, true);
    }
  }

  /**
   * This method update info according to the object that was eaten.
   *
   * @param tile   - location
   * @param object - objects that was eater
   */
  @Override
  public void onEat(Tile tile, TileObject object) {
    game.spawnFood();
    game.getTerrain().put(tile, null);
    if (object instanceof Apple)
      game.setPoints(game.getPoints() + 10);
    if (object instanceof Banana)
      game.setPoints(game.getPoints() + 15);
    if (object instanceof Pear)
      game.setPoints(game.getPoints() + 20);
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
