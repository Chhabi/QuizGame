package com.example.tiagoeira.quizgame.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.tiagoeira.quizgame.model.Answers;
import com.example.tiagoeira.quizgame.model.Questions;

import java.util.ArrayList;
import java.util.List;


public class QuizManager {

    private Context _context;

    public QuizManager(Context context) {
        _context = context;
    }

    public void saveQuestions(Questions questions){
        ContentValues questionValues = new ContentValues();
        questionValues.put(QuizContract.QUESTION_ID, questions.getQuestionId());
        questionValues.put(QuizContract.KEY_QUESTION, questions.getQuestionString());

        Log.i("MNGR_Q_LOG", String.valueOf(QuizProvider.QUESTION_URI) + " " + questionValues);
        _context.getContentResolver().insert(QuizProvider.QUESTION_URI, questionValues);
    }



    public void saveAnswers(Answers answers){
        ContentValues answerValues = new ContentValues();
        answerValues.put(QuizContract.ANSWER_ID, answers.getAnswerId());
        answerValues.put(QuizContract.KEY_ANSWER, answers.getAnswer());
        answerValues.put(QuizContract.KEY_CORRECT, answers.getAnswerCorrect());
        answerValues.put(QuizContract.KEY_Q_ID, answers.getQuestion_id());

        Log.i("MNGR_A_LOG", String.valueOf(QuizProvider.ANSWER_URI) + " " + answerValues);
        _context.getContentResolver().insert(QuizProvider.ANSWER_URI, answerValues);
    }

    public List<Questions> getAllQuestions(){
        List<Questions> questions = new ArrayList<>();
        Cursor cursor = _context.getContentResolver().query(QuizContract.Q_CONTENT_PROVIDER, null, null, null, null);
        while (cursor.moveToNext()){
            questions.add(new Questions(
                    cursor.getInt(cursor.getColumnIndex(QuizContract.QUESTION_ID)),
                    cursor.getString(cursor.getColumnIndex(QuizContract.KEY_QUESTION))));
        }
        cursor.close();
        Log.i("MNGR_ARR_LOG", "questions: " + questions);
        return questions;
    }

    public List<Answers> getAnswers(){
        List<Answers> answers = new ArrayList<>();
        Cursor cursor = _context.getContentResolver().query(QuizContract.A_CONTENT_PROVIDER, null, null, null, null);
        while (cursor.moveToNext()){
            answers.add(new Answers(
                    cursor.getString(cursor.getColumnIndex(QuizContract.ANSWER_ID)), //answer id
                    cursor.getString(cursor.getColumnIndex(QuizContract.KEY_ANSWER)), // txt,
                    cursor.getInt(cursor.getColumnIndex(QuizContract.KEY_Q_ID)))); // question id
        }
        cursor.close();
        Log.i("MNGR_ARR_LOG", "answers: " + answers);
        return answers;
    }

    public int rowCont(){
        int row = 0;
        Cursor questionCursor = _context.getContentResolver().query(QuizContract.Q_CONTENT_PROVIDER, new String[]{"\"" + QuizContract.QUESTION_ID +"\""}, null, null, null);
        //row = questionCursor.getCount();
        while (questionCursor.moveToNext()){
            row = questionCursor.getColumnIndex(QuizContract.QUESTION_ID);
        }
        questionCursor.close();
        return row;
    }

    //not used:
    public List<Answers> getAnswersById(){
        final int id = this.rowCont();
        final String[] columns = new String[]{"\"" + QuizContract.ANSWER_ID + "\", \"" + QuizContract.KEY_ANSWER + "\", \"" + QuizContract.KEY_Q_ID + "\""};
        final String where = "\"" + QuizContract.KEY_Q_ID + "\"= ?";
        final String[] whereArgs = new String[]{"\""+ id + "\""};
        List<Answers> answers = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(QuizContract.A_CONTENT_PROVIDER, columns, where, whereArgs, null);
        while (cursor.moveToNext()){
            answers.add(new Answers(
                    cursor.getString(cursor.getColumnIndex(QuizContract.ANSWER_ID)),
                    cursor.getString(cursor.getColumnIndex(QuizContract.KEY_ANSWER)),
                    cursor.getInt(cursor.getColumnIndex(QuizContract.KEY_CORRECT))==1, //for cursor to get boolean
                    cursor.getInt(cursor.getColumnIndex(QuizContract.KEY_Q_ID))));
        }
        cursor.close();
        Log.i("MNGR_ARR_LOG", "answers: " + answers);
        return answers;
    }
}
