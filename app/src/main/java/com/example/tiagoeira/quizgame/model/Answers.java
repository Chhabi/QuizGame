package com.example.tiagoeira.quizgame.model;

/* Model for Answers */

public class Answers {
    String answerId;
    String answer;
    boolean answerCorrect;
    int question_id;

    public Answers (String answerId, String answer, boolean answerCorrect,int question_id){
        this.answerId = answerId;
        this.answer = answer;
        this.answerCorrect = answerCorrect;
        this.question_id = question_id;
    }

    public Answers(String answerId, String answer, int question_id) {
        this.answerId = answerId;
        this.answer = answer;
        this.question_id = question_id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getAnswer(){
        return answer;
    }

    public boolean getAnswerCorrect() {
        return answerCorrect;
    }

    public int getQuestion_id() {
        return question_id;
    }
}
