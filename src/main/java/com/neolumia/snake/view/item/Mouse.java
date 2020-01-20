package com.neolumia.snake.view.item;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/*
mouse class thats responsible for showing the mouse
 */
public class Mouse extends Item {
  private final ImageView view;

  public Mouse() {
    super(ItemType.MOUSE,"mouse");
    getChildren().add(view = new ImageView());
    view.setImage(new Image(getClass().getResourceAsStream("/lib/" + "mouse.png")));
    view.setSmooth(true);
    view.setCache(true);
  }


  public void setSize(int size) {
    super.setSize(size);
    view.setFitHeight(size);
    view.setFitWidth(size);
    view.setX(getFoodModel().getX() * size);
    view.setY(getFoodModel().getY() * size);
  }
}
