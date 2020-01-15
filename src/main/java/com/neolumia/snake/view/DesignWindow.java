package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.view.design.Design;
import com.neolumia.snake.view.design.DesignOption;
import com.neolumia.snake.view.option.BgDesign;
import com.neolumia.snake.view.option.SnakeDesign;
import com.neolumia.snake.view.option.TerrainDesign;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

import static com.neolumia.snake.GameApp.t;

/**
 * this class is implemented in order to control design window that customizes snake view and board
 * design and more
 */
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

  /**
   * initializer method for the design window
   *
   * @param app
   */
  DesignWindow(GameApp app) {
    this.app = app;

    terrain.setToggleGroup(menu);
    snake.setToggleGroup(menu);
    background.setToggleGroup(menu);

    menu.selectedToggleProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
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
  /** beforeTerrain - updates the new terrain when clicking on the before arrow */
  @FXML
  public void beforeTerrain() {
    terrainDesign
        .before()
        .ifPresent(
            d -> {
              this.terrainDesign = d;
              update(d);
            });
  }
  /** nextTerrain - updates the new terrain when clicking on the next arrow */
  @FXML
  public void nextTerrain() {
    terrainDesign
        .next()
        .ifPresent(
            d -> {
              this.terrainDesign = d;
              update(d);
            });
  }
  /** beforeSnake - updates the new snake when clicking on the before arrow */
  @FXML
  public void beforeSnake() {
    snakeDesign
        .before()
        .ifPresent(
            d -> {
              this.snakeDesign = d;
              update(d);
            });
  }
  /** nextSnake - updates the new snake when clicking on the next arrow */
  @FXML
  public void nextSnake() {
    snakeDesign
        .next()
        .ifPresent(
            d -> {
              this.snakeDesign = d;
              update(d);
            });
  }
  /** beforeBackground - updates the new background when clicking on the before arrow */
  @FXML
  public void beforeBackground() {
    bgDesign
        .before()
        .ifPresent(
            d -> {
              this.bgDesign = d;
              update(d);
            });
  }

  /** nextBackground - updates the new background when clicking on the next arrow */
  @FXML
  public void nextBackground() {
    bgDesign
        .next()
        .ifPresent(
            d -> {
              this.bgDesign = d;
              update(d);
            });
  }
  /**
   * cancel - handles the back/cancel button which is responsible to return the user to menu window
   */
  @FXML
  public void cancel() {
    app.updateDesign(new Design(terrainDesign, snakeDesign, bgDesign));
    try {
      app.getWindowManager().request(new MenuWindow(app));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * this method updates the new design options that the user chose to his game for the next time
   *
   * @param option - the options of design concerning terrain snake and background the user chose
   *     for his game
   */
  private void update(DesignOption option) {
    before.setVisible(option.before().isPresent());
    next.setVisible(option.next().isPresent());
    current.setImage(new Image(getClass().getResourceAsStream(option.getFile())));
    currentName.setText(t(option.getName()));
  }
}
