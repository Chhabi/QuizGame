package com.example.tiagoeira.quizgame.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class QuizContract {

    //tables:
    public static final String QUESTIONS_TABLE = "QUIZ_QUESTIONS";
    public static final String ANSWERS_TABLE = "QUIZ_ANSWERS";

    //QUESTIONS columns
    public static final String _ID = BaseColumns._ID; //unique id
    public static final String QUESTION_ID = "questionId"; //id of the questions (1-15)
    public static final String KEY_QUESTION = "question";

    //ANSWERS columns
    public static final String A_ID = BaseColumns._ID;
    public static final String ANSWER_ID = "answerId";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_CORRECT = "correct";
    public static final String KEY_Q_ID = "q_id"; //question id

    public static Uri Q_CONTENT_PROVIDER = Uri.withAppendedPath(QuizProvider.CONTENT_URI, QUESTIONS_TABLE);
    public static Uri A_CONTENT_PROVIDER = Uri.withAppendedPath(QuizProvider.CONTENT_URI, ANSWERS_TABLE);
}
