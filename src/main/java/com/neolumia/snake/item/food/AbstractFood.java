package com.neolumia.snake.item.food;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractFood extends Food {

  private final ImageView view;

  AbstractFood(String name, String file) {
    super(name);
    getChildren().add(view = new ImageView());
    view.setImage(new Image(getClass().getResourceAsStream("/lib/" + file)));
    view.setSmooth(true);
    view.setCache(true);
  }

  @Override
  public void setSize(int size) {
    super.setSize(size);
    view.setFitHeight(size);
    view.setFitWidth(size);
    view.setX(x * size);
    view.setY(y * size);
  }
}
