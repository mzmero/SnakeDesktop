package com.neolumia.snake.view;

import javafx.beans.property.SimpleStringProperty;

// this class is for Questions window table data


public class DataItem {
  private SimpleStringProperty question;

 public DataItem (String q){
   this.question=new SimpleStringProperty(q);
  }

  public String getQuestion() {
    return question.get();
  }
  public void setQuestion(String q){
   this.question.set(q);
  }

}
