package com.neolumia.snake.model.questions;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the software engineering questions which will be shown during the game
 */
public class Question implements Serializable {
  /**
   * question : body of the question
   * answers : string array for the ansers of the question
   * correctAns : the right anser to the question
   * level : the difficulty of the level
   * team : which team has added this question
   */
    private String question;
    private ArrayList<String> answers = null;
    private String correctAns;
    private QuestionLevel level;
    private String team;
    private final static long serialVersionUID = 2895261579048435587L;

    public Question() {
    }

    public Question(String question, ArrayList<String> answers, String correctAns, QuestionLevel level, String team) {
        super();
        this.question = question;
        this.answers = answers;
        this.correctAns = correctAns;
        this.level = level;
        this.team = team;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question withQuestion(String question) {
        this.question = question;
        return this;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public Question withAnswers(ArrayList<String> answers) {
        this.answers = answers;
        return this;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public Question withCorrectAns(String correctAns) {
        this.correctAns = correctAns;
        return this;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Question withTeam(String team) {
        this.team = team;
        return this;
    }

    @Override
    public String toString() {
        return "question " + this.question + " answers:" + this.answers + " CorrectAns: " + this.correctAns + " level:"
                + this.level + " team:" + this.team;
    }




}
