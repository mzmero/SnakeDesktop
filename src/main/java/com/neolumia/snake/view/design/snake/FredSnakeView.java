
package com.neolumia.snake.view.design.snake;

import com.neolumia.snake.control.Snake;
import com.neolumia.snake.view.game.SnakePartView;
import com.neolumia.snake.model.Tile;
import com.neolumia.snake.model.Direction;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.annotation.Nullable;

public class FredSnakeView extends SnakePartView {
  /**
   * FredSnake is the default snake in the game
   */
  private static final int MARGIN = 4;

  /**
   * Head of the snake
   */
  private final Circle head = new Circle(0, Color.BLACK);

  /**
   * Body of the snake
   */
  private final Rectangle body = new Rectangle(0, 0, Color.BLACK);

  /**
   * Constructs the FredSnake
   * @param snake
   * @param tile
   * @param direction
   * @param color
   */
  public FredSnakeView(Snake snake, Tile tile, Direction direction, @Nullable Color color) {
    super(snake, tile, direction, color == null ? Color.WHITE : color);
  }

  /**
   * Initializer method for the snake
   */
  @Override
  public void init() {
    head.setFill(getColor());
    body.setFill(getColor());
    update();
  }

  /**
   * This method updates the parts of the FredSnake and the heard according to the direction of each one on board on board
   */
  @Override
  public void update() {
    head.setRadius(getSize() / 2);
    body.setFill(getColor());

    if (isTail()) {

      // TAIL

      if (getP().getDirection() == Direction.EAST) {
        tail(getX() * getSize(), getY() * getSize() + getSize() - MARGIN / 2, getSize() - (MARGIN / 2), getSize() - MARGIN, 0.0F);
        return;
      }

      if (getP().getDirection() == Direction.WEST) {
        tail(getX() * getSize() + getSize(), getY() * getSize() + getSize() - MARGIN / 2, getSize() - MARGIN / 2, getSize() - MARGIN, 90.0F);
        return;
      }

      if (getP().getDirection() == Direction.NORTH) {
        tail(getX() * getSize() + MARGIN / 2, getY() * getSize() + getSize(), getSize() - MARGIN, getSize() - MARGIN, 0.0F);
        return;
      }

      if (getP().getDirection() == Direction.SOUTH) {
        tail(getX() * getSize() + MARGIN / 2, getY() * getSize(), getSize() - MARGIN, getSize() - MARGIN, 270.0F);
      }

      return;
    }

    if (isHead()) {

      // HEAD

      switch (getDirection()) {
        case EAST:
          head.setCenterX(getX() * getSize() + (getSize() / 2));
          head.setCenterY(getY() * getSize() + (getSize() / 2));

          body.setX(getX() * getSize() + getSize() / 2 + 2);
          body.setY(getY() * getSize() + MARGIN / 2);
          body.setWidth(getSize() / 2);
          body.setHeight(getSize() - MARGIN);

          add(
            head,
            body,
            eye(getX() * getSize() + getSize() / 2, getY() * getSize() + (getSize() / 2) - 5),
            eye(getX() * getSize() + getSize() / 2, getY() * getSize() + (getSize() / 2) + 5)
          );
          break;
        case WEST:
          head.setCenterX(getX() * getSize() + getSize() / 2);
          head.setCenterY(getY() * getSize() + getSize() / 2);

          body.setX(getX() * getSize());
          body.setY(getY() * getSize() + MARGIN / 2);
          body.setWidth(getSize() / 2);
          body.setHeight(getSize() - MARGIN);

          add(
            head,
            body,
            eye(getX() * getSize() + getSize() / 2, getY() * getSize() + getSize() / 2 - 5),
            eye(getX() * getSize() + getSize() / 2, getY() * getSize() + getSize() / 2 + 5)
          );
          break;
        case NORTH:
          head.setCenterX(getX() * getSize() + getSize() / 2);
          head.setCenterY(getY() * getSize() + getSize() / 2);

          body.setX(getX() * getSize() + MARGIN / 2);
          body.setY(getY() * getSize() - 2);
          body.setWidth(getSize() - MARGIN);
          body.setHeight(getSize() / 2);

          add(
            head,
            body,
            eye(getX() * getSize() + getSize() / 2 - 5, getY() * getSize() + getSize() / 2),
            eye(getX() * getSize() + getSize() / 2 + 5, getY() * getSize() + getSize() / 2)
          );
          break;
        case SOUTH:
          head.setCenterX(getX() * getSize() + getSize() / 2);
          head.setCenterY(getY() * getSize() + getSize() / 2);

          body.setX(getX() * getSize() + MARGIN / 2);
          body.setY(getY() * getSize() + getSize() / 2 + 2);
          body.setWidth(getSize() - MARGIN);
          body.setHeight(getSize() / 2);

          add(
            head,
            body,
            eye(getX() * getSize() + getSize() / 2 - 5, getY() * getSize() + getSize() / 2),
            eye(getX() * getSize() + getSize() / 2 + 5, getY() * getSize() + getSize() / 2)
          );
          break;
      }
      return;
    }

    // BODY

    if (getDirection() == getP().getDirection()) {
      if (getDirection() == Direction.WEST || getDirection() == Direction.EAST) {
        body.setX(getX() * getSize());
        body.setY(getY() * getSize() + MARGIN / 2);
        body.setWidth(getSize());
        body.setHeight(getSize() - MARGIN);
      } else {
        body.setX(getX() * getSize() + MARGIN / 2);
        body.setY(getY() * getSize());
        body.setWidth(getSize() - MARGIN);
        body.setHeight(getSize());
      }
      add(body);
      return;
    }

    // <-->

    if (getDirection() == Direction.WEST && getP().getDirection() == Direction.SOUTH) {
      arc(getX() * getSize(), getY() * getSize(), 270.0F);
      return;
    }
    if (getDirection() == Direction.NORTH && getP().getDirection() == Direction.EAST) {
      arc(getX() * getSize(), getY() * getSize(), 270.0F);
      return;
    }

    // <-->

    if (getDirection() == Direction.WEST && getP().getDirection() == Direction.NORTH) {
      arc(getX() * getSize(), getY() * getSize() + getSize(), 0.0F);
      return;
    }
    if (getDirection() == Direction.SOUTH && getP().getDirection() == Direction.EAST) {
      arc(getX() * getSize(), getY() * getSize() + getSize(), 0.0F);
      return;
    }

    // <-->

    if (getDirection() == Direction.EAST && getP().getDirection() == Direction.SOUTH) {
      arc(getX() * getSize() + getSize(), getY() * getSize(), 180.0F);
      return;
    }
    if (getDirection() == Direction.NORTH && getP().getDirection() == Direction.WEST) {
      arc(getX() * getSize() + getSize(), getY() * getSize(), 180.0F);
      return;
    }

    // <-->

    if (getDirection() == Direction.EAST && getP().getDirection() == Direction.NORTH) {
      arc(getX() * getSize() + getSize(), getY() * getSize() + getSize(), 90.0F);
      return;
    }
    if (getDirection() == Direction.SOUTH && getP().getDirection() == Direction.WEST) {
      arc(getX() * getSize() + getSize(), getY() * getSize() + getSize(), 90.0F);
    }
  }

  /**
   * Sets the eyes of the snake
   * @param x - x coordinate
   * @param y - y coordinate
   * @return
   */
  private Circle eye(int x, int y) {
    final Circle eye = new Circle(1.5, Color.BLACK);
    eye.setCenterX(x);
    eye.setCenterY(y);
    return eye;
  }

  /**
   * Sets the arc - body part of the snake
   * @param centerX - x coordinate
   * @param centerY - y coordinate
   * @param angle
   */
  private void arc(int centerX, int centerY, float angle) {
    tail(centerX, centerY, getSize() - (MARGIN / 2), getSize() - (MARGIN / 2), angle);
  }

  /**
   * Sets the tail of the snake
   * @param centerX - x coordinate
   * @param centerY - y coordinate
   * @param radiusX - x rad
   * @param radiusY - y rad
   * @param angle
   */
  private void tail(int centerX, int centerY, int radiusX, int radiusY, float angle) {
    Arc arc = new Arc();
    arc.setFill(getColor());
    arc.setType(ArcType.ROUND);
    arc.setRadiusX(radiusX);
    arc.setRadiusY(radiusY);
    arc.setCenterX(centerX);
    arc.setCenterY(centerY);
    arc.setStartAngle(angle);
    arc.setLength(90.0F);
    add(arc);
  }

  @SafeVarargs
  private final <T extends Node> void add(final T... elements) {
    Platform.runLater(() -> {
      getChildren().clear();
      getChildren().addAll(elements);
    });
  }
}
