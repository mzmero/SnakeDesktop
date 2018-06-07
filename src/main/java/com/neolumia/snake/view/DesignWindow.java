package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.design.BgDesign;
import com.neolumia.snake.design.Design;
import com.neolumia.snake.design.TerrainDesign;
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
  private BgDesign bgDesign;
  private Node node;

  @FXML private GridPane grid;
  @FXML private ToggleButton terrain;
  @FXML private ToggleButton background;

  @FXML private ImageView before;
  @FXML private ImageView next;
  @FXML private ImageView current;
  @FXML private Label currentName;

  DesignWindow(GameApp app) {
    this.app = app;

    terrain.setToggleGroup(menu);
    background.setToggleGroup(menu);

    menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.equals(terrain)) {
        grid.getChildren().removeAll(node);
        grid.add(node = render("terrain"), 0, 0);
        updateTerrain(terrainDesign);
        return;
      }
      if (newValue.equals(background)) {
        grid.getChildren().removeAll(node);
        grid.add(node = render("background"), 0, 0);
        updateBackground(bgDesign);
      }
    });

    this.terrainDesign = app.getDesign().terrain;
    this.bgDesign = app.getDesign().background;

    menu.selectToggle(terrain);
  }

  @FXML
  public void beforeTerrain() {
    terrainDesign.before().ifPresent(d -> {
      this.terrainDesign = d;
      updateTerrain(d);
    });
  }

  @FXML
  public void nextTerrain() {
    terrainDesign.next().ifPresent(d -> {
      this.terrainDesign = d;
      updateTerrain(d);
    });
  }

  @FXML
  public void beforeBackground() {
    bgDesign.before().ifPresent(d -> {
      this.bgDesign = d;
      updateBackground(d);
    });
  }

  @FXML
  public void nextBackground() {
    bgDesign.next().ifPresent(d -> {
      this.bgDesign = d;
      updateBackground(d);
    });
  }

  @FXML
  public void cancel() {
    app.updateDesign(new Design(terrainDesign, bgDesign));
    app.getWindowManager().request(new MenuWindow(app));
  }

  private void updateTerrain(TerrainDesign design) {
    before.setVisible(design.before().isPresent());
    next.setVisible(design.next().isPresent());
    current.setImage(new Image(getClass().getResourceAsStream(design.getFile())));
    currentName.setText(t(design.getName()));
  }

  private void updateBackground(BgDesign design) {
    before.setVisible(design.before().isPresent());
    next.setVisible(design.next().isPresent());
    current.setImage(new Image(getClass().getResourceAsStream(design.getFile())));
    currentName.setText(t(design.getName()));
  }
}
