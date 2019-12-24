package com.neolumia.snake.control;


import com.neolumia.snake.model.game.GameHistory;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;
import com.neolumia.snake.view.TableItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class SysData {

  private static SysData single_instance = null;
  private ArrayList<Question> questions;
  private ArrayList<GameHistory> history;

  /**
   * Creates an instance from the sysData
   *
   * @return
   */
  public static SysData getInstance() {
    if (single_instance == null) {
      single_instance = new SysData();
      single_instance.setQuestions();
      single_instance.setHistory();
    }

    return single_instance;
  }

  public ArrayList<GameHistory> getHistory() {
    return history;
  }

  public ArrayList<TableItem> getHistoryTableItems() {
    ArrayList<TableItem> tableItems = new ArrayList<>();
    for (GameHistory gameHistory : history
    ) {
      TableItem tableItem = new TableItem(gameHistory.getPlayer(), Integer.toString(gameHistory.getPoints()), Integer.toString(gameHistory.getLives()));
      tableItems.add(tableItem);
    }
    return tableItems;
  }

  public void setHistory() {
    this.history = readHistoryFromJson();
  }

  /**
   * Sets the questions array to be equals to the array returned after reading from json
   */
  public void setQuestions() {
    this.questions = readQuestionFromJson();
  }


  /**
   * Getter method for the questions array
   *
   * @return questions array
   */
  public ArrayList<Question> getQuestions() {
    return questions;
  }

  public boolean ifExists(String ID) {
    Question temp = null;
    for (Question d : questions) {
      if (d.getQuestion().equals(ID))
        temp = d;
      if (temp != null) return true;
    }
    return false;
  }

  public boolean deleteQuestion(String ID) {
    boolean temp = ifExists(ID);
    Question DeleteQ = null;
    if (!temp) return false;
    else {
      for (Question Q : getQuestions()) {
        if (Q.getQuestion().equals(ID))
          DeleteQ = Q;
      }
      questions.remove(DeleteQ);
      return true;
    }
  }

  /**
   * This method inserts the question that was passed as a parameter and adds it to the questions array
   *
   * @param Q the question that we want to insert
   * @return returns true if question was added successfully and false otherwise
   */
  public boolean insertQuestion(Question Q) {
    boolean temp = ifExists(Q.getQuestion());
    if (temp)
      return false;
    else return (questions.add(Q));
  }

  public boolean updateQuestion(String question, String Updated, ArrayList<String> answer, String correctAns, String level, String team) {

    if (!ifExists(Updated)) return false;

    if (!deleteQuestion(question)) return false;

    questions.add(new Question(Updated, answer, correctAns, QuestionLevel.valueOf(level), team));
    return true;


  }

  /**
   * Reads the questions from the json file and converts the questions to objects from the type question
   *
   * @return array which contains all the questions that were read
   */

  public ArrayList<Question> readQuestionFromJson() {
    JSONParser jsonParser = new JSONParser();
    ArrayList<Question> result = new ArrayList<>();
    File file = new File(".");
    for (String fileNames : file.list()) System.out.println(fileNames);
    try (FileReader reader = new FileReader(new File("json/questions.json"))) {
      //Read JSON file
      Object obj = jsonParser.parse(reader);
      JSONObject obj2 = (JSONObject) obj;
      JSONArray arr = (JSONArray) obj2.get("questions");
      Iterator<Object> iterator = arr.iterator();
      while (iterator.hasNext()) {
        JSONObject object = (JSONObject) iterator.next();
        ArrayList<String> answers = (JSONArray) object.get("answers");
        QuestionLevel level = null;
        switch ((String) object.get("level")) {
          case "1":
            level = QuestionLevel.ONE;
          case "2":
            level = QuestionLevel.TWO;
          case "3":
            level = QuestionLevel.THREE;
        }
        result.add(new Question((String) object.get("question"), answers, (String) object.get("correct_ans"), level, (String) object.get("team")));
      }
      return result;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * This method writes the questions from the questions array to the json file located in "json/questions.json"
   */
  public void writeQuestionTojson() {
    JSONObject jObject = new JSONObject();
    try {
      JSONArray jArray = new JSONArray();
      for (Question Q : questions) {
        JSONObject Question = new JSONObject();
        Question.put("question", Q.getQuestion());
        JSONArray array = new JSONArray();
        array.add(Q.getAnswers());
        Question.put("answers", array);
        Question.put("correct_ans", Q.getCorrectAns());
        Question.put("level", Q.getLevel());
        Question.put("team", Q.getTeam());
        jArray.add(Question);
      }
      jObject.put("questions", jArray);
      Files.write(Paths.get("json/questions.json"), jObject.toJSONString().getBytes());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Searches for question and returns question if exists
   *
   * @param questionBody - is the questions body
   * @return question if exists, otherwise NULL
   */
  public Question getQuestion(String questionBody) {
    for (Question p : questions
    ) {
      if (p != null && p.getQuestion().equals(questionBody))
        return p;
    }
    return null;
  }


  public ArrayList<GameHistory> readHistoryFromJson() {
    JSONParser jsonParser = new JSONParser();
    ArrayList<GameHistory> result = new ArrayList<>();
    try (FileReader reader = new FileReader("json/history.json")) {
      //Read JSON file
      Object obj = jsonParser.parse(reader);
      JSONObject obj2 = (JSONObject) obj;
      JSONArray arr = (JSONArray) obj2.get("history");
      Iterator<Object> iterator = arr.iterator();
      while (iterator.hasNext()) {
        JSONObject object = (JSONObject) iterator.next();
        result.add(new GameHistory((String) object.get("player"), (int) (long) object.get("score"), (int) (long) object.get("numOfSouls")));
      }
      Collections.sort(result);
      return result;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void writeHistoryTojson() {
    JSONObject jObject = new JSONObject();
    try {
      JSONArray jArray = new JSONArray();
      for (GameHistory h : history) {
        JSONObject play = new JSONObject();
        play.put("player", h.getPlayer());
        play.put("score", h.getPoints());
        play.put("numOfSouls", h.getLives());
        jArray.add(play);
      }
      jObject.put("history", jArray);
      Files.write(Paths.get("json/history.json"), jObject.toJSONString().getBytes());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
