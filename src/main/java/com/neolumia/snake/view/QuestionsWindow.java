package com.neolumia.snake.view;

import com.neolumia.snake.GameApp;
import com.neolumia.snake.control.SysData;
import com.neolumia.snake.view.design.DesignOption;
import com.neolumia.snake.view.option.BgDesign;
import com.neolumia.snake.view.design.Design;
import com.neolumia.snake.view.option.SnakeDesign;
import com.neolumia.snake.view.option.TerrainDesign;
import com.neolumia.snake.model.Question;
import com.neolumia.snake.model.QuestionLevel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import static com.neolumia.snake.GameApp.t;
/**
 * This is the class which is responsible for the design Management view - which manages the design
 */
public final class QuestionsWindow extends Window {
  private final GameApp app;
  private final ToggleGroup menu = new ToggleGroup();
  private ObservableList<String> items = FXCollections.observableArrayList();  // observer for questions combobox
  private ObservableList<DataItem> data = FXCollections.observableArrayList(); // observer for questions in table

  private TerrainDesign terrainDesign;
  private SnakeDesign snakeDesign;
  private BgDesign bgDesign;
  private Node node;

  @FXML
  private GridPane grid;
  @FXML
  TableColumn Q1;
  @FXML
  TableView<DataItem> tb;
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

  QuestionsWindow(GameApp app) {
    this.app = app;
    //TODO:Update view and remove custom settings



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


    initCB();
  }

  private void initCB() {
    ComboDelete.setItems(items);
    ComboChooseQuestion.setItems(items);
    items.setAll(reloadData());     //  preparing questions (items) for delete and update combo box

     Q1.setText("Question");
     Q1.setCellValueFactory(new PropertyValueFactory<DataItem,String>("question"));
     data.setAll(reloadQuestionData());
     tb.setItems(data);           // preparing  questions for table

    String[] teams = {"Chimp", "Crocodile", "Scorpion", "Giraffe", "Spider", "Viper", "Panther", "Wolf", "Sloth", "Lion", "Panda", "Piranha", "Rabbit", "Shark", "Hawk", "Husky"};
    ComboUpdateTeam.getItems().addAll(teams);    // team combobox
    CoInsertteams.getItems().addAll(teams);

    ArrayList<String> levels = new ArrayList<>();
    QuestionLevel[] values = QuestionLevel.values();
    for (QuestionLevel ql : values)
      levels.add(ql.getLevel());
    ComboUpdateLevel.getItems().addAll(levels);   // level combobox
    CoInsertlevel.getItems().addAll(levels);

    ArrayList<Integer> CoreectAns = new ArrayList<>();
    CoreectAns.add(1);
    CoreectAns.add(2);
    CoreectAns.add(3);
    CoreectAns.add(4);

    CoInsertCorrectAnswer.getItems().addAll(CoreectAns);  // answer combobox
    ComboUpdateCorrectAns.getItems().addAll(CoreectAns);

    ComboDelete.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {   //delete combobox listener
        if(newValue!=null)
          QuestionBody.setText(SysData.getInstance().getQuestion(newValue).toString());
        else QuestionBody.setText("");
      }
    );


    ComboChooseQuestion.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {  //  update combobox listener
        if(newValue!=null){
          Question q=SysData.getInstance().getQuestion(newValue);          // fill updates textfile with the new value
          UpdateQuestionBody.setText(q.getQuestion());
          ComboUpdateTeam.setValue(q.getTeam());
          int num=Integer.parseInt(q.getCorrectAns());
          ComboUpdateCorrectAns.setValue(num);
          ComboUpdateLevel.setValue(q.getLevel());
          UpdateAnswer1.setText(q.getAnswers().get(0));
          UpdateAnswer2.setText(q.getAnswers().get(1));
          UpdateAnswer3.setText(q.getAnswers().get(2));
          UpdateAnswer4.setText(q.getAnswers().get(3));
        }
        else{
          UpdateQuestionBody.setText("");                          // empty the update text fields
          ComboUpdateTeam.getSelectionModel().clearSelection();
          ComboUpdateCorrectAns.getSelectionModel().clearSelection();
          ComboUpdateLevel.getSelectionModel().clearSelection();
          UpdateAnswer1.setText("");
          UpdateAnswer2.setText("");
          UpdateAnswer3.setText("");
          UpdateAnswer4.setText("");

        }
      }
    );

  }

  private List<DataItem> reloadQuestionData() {       // reloading question func
    List<DataItem> items= new ArrayList<>();
    for(Question q: SysData.getInstance().getQuestions()){
      DataItem d= new DataItem(q.getQuestion());
      items.add(d);
    }
    return items;
  }

  private List<String> reloadData() {              // reloading question func
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
  public void cancel() throws SQLException {        // close page and return to menu
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
  public void UpdateQ(MouseEvent event) {               // update button event
    if(ComboChooseQuestion.getSelectionModel().isEmpty()){     //input check
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("Something went wrong , please try again");
      String s ="Please choose a question from the combobox";
      alert.setContentText(s);
      alert.show();

    } else
      // another input check
    if(UpdateQuestionBody.getText()!=null && !UpdateQuestionBody.getText().isEmpty() &&!ComboUpdateTeam.getSelectionModel().isEmpty() && !ComboUpdateLevel.getSelectionModel().isEmpty()
      && !ComboUpdateCorrectAns.getSelectionModel().isEmpty() && UpdateAnswer1.getText()!=null && !UpdateAnswer1.getText().isEmpty() && UpdateAnswer2.getText()!=null &&
      !UpdateAnswer2.getText().isEmpty() && !UpdateAnswer3.getText().isEmpty() && UpdateAnswer3.getText()!=null && UpdateAnswer4.getText()!=null
      && !UpdateAnswer4.getText().isEmpty()){
      if(!ComboChooseQuestion.getValue().equals(UpdateQuestionBody.getText()) && SysData.getInstance().ifExists(UpdateQuestionBody.getText())){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed");
        alert.setHeaderText("Something went wrong");
        String s ="Your updating the QuestionBody to an existing one ,Please write a new question";
        alert.setContentText(s);
        alert.show();

      }else{    // input is legal
        ArrayList<String> answers=new ArrayList<>();
        answers.add(UpdateAnswer1.getText());
        answers.add(UpdateAnswer2.getText());
        answers.add(UpdateAnswer3.getText());
        answers.add(UpdateAnswer4.getText());
        SysData.getInstance().updateQuestion(ComboChooseQuestion.getValue(),UpdateQuestionBody.getText(),answers,ComboUpdateCorrectAns.getValue().toString(),
          ComboUpdateLevel.getValue(),ComboUpdateTeam.getValue());
         // popup for a success
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Question successfully");
        alert.setHeaderText("The Question updated successfully");
        String s ="The informations which was inserted:\nQuestion Body:"+UpdateQuestionBody.getText()+"\nAnswer1: "+answers.get(0)+ "\nAnswer2: "+answers.get(1)+"\nAnswer3: "+answers.get(2)+"\nAnswer4: "+answers.get(3)
          +"\nCorrect Answer Number: "+ComboUpdateCorrectAns.getValue().toString() +"\nlevel: "+ ComboUpdateLevel.getValue()+"    Team: "+ComboUpdateTeam.getValue();
        alert.setContentText(s);
        alert.show();


        ComboChooseQuestion.getSelectionModel().clearSelection();  //empty update combobox and reload data
        items.setAll(reloadData());
        data.setAll(reloadQuestionData());





      }

    }else {   //error popup
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("Something went wrong");
      String s ="There could be some missing field , Please try again";
      alert.setContentText(s);
      alert.show();
    }
  }


  @FXML
  public void DeleteQ(MouseEvent event) {    // delete button event
    String DeleteQ=ComboDelete.getValue();
    if(DeleteQ!=null) {
      if (SysData.getInstance().deleteQuestion(DeleteQ)){        //delete question
        Alert alert = new Alert(Alert.AlertType.INFORMATION);     //success
        alert.setTitle("Delete Question");
        alert.setHeaderText("The Question Deleted successfully");
        //  String s =" ";
        // alert.setContentText(s);
        alert.show();
        ComboDelete.getSelectionModel().clearSelection();     // clear delete combobox and reload data
        //  if(ComboChooseQuestion.getValue().equals(DeleteQ)) ComboChooseQuestion.getSelectionModel().clearSelection();
        items.setAll(reloadData());
        data.setAll(reloadQuestionData());

      }
      else {
        Alert alert = new Alert(Alert.AlertType.ERROR);   //failed to delete question
        alert.setTitle("Failed");
        alert.setHeaderText("Something went wrong , please try again");
        //  String s =" ";
        // alert.setContentText(s);
        alert.show();
      }}
    else {
      Alert alert = new Alert(Alert.AlertType.ERROR);   //failed input
      alert.setTitle("Failed");
      alert.setHeaderText("you need to choose a question First");
      //String s =" ";
      // alert.setContentText(s);
      alert.show();
    }}


  @FXML
  public void InsertQ(MouseEvent event) {         // insert question event
    String InsertThisQuestion = InsertQuestionBody.getText();
    // input check
    if ( InsertThisQuestion!=null && !InsertThisQuestion.equals("") && !CoInsertteams.getSelectionModel().isEmpty()
      && !CoInsertlevel.getSelectionModel().isEmpty() &&!CoInsertCorrectAnswer.getSelectionModel().isEmpty()) {
      String T = CoInsertteams.getValue();
      String L = CoInsertlevel.getValue();
      String An1 = InsertAnswer1.getText();
      String An2 = InsertAnswer2.getText();
      String An3 = InsertAnswer3.getText();
      String An4 = InsertAnswer4.getText();
      String CorrectAnsNum = CoInsertCorrectAnswer.getValue().toString();
      // input check
      if ( An1 != null && !An1.isEmpty() && An2 != null && !An2.isEmpty() &&  An3 != null && !An3.isEmpty()  && An4 != null && !An4.isEmpty() ) {
        ArrayList<String> newAnsInsert = new ArrayList<>();
        newAnsInsert.add(An1);
        newAnsInsert.add(An2);
        newAnsInsert.add(An3);
        newAnsInsert.add(An4);

        Question InserQuestion = new Question(InsertThisQuestion, newAnsInsert, CorrectAnsNum,L, T);
        if(SysData.getInstance().insertQuestion(InserQuestion)){  // insert question
          Alert alert = new Alert(Alert.AlertType.INFORMATION);  // insert success
          alert.setTitle("Inserted Question");
          alert.setHeaderText("The Question Inserted successfully");
          String s ="The informations which was inserted:\nQuestion Body:"+InsertThisQuestion+"\nAnswer1: "+An1+ "\nAnswer2: "+An2+"\nAnswer3: "+An3+"\nAnswer4: "+An4
            +"\nCorrect Answer Number: "+CorrectAnsNum +"\nlevel: "+ L+"    Team: "+T;
          alert.setContentText(s);
          alert.show();

          InsertQuestionBody.setText("");                     //empty insert fields and reload data
          CoInsertlevel.getSelectionModel().clearSelection();
          CoInsertteams.getSelectionModel().clearSelection();
          CoInsertCorrectAnswer.getSelectionModel().clearSelection();
          InsertAnswer1.setText("");
          InsertAnswer2.setText("");
          InsertAnswer3.setText("");
          InsertAnswer4.setText("");

          items.setAll(reloadData());
          data.setAll(reloadQuestionData());

        }else {  // question  inserted failed
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Failed");
          alert.setHeaderText("The Question Already existed ,Please try another one");
          //String s ="There could be some missing fields,Please try again";
          //alert.setContentText(s);
          alert.show();
        }

      }else { //input failed
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed");
        alert.setHeaderText("Something went wrong ");
        String s ="There could be some missing fields ,Please try again";
        alert.setContentText(s);
        alert.show();
      }
    } else {   //input failed
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("Something went wrong");
      String s ="There could be some missing fields ,Please try again";
      alert.setContentText(s);
      alert.show();
    }
  }
  @FXML
  public void handleTable(MouseEvent event){    // table row listener
    DataItem d=tb.getSelectionModel().getSelectedItem();     // change combobox for a given selected row table
    ComboChooseQuestion.getSelectionModel().select(d.getQuestion());
    ComboDelete.getSelectionModel().select(d.getQuestion());
  }



}
