package com.example.tiagoeira.quizgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tiagoeira.quizgame.model.Answers;
import com.example.tiagoeira.quizgame.model.Questions;
import com.example.tiagoeira.quizgame.provider.QuizManager;

import java.util.List;

public class GamePlayActivity extends Activity implements View.OnClickListener {
    List<Questions> questions;
    //List<Answers> answers;
    int qId = 0;
    Questions currentQuestion;
    //Answers currentAnswer;
    TextView txtQuestion, txtQuestionNumber, txtScore;
    Button AnswerA, AnswerB, AnswerC, AnswerD, btnHelp50, btnHelpPub, btnHelpPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        QuizManager quizManager = new QuizManager(this);
        questions = quizManager.getAllQuestions();
        //answers = quizManager.getAnswersById(); //by id
        currentQuestion = questions.get(qId);
        //currentAnswer = answers.get();

        /* - Text views - */
        txtQuestion = (TextView) findViewById(R.id.textViewQuestion);
        txtQuestionNumber = (TextView) findViewById(R.id.textViewQnr);
        txtScore = (TextView) findViewById(R.id.textViewScore);

        /* - Answer Buttons - */

        Button AnswerA = (Button) findViewById(R.id.btnAnswerA);
        AnswerA.setOnClickListener(this);
        Button AnswerB = (Button) findViewById(R.id.btnAnswerB);
        AnswerB.setOnClickListener(this);
        Button AnswerC = (Button) findViewById(R.id.btnAnswerC);
        AnswerC.setOnClickListener(this);
        Button AnswerD = (Button) findViewById(R.id.btnAnswerD);
        AnswerD.setOnClickListener(this);

        /* - Help Buttons - */
        Button btnHelp50 = (Button) findViewById(R.id.btnHelp50);
        //btnHelp50.setEnabled(true);
        btnHelp50.setOnClickListener(this);

        Button btnHelpPub = (Button) findViewById(R.id.btnHelpPub);
        //btnHelpPub.setEnabled(true);
        btnHelpPub.setOnClickListener(this);

        Button btnHelpPhone= (Button) findViewById(R.id.btnHelpPhone);
        //btnHelpPhone.setEnabled(true);
        btnHelpPhone.setOnClickListener(this);

        //set the view for the question set
        setQuestionView();
    }

    private void setQuestionView(){
        txtQuestion.setText(currentQuestion.getQuestionString());
        //txtQuestionNumber.setText(currentQuestion.getQuestionId());
        qId++;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnHelp50:
                btnHelp50.setEnabled(false);
                // add functionality here
                AnswerA.setVisibility(View.INVISIBLE); //just for test!

                Toast.makeText(getApplicationContext(), "Ajuda 50:50", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnHelpPub:
                btnHelpPub.setEnabled(false);
                //add functionality here
                Toast.makeText(getApplicationContext(), "Ajuda do Público", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnHelpPhone:
                btnHelpPhone.setEnabled(false);
                //add functionality here - toast right answer
                Toast.makeText(getApplicationContext(), "Ajuda Telefónica", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnAnswerA:
                //just for test purposes
                if (qId<15){
                    currentQuestion = questions.get(qId);
                    setQuestionView();
                }
                else{
                    //just for test purposes
                    Toast.makeText(getApplicationContext(), "Fim", Toast.LENGTH_SHORT).show();

                    //change to GameOverActivity
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                break;
            case R.id.btnAnswerB:
                //just for test purposes
                if (qId<15){
                    currentQuestion = questions.get(qId);
                    setQuestionView();
                }
                else{
                    //just for test purposes
                    Toast.makeText(getApplicationContext(), "Fim", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAnswerC:
                //just for test purposes
                if (qId<15){
                    currentQuestion = questions.get(qId);
                    setQuestionView();
                }
                else{
                    //just for test purposes
                    Toast.makeText(getApplicationContext(), "Fim", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAnswerD:
                //just for test purposes
                if (qId<15){
                    currentQuestion = questions.get(qId);
                    setQuestionView();
                }
                else{
                    //just for test purposes
                    Toast.makeText(getApplicationContext(), "Fim", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
