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

package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.design.DesignOption;
import com.neolumia.snake.design.option.BgDesign;
import com.neolumia.snake.design.Design;
import com.neolumia.snake.design.option.SnakeDesign;
import com.neolumia.snake.design.option.TerrainDesign;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import static com.neolumia.snake.GameApp.t;

public final class DesignWindow extends Window {

  private final GameApp app;
  private final ToggleGroup menu = new ToggleGroup();

  private TerrainDesign terrainDesign;
  private SnakeDesign snakeDesign;
  private BgDesign bgDesign;
  private Node node;

  @FXML private GridPane grid;
  @FXML private ToggleButton terrain;
  @FXML private ToggleButton snake;
  @FXML private ToggleButton background;

  @FXML private ImageView before;
  @FXML private ImageView next;
  @FXML private ImageView current;
  @FXML private Label currentName;

  DesignWindow(GameApp app) {
    this.app = app;

    terrain.setToggleGroup(menu);
    snake.setToggleGroup(menu);
    background.setToggleGroup(menu);

    menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.equals(terrain)) {
        grid.getChildren().removeAll(node);
        grid.add(node = render("terrain"), 0, 0);
        update(terrainDesign);
        return;
      }
      if (newValue.equals(snake)) {
        grid.getChildren().removeAll(node);
        grid.add(node = render("snake"), 0, 0);
        update(snakeDesign);
      }
      if (newValue.equals(background)) {
        grid.getChildren().removeAll(node);
        grid.add(node = render("background"), 0, 0);
        update(bgDesign);
      }
    });

    this.terrainDesign = app.getDesign().terrain;
    this.snakeDesign = app.getDesign().snake;
    this.bgDesign = app.getDesign().background;

    menu.selectToggle(terrain);
  }

  @FXML
  public void beforeTerrain() {
    terrainDesign.before().ifPresent(d -> {
      this.terrainDesign = d;
      update(d);
    });
  }

  @FXML
  public void nextTerrain() {
    terrainDesign.next().ifPresent(d -> {
      this.terrainDesign = d;
      update(d);
    });
  }

  @FXML
  public void beforeSnake() {
    snakeDesign.before().ifPresent(d -> {
      this.snakeDesign = d;
      update(d);
    });
  }

  @FXML
  public void nextSnake() {
    snakeDesign.next().ifPresent(d -> {
      this.snakeDesign = d;
      update(d);
    });
  }

  @FXML
  public void beforeBackground() {
    bgDesign.before().ifPresent(d -> {
      this.bgDesign = d;
      update(d);
    });
  }

  @FXML
  public void nextBackground() {
    bgDesign.next().ifPresent(d -> {
      this.bgDesign = d;
      update(d);
    });
  }

  @FXML
  public void cancel() {
    app.updateDesign(new Design(terrainDesign, snakeDesign, bgDesign));
    app.getWindowManager().request(new MenuWindow(app));
  }

  private void update(DesignOption option) {
    before.setVisible(option.before().isPresent());
    next.setVisible(option.next().isPresent());
    current.setImage(new Image(getClass().getResourceAsStream(option.getFile())));
    currentName.setText(t(option.getName()));
  }
}
