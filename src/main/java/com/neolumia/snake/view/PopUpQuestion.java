package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.Game;
import com.neolumia.snake.control.SingleGame;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;


public class PopUpQuestion extends Window {

  @FXML
  Text questionBody;
  @FXML
  CheckBox checkBox1;
  @FXML
  CheckBox checkBox2;
  @FXML
  CheckBox checkBox3;
  @FXML
  CheckBox checkBox4;
  @FXML
  Text checkBox1_text;
  @FXML
  Text checkBox2_text;
  @FXML
  Text checkBox3_text;
  @FXML
  Text checkBox4_text;
  @FXML
  Button submitButton;
  Question question;
  CheckBox[] checkBoxes = new CheckBox[4];
  private int selectedCheckBox;
  private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
  private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
  private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
  private final int maxNumSelected = 1;

  public PopUpQuestion(SingleGame game, Question q) {
    System.out.println(q);
    question = q;
    checkBoxes[0] = checkBox1;
    checkBoxes[1] = checkBox2;
    checkBoxes[2] = checkBox3;
    checkBoxes[3] = checkBox4;
    submitButton.setDisable(true);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        questionBody.setText(q.getQuestion());
        checkBox1_text.setText(q.getAnswers().get(0));
        checkBox2_text.setText(q.getAnswers().get(1));
        checkBox3_text.setText(q.getAnswers().get(2));
        checkBox4_text.setText(q.getAnswers().get(3));
        configureCheckBox(checkBox1);
        configureCheckBox(checkBox2);
        configureCheckBox(checkBox3);
        configureCheckBox(checkBox4);
        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
          if (newSelectedCount.intValue() >= maxNumSelected) {
            unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
            submitButton.setDisable(false);
          } else {
            unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
            submitButton.setDisable(true);
          }
        });

        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (selectedCheckBoxes.size() != 0) {
              if (Integer.parseInt(question.getCorrectAns()) == (selectedCheckBox)) {
                toast("Correct Answer :)");
                submitButton.setDisable(true);
                updatePoints(game, true);
                submitButton.getScene().getWindow().hide();
                GameWindow.setQuestions(game);
              } else {
                toast("Wrong Answer :(");
                submitButton.setDisable(true);
                updatePoints(game, false);
                submitButton.getScene().getWindow().hide();
                GameWindow.setQuestions(game);
              }
            } else toast("Please Choose Answer");
          }
        });
      }
    });

  }

  private void toast(String s) {
    Toast.makeText(GameApp.windowManager.getStage(), s, 1500, 500,
      500);
  }

  private void updatePoints(SingleGame game, Boolean isCorrect) {
    if (isCorrect) {
      if (question.getLevel().equals(QuestionLevel.ONE.getLevel()))
        game.setPoints(game.getPoints() + 1);
      if (question.getLevel().equals(QuestionLevel.TWO.getLevel()))
        game.setPoints(game.getPoints() + 2);
      if (question.getLevel().equals(QuestionLevel.THREE.getLevel()))
        game.setPoints(game.getPoints() + 3);
    } else {
      if (question.getLevel().equals(QuestionLevel.ONE.getLevel())) {
        int points = game.getPoints() - 10;
        if (points >= 0) game.setPoints(points);
        else game.setPoints(0);
      }
      if (question.getLevel().equals(QuestionLevel.TWO.getLevel())) {
        int points = game.getPoints() - 20;
        if (points >= 0) game.setPoints(points);
        else game.setPoints(0);
      }
      if (question.getLevel().equals(QuestionLevel.THREE.getLevel())) {
        int points = game.getPoints() - 30;
        if (points >= 0) game.setPoints(points);
        else game.setPoints(0);
      }
    }
  }

  private void configureCheckBox(CheckBox checkBox) {
    if (checkBox.isSelected()) selectedCheckBoxes.add(checkBox);
    else unselectedCheckBoxes.add(checkBox);
    checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
    if (isNowSelected) {
      unselectedCheckBoxes.remove(checkBox);
      selectedCheckBoxes.add(checkBox);
      updateSelected(checkBox);
    } else {
      selectedCheckBoxes.remove(checkBox);
      unselectedCheckBoxes.add(checkBox);
    }
  });
}

  private void updateSelected(CheckBox checkBox) {
    for (int i = 0; i < checkBoxes.length && checkBoxes[i].getId().equals(checkBox.getId()); i++) this.selectedCheckBox = i + 1;
  }


}
