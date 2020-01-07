package com.neolumia.snake.view.item;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import javafx.scene.layout.Pane;

/** A general representation of a board tile in the game. */
public abstract class TileObject extends Pane {

  /** x - x coordinate y - y coordinate size - the size of the size */
  private int x;
  private int y;
  private int size;

  public TileObject() {}

  public TileObject(int size) {
    this.size = size;
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

  public void init() {}

  protected ToStringHelper toStringHelper() {
    return MoreObjects.toStringHelper(this).add("x", x).add("y", y).add("size", size);
  }

  @Override
  public String toString() {
    return toStringHelper().toString();
  }
}
