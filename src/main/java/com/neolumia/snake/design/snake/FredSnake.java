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

package com.neolumia.snake.design.snake;

import com.neolumia.snake.util.Direction;
import com.neolumia.snake.game.SnakePart;
import com.neolumia.snake.game.Tile;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.annotation.Nullable;

public class FredSnake extends SnakePart {

  private static final int MARGIN = 4;

  private final Circle head = new Circle(0, Color.BLACK);
  private final Rectangle body = new Rectangle(0, 0, Color.BLACK);

  public FredSnake(SnakePart parent, Tile tile, Direction direction, @Nullable Color color) {
    super(parent, tile, direction, color == null ? Color.WHITE : color);
  }

  @Override
  public void init() {
    head.setFill(getColor());
    body.setFill(getColor());
    update();
  }

  @Override
  public void update() {
    head.setRadius(getSize() / 2);
    body.setFill(getColor());

    if (isHead()) {

      // HEAD

      switch (getDirection()) {
        case EAST:
          head.setCenterX(getX() * getSize() + (getSize() / 2));
          head.setCenterY(getY() * getSize() + (getSize() / 2));

          body.setX(getX() * getSize() + getSize() / 2);
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
          body.setY(getY() * getSize());
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
          body.setY(getY() * getSize() + getSize() / 2);
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

  private Circle eye(int x, int y) {
    final Circle eye = new Circle(1.5, Color.BLACK);
    eye.setCenterX(x);
    eye.setCenterY(y);
    return eye;
  }

  private void arc(int centerX, int centerY, float angle) {
    Arc arc = new Arc();
    arc.setFill(getColor());
    arc.setType(ArcType.ROUND);
    arc.setRadiusX(getSize() - (MARGIN / 2));
    arc.setRadiusY(getSize() - (MARGIN / 2));
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
