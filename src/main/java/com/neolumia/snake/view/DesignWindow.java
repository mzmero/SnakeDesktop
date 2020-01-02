
package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.SysData;
import com.neolumia.snake.view.design.DesignOption;
import com.neolumia.snake.view.option.BgDesign;
import com.neolumia.snake.view.design.Design;
import com.neolumia.snake.view.option.SnakeDesign;
import com.neolumia.snake.view.option.TerrainDesign;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import static com.neolumia.snake.GameApp.t;
/**
 * This is the class which is responsible for the design Management view - which manages the design
 */
public final class DesignWindow extends Window {

  private final GameApp app;
  private final ToggleGroup menu = new ToggleGroup();
  private ObservableList<String> items = FXCollections.observableArrayList();

  private TerrainDesign terrainDesign;
  private SnakeDesign snakeDesign;
  private BgDesign bgDesign;
  private Node node;

  @FXML
  private GridPane grid;
  @FXML
  private ToggleButton terrain;
  @FXML
  private ToggleButton snake;
  @FXML
  private ToggleButton background;

  @FXML
  private ImageView before;
  @FXML
  private ImageView next;
  @FXML
  private ImageView current;
  @FXML
  private Label currentName;

  @FXML
  Button btnDelete;
  @FXML
  ComboBox<String> ComboDelete;
  @FXML
  TextArea QuestionBody;
  @FXML
  Button btnUpdate;
  @FXML
  ComboBox<String> ComboChooseQuestion;
  @FXML
  ComboBox<String> ComboUpdateTeam;
  @FXML
  ComboBox<String> ComboUpdateLevel;
  @FXML
  TextField UpdateQuestionBody;
  @FXML
  TextField UpdateAnswer1;
  @FXML
  TextField UpdateAnswer2;
  @FXML
  TextField UpdateAnswer3;
  @FXML
  TextField UpdateAnswer4;
  @FXML
  ComboBox<Integer> ComboUpdateCorrectAns;
  @FXML
  Button btnInsert;
  @FXML
  TextField InsertQuestionBody;
  @FXML
  ComboBox<String> CoInsertteams;
  @FXML
  ComboBox<String> CoInsertlevel;
  @FXML
  TextField InsertAnswer1;
  @FXML
  TextField InsertAnswer2;
  @FXML
  TextField InsertAnswer3;
  @FXML
  TextField InsertAnswer4;
  @FXML
  ComboBox<Integer> CoInsertCorrectAnswer;

  DesignWindow(GameApp app) {
    this.app = app;
    //TODO:Update view and remove custom settings

    terrain.setToggleGroup(menu);
    snake.setToggleGroup(menu);
    background.setToggleGroup(menu);

//    menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//      if (newValue.equals(terrain)) {
//        grid.getChildren().removeAll(node);
//        grid.add(node = render("terrain"), 0, 0);
//        update(terrainDesign);
//        return;
//      }
//      if (newValue.equals(snake)) {
//        grid.getChildren().removeAll(node);
//        grid.add(node = render("snake"), 0, 0);
//        update(snakeDesign);
//      }
//      if (newValue.equals(background)) {
//        grid.getChildren().removeAll(node);
//        grid.add(node = render("background"), 0, 0);
//        update(bgDesign);
//      }
//    });

    this.terrainDesign = app.getDesign().terrain;
    this.snakeDesign = app.getDesign().snake;
    this.bgDesign = app.getDesign().background;

    menu.selectToggle(terrain);
    initCB();
  }

  private void initCB() {
     ComboDelete.setItems(items);
     ComboChooseQuestion.setItems(items);
      items.setAll(reloadData());
      System.out.println(items);

    String[] teams = {"Chimp", "Crocodile", "Scorpion", "Giraffe", "Spider", "Viper", "Panther", "Wolf", "Sloth", "Lion", "Panda", "Piranha", "Rabbit", "Shark", "Hawk", "Husky"};
    ComboUpdateTeam.getItems().addAll(teams);
    CoInsertteams.getItems().addAll(teams);

    ArrayList<String> levels = new ArrayList<>();
    QuestionLevel[] values = QuestionLevel.values();
    for (QuestionLevel ql : values)
      levels.add(ql.getLevel());
    ComboUpdateLevel.getItems().addAll(levels);
    CoInsertlevel.getItems().addAll(levels);

    ArrayList<Integer> CoreectAns = new ArrayList<>();
    CoreectAns.add(1);
    CoreectAns.add(2);
    CoreectAns.add(3);
    CoreectAns.add(4);

    CoInsertCorrectAnswer.getItems().addAll(CoreectAns);
    ComboUpdateCorrectAns.getItems().addAll(CoreectAns);

    ComboDelete.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
      if(newValue!=null)
        QuestionBody.setText(SysData.getInstance().getQuestion(newValue).toString());
      else QuestionBody.setText("");
      }
    );
  }

  private List<String> reloadData() {
    List<String> items = new ArrayList<>();

    for (Question q: SysData.getInstance().getQuestions()) {
      items.add(q.getQuestion());
    }
    return items;

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

  @FXML
  public void UpdateQ(MouseEvent event) {
    if(ComboChooseQuestion.getSelectionModel().isEmpty()){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("Something went wrong , please try again");
      String s ="Please choose a question from the combobox";
      alert.setContentText(s);
      alert.show();

    } else
    if(UpdateQuestionBody.getText()!=null && !ComboUpdateTeam.getSelectionModel().isEmpty() && !ComboUpdateLevel.getSelectionModel().isEmpty()
      && !ComboUpdateCorrectAns.getSelectionModel().isEmpty() && UpdateAnswer1.getText()!=null && UpdateAnswer2.getText()!=null &&
      UpdateAnswer3.getText()!=null && UpdateAnswer4.getText()!=null){





    }else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("Something went wrong");
      String s ="There could be some missing field , Please try again";
      alert.setContentText(s);
      alert.show();
    }
  }

    /*
    if (event.getSource().equals(btnUpdate)) {
      String UpDateThisValue = ComboChooseQuestion.getValue();
      if (UpDateThisValue != null) {
        if (SysData.getInstance().ifExists(UpDateThisValue)) {
          Question UpdateThisQ = null;
          for (Question Q : SysData.getInstance().getQuestions()) {
            if (Q.getQuestion().equals(UpDateThisValue)) {
              UpdateThisQ = Q;
            }
          }
          if (UpdateThisQ != null) {
            ArrayList<String> UpdatedAnswers = new ArrayList<>();
            int CorrectAns = ComboUpdateCorrectAns.getValue();
            String GetTeamValue = ComboUpdateTeam.getValue();
            String GetLevelValue = ComboUpdateLevel.getValue();
            String UpdateBody = UpdateQuestionBody.getText();
            String UpdateA1 = UpdateAnswer1.getText();
            String UpdateA2 = UpdateAnswer2.getText();
            String UpdateA3 = UpdateAnswer3.getText();
            String UpdateA4 = UpdateAnswer4.getText();
            if (UpdateBody != "" && UpdateA1 != "" && UpdateA2 != "" && UpdateA3 != "" && UpdateA4 != "" && GetTeamValue != null && GetLevelValue != "" && CorrectAns != 0)
              UpdatedAnswers.add(UpdateA1);
            UpdatedAnswers.add(UpdateA2);
            UpdatedAnswers.add(UpdateA3);
            UpdatedAnswers.add(UpdateA4);
            String CorrectAnswer = UpdatedAnswers.get(CorrectAns);
            if (SysData.getInstance().updateQuestion(UpDateThisValue, UpdateBody, UpdatedAnswers, CorrectAnswer, GetLevelValue, GetTeamValue))
              ;
          }
        }
      }

    }
  }*/

  @FXML
  public void DeleteQ(MouseEvent event) {
        String DeleteQ=ComboDelete.getValue();
        if(DeleteQ!=null) {
          if (SysData.getInstance().deleteQuestion(DeleteQ)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Question");
            alert.setHeaderText("The Question Deleted successfully");
          //  String s =" ";
           // alert.setContentText(s);
            alert.show();
            ComboDelete.getSelectionModel().clearSelection();
            items.setAll(reloadData());

              }
          else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Something went wrong , please try again");
            //  String s =" ";
            // alert.setContentText(s);
            alert.show();
          }}
         else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Failed");
          alert.setHeaderText("you need to choose a question First");
           //String s =" ";
          // alert.setContentText(s);
          alert.show();
        }}


  @FXML
  public void InsertQ(MouseEvent event) {
    String InsertThisQuestion = InsertQuestionBody.getText();
    if (!InsertThisQuestion.equals("") && InsertThisQuestion!=null && !CoInsertteams.getSelectionModel().isEmpty()
      && !CoInsertlevel.getSelectionModel().isEmpty() &&!CoInsertCorrectAnswer.getSelectionModel().isEmpty()) {
      String T = CoInsertteams.getValue();
      String L = CoInsertlevel.getValue();
      String An1 = InsertAnswer1.getText();
      String An2 = InsertAnswer2.getText();
      String An3 = InsertAnswer3.getText();
      String An4 = InsertAnswer4.getText();
      String CorrectAnsNum = CoInsertCorrectAnswer.getValue().toString();
      if (T != null && L != null && An1 != null && An2 != null && An3 != null && An4 != null && CorrectAnsNum != "") {
        ArrayList<String> newAnsInsert = new ArrayList<>();  //answers array
        newAnsInsert.add(An1);
        newAnsInsert.add(An2);
        newAnsInsert.add(An3);
        newAnsInsert.add(An4);

        Question InserQuestion = new Question(InsertThisQuestion, newAnsInsert, CorrectAnsNum,L, T);
        if(SysData.getInstance().insertQuestion(InserQuestion)){
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Inserted Question");
          alert.setHeaderText("The Question Inserted successfully");
           String s ="The informations which was inserted:\nQuestion Body:"+InsertThisQuestion+"\nAnswer1: "+An1+ "\nAnswer2: "+An2+"Answer3: "+An3+"\nAnswer4: "+An4
           +"\nCorrect Answer Number: "+CorrectAnsNum +"\nlevel: "+ L+"    Team: "+T;
          alert.setContentText(s);
          alert.show();

          InsertQuestionBody.setText("");
          CoInsertlevel.getSelectionModel().clearSelection();
          CoInsertteams.getSelectionModel().clearSelection();
          CoInsertCorrectAnswer.getSelectionModel().clearSelection();
           InsertAnswer1.setText("");
           InsertAnswer2.setText("");
           InsertAnswer3.setText("");
           InsertAnswer4.setText("");

          items.setAll(reloadData());

        }else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Failed");
          alert.setHeaderText("The Question Already existed ,Please try another one");
          //String s ="There could be some missing fields,Please try again";
          //alert.setContentText(s);
          alert.show();
        }

      }else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed");
        alert.setHeaderText("Something went wrong ");
        String s ="There could be some missing fields ,Please try again";
        alert.setContentText(s);
        alert.show();
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("Something went wrong");
       String s ="There could be some missing fields ,Please try again";
       alert.setContentText(s);
       alert.show();
    }
  }
}
