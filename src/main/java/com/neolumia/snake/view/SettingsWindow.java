package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.model.Difficulty;
import com.neolumia.snake.model.Locale;
import com.neolumia.snake.model.Settings;
import com.neolumia.snake.model.Size;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * This is the class which is responsible for the Question Management view - which manages the SE
 * qustion insert, update and delete
 */
public final class SettingsWindow extends Window {
  private static final Logger LOGGER = LogManager.getLogger(SettingsWindow.class);

  private final GameApp app;

  private final ToggleGroup locale = new ToggleGroup();
  private final ToggleGroup difficulty = new ToggleGroup();
  private final ToggleGroup terrain = new ToggleGroup();

  @FXML private ToggleButton localeGerman;
  @FXML private ToggleButton localeEnglish;
  @FXML private ToggleButton difficultyEasy;
  @FXML private ToggleButton difficultyMedium;
  @FXML private ToggleButton difficultyHard;
  @FXML private ToggleButton terrainSmall;
  @FXML private ToggleButton terrainMedium;
  @FXML private ToggleButton terrainBig;
  @FXML private TextField playerName;
  @FXML private CheckBox leaderboard;

  SettingsWindow(GameApp app) {
    this.app = app;

    localeGerman.setToggleGroup(locale);
    localeEnglish.setToggleGroup(locale);

    difficultyEasy.setToggleGroup(difficulty);
    difficultyMedium.setToggleGroup(difficulty);
    difficultyHard.setToggleGroup(difficulty);

    terrainSmall.setToggleGroup(terrain);
    terrainMedium.setToggleGroup(terrain);
    terrainBig.setToggleGroup(terrain);

    locale.selectToggle(app.getSettings().locale == Locale.GERMAN ? localeGerman : localeEnglish);

    switch (app.getSettings().difficulty) {
      case EASY:
        difficulty.selectToggle(difficultyEasy);
        break;
      case MEDIUM:
        difficulty.selectToggle(difficultyMedium);
        break;
      case HARD:
        difficulty.selectToggle(difficultyHard);
        break;
    }

    switch (app.getSettings().size) {
      case SMALL:
        terrain.selectToggle(terrainSmall);
        break;
      case MEDIUM:
        terrain.selectToggle(terrainMedium);
        break;
      case BIG:
        terrain.selectToggle(terrainBig);
        break;
    }

    playerName.setText(app.getSettings().playerName);
    leaderboard.setSelected(app.getSettings().leaderboard);
  }

  @FXML
  public void cancel() {
    try {
      app.getWindowManager().request(new MenuWindow(app));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void save() throws SQLException {
    app.getSettings().locale =
        locale.getSelectedToggle().equals(localeGerman) ? Locale.GERMAN : Locale.ENGLISH;

    if (difficulty.getSelectedToggle() == difficultyEasy) {
      app.getSettings().difficulty = Difficulty.EASY;
    } else if (difficulty.getSelectedToggle() == difficultyMedium) {
      app.getSettings().difficulty = Difficulty.MEDIUM;
    } else if (difficulty.getSelectedToggle() == difficultyHard) {
      app.getSettings().difficulty = Difficulty.HARD;
    } else {
      app.getSettings().difficulty = Settings.DEFAULT_DIFFICULTY;
    }

    if (terrain.getSelectedToggle() == terrainSmall) {
      app.getSettings().size = Size.SMALL;
    } else if (terrain.getSelectedToggle() == terrainMedium) {
      app.getSettings().size = Size.MEDIUM;
    } else if (terrain.getSelectedToggle() == terrainBig) {
      app.getSettings().size = Size.BIG;
    } else {
      app.getSettings().size = Settings.DEFAULT_SIZE;
    }

    //app.getSettings().playerName = playerName.getText();
    app.getSettings().leaderboard = leaderboard.isSelected();

    app.getDatabase().saveSettings(app.getSettings());
    LOGGER.info(app.getDatabase().GetAllsetttings().toString());
    //System.out.print(app.getDatabase().getettings().toString());

    cancel();
  }
}
