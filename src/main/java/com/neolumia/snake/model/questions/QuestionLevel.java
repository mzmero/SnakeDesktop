package com.neolumia.snake.model.questions;

public enum QuestionLevel {
  /**
   * This ENUM class represents the level of the game
   */
   ONE,  TWO , THREE;

  public static QuestionLevel Value(String s){
     switch (s){
       case "1":
         return QuestionLevel.ONE;
       case "2":
         return  QuestionLevel.TWO;
       case "3":
         return QuestionLevel.THREE;

     }
     return null;
  }

  public static String converTostring(QuestionLevel q){
    if(q==ONE) return "1";
    else if(q==TWO) return"2";
    else return "3";


  }


}
