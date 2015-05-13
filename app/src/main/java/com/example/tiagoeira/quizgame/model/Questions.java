package com.example.tiagoeira.quizgame.model;

/* Model for Questions */

public class Questions {
    int questionId;
    String questionString;

    public Questions(int questionId, String questionString){
        this.questionId = questionId;
        this.questionString = questionString;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }
}
