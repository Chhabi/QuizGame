package com.example.tiagoeira.quizgame;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.tiagoeira.quizgame.model.Answers;
import com.example.tiagoeira.quizgame.model.Questions;
import com.example.tiagoeira.quizgame.provider.QuizManager;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class QuestionIntentService extends IntentService {

    private static final String QST_SERVICE_LOG = "QST_SERVICE_LOG";
    private final QuizManager manager;

    public QuestionIntentService() {
        super("QuestionIntentService");
        this.manager = new QuizManager(this);
    }

    //just for test
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(QST_SERVICE_LOG, "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            URL url = new URL("http://54.187.166.51:81/questions");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            String res = "";
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
                res += line;

            /* Get the data from JSONArray response: */
            JSONArray response = new JSONArray(res);

            //get the questions from response array
            for (int i=0; i< response.length();i++ ){
                JSONObject jQstData = response.getJSONObject(i);

                int questionId = jQstData.getInt("id");
                String questionString = jQstData.getString("question");

                //save to database
                manager.saveQuestions(new Questions(
                        jQstData.getInt("id"),
                        jQstData.getString("question")));

                Log.i(QST_SERVICE_LOG, questionId + " - Q: " + questionString);

                //get answers from the answers array
                JSONArray jAnswers = jQstData.getJSONArray("answers");
                for(int j=0;j<jAnswers.length();j++){
                    JSONObject jAnsData = jAnswers.getJSONObject(j);

                    String answerId = jAnsData.getString("id");
                    String answerString = jAnsData.getString("answer");
                    boolean answerCorrect = jAnsData.getBoolean("correct");

                    //save to database
                    manager.saveAnswers(new Answers(
                            jAnsData.getString("id"),
                            jAnsData.getString("answer"),
                            jAnsData.getBoolean("correct"),
                            jQstData.getInt("id")));

                    Log.i(QST_SERVICE_LOG, answerId + " - " + answerString + " correct? " + answerCorrect);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Log.i(QST_SERVICE_LOG, "something is wrong!");
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(QST_SERVICE_LOG, "onDestroy");
    }
}
