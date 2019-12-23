package com.neolumia.snake.control;


import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;
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
import java.util.Iterator;

public class SysData {

  private static SysData single_instance = null;
  private ArrayList<Question> questions;

  public static SysData getInstance() {
    if (single_instance == null) {
      single_instance = new SysData();
      single_instance.setQuestions();
    }

    return single_instance;
  }

  public void setQuestions() {
    this.questions = readQuestionFromJson();
  }

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

  public ArrayList<Question> readQuestionFromJson() {
    JSONParser jsonParser = new JSONParser();
    ArrayList<Question> result = new ArrayList<>();
    File file = new File(".");
    for(String fileNames : file.list()) System.out.println(fileNames);
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
  public void writeQuestionTojson() {
    JSONObject jObject = new JSONObject();
    try {
      JSONArray jArray = new JSONArray();
      System.out.print("hala");
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

  public Question getQuestion(String id) {
    for (Question p : questions
    ) {
      if (p != null && p.getQuestion().equals(id))
        return p;
    }
    return null;
  }
}
