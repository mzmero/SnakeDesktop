package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class is responsible for windows management and link with the FXML file */
public abstract class Window {

  private final FXMLLoader loader;

  /**
   * Window assigns a window class to the fxml matching name and loads it to the screen
   */
  Window() {
    loader = new FXMLLoader(getClass().getResource('/' + getResourceName()), GameApp.getBundle());
    loader.setController(this);
    try {
      loader.load();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public final FXMLLoader getLoader() {
    return loader;
  }

  /**
   * render - converts FXML design to screen view
   * @param name
   * @return
   */
  public Node render(String name) {
    try {
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/_" + name + ".fxml"), GameApp.getBundle());
      loader.setController(this);
      return loader.load();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private String getResourceName() {
    return getClass().getSimpleName() + ".fxml";
  }

  public void load(Stage stage, Scene scene) {}

  public void unload() {}
}
