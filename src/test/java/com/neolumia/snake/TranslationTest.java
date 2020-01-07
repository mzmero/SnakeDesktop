/*
 * This file is part of Snake, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 Neolumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.neolumia.snake;

import com.neolumia.snake.control.SysData;
import com.neolumia.snake.model.questions.History;
import com.neolumia.snake.model.questions.Question;
import com.neolumia.snake.model.questions.QuestionLevel;
import com.neolumia.snake.view.GameWindow;
import com.neolumia.snake.view.StatisticsWindow;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public final class TranslationTest {
  @Test
  public void testInsertQuestion(){
    ArrayList<String> answers=new ArrayList<>(); // preparing the question object
    answers.add("1");
    answers.add("2");
    answers.add("3");
    answers.add("4");
    Question q= new Question("q1",answers,"3","2","animal"); // adding new question q1 thats existed
    assertFalse(SysData.getInstance().insertQuestion(q));
    q=new Question("q4",answers,"3","2","animal");  // adding a new answer that doest exist
    assertTrue(SysData.getInstance().insertQuestion(q));

  }

  @Test
  public void testDeleteQuestion(){
    assertTrue(SysData.getInstance().deleteQuestion("q1"));      // preparing to delete a question that exist
    assertFalse(SysData.getInstance().deleteQuestion("q5"));    // in the other hand , deleting a non existing question
  }

  @Test
  public void testUpdateQuestion(){
    ArrayList<String> answers=new ArrayList<>();      //preparing question for update
    answers.add("1");
    answers.add("2");
    answers.add("3");
    answers.add("4");
    assertTrue(SysData.getInstance().updateQuestion("q1","q1",answers,"2","1","animal")); // update questionn q1 thats exist

    Question temp=null;
    for(Question i: SysData.getInstance().getQuestions()){  // checking if the question updatedsuccessfully
      if(i.getQuestion().equals("q1") && i.getCorrectAns().equals("2")&& i.getLevel()=="1" && i.getTeam().equals("animal")){
        ArrayList<String> l= (ArrayList<String>)i.getAnswers();
        Collections.sort(l);
        Collections.sort(answers);
        if(l.get(0).equals(answers.get(0)) && l.get(1).equals(answers.get(1)) && l.get(2).equals(answers.get(2))
            && l.get(3).equals(answers.get(3))   )
            temp=i;
      }
    }
    assertNotNull(temp); // the temp should have the object of q1
    assertFalse(SysData.getInstance().updateQuestion("q7","q1",answers,"2","1","animal")); // update Question q7 that doesnt exist
  }

  @Test
  public void testReadhistoryfromjson(){
    com.neolumia.snake.model.questions.History g= new History("3",20,1); // preparing the History object
    SysData.getInstance().getHistory().add(g);   // adding the object to data structure
    SysData.getInstance().writeHistoryTojson();  // write the new object to json

   boolean flag=false;
   for(History h:SysData.getInstance().readHistoryFromJson()){ // searching for the object after reading the jsonfile
       if(h.equals(g)) flag=true;
   }
   assertTrue(flag);
   SysData.getInstance().getHistory().remove(g); // removing the object so we dont disturb the real data
   SysData.getInstance().writeHistoryTojson();
  }


  @Test
  public void testReadQuestionFromJson(){

    ArrayList<String> answers=new ArrayList<>(); // preparing the question object
    answers.add("1");
    answers.add("2");
    answers.add("3");
    answers.add("4");
    Question q=new Question("q8",answers,"3","2","animal");
    SysData.getInstance().getQuestions().add(q);
    SysData.getInstance().writeQuestionTojson();

    Question temp=null;


    for(Question i: SysData.getInstance().getQuestions()){  // checking if the question added successfully
      if(i.getQuestion().equals(q.getQuestion()) && i.getCorrectAns().equals(q.getCorrectAns())&& i.getLevel()==q.getLevel() && i.getTeam().equals(q.getTeam())){
        ArrayList<String> l= (ArrayList<String>)i.getAnswers();
        Collections.sort(l);
        Collections.sort(answers);
        if(l.get(0).equals(answers.get(0)) && l.get(1).equals(answers.get(1)) && l.get(2).equals(answers.get(2))
          && l.get(3).equals(answers.get(3))   )
          temp=i;
      }
    }


    assertNotNull(temp);
    SysData.getInstance().getQuestions().remove(temp);
    SysData.getInstance().writeQuestionTojson();

    }



  }
















