package com.example.witsonline;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizFeedback extends AppCompatActivity implements View.OnScrollChangeListener {
    TextView QuizTopic;
    TextView QuizScore;
    RecyclerView recyclerView;

    //Volley Request Queue
    private RequestQueue requestQueue;
    private int questionCount = 1;

    //Questions recyclerView
    private RecyclerView.LayoutManager layoutManager;

    private QuestionCardAdapter adapter;
    private ArrayList<QuestionV> listQuestions;

    private String webURL = "https://lamp.ms.wits.ac.za/home/s2105624/questionFeed.php?page=";

    private String Answers = "";
    String getAnswersURL = "https://lamp.ms.wits.ac.za/home/s2105624/QuizFeedback.php?Student_Number=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_feedback);

        requestQueue = Volley.newRequestQueue(this);

        getAnswers();


        QuizTopic = (TextView)findViewById( R.id.quiz_feedback_topic);
        QuizScore = (TextView)findViewById( R.id.marks);

        //QuizScore.setText("x out of " + QUIZ.MARK_ALLOC);

        recyclerView = (RecyclerView) findViewById(R.id.feedbackRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listQuestions = new ArrayList<>();
        //getData();

        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new QuestionCardAdapter(listQuestions, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        QuizTopic.setText(QUIZ.NAME);

    }

    //This method will get Data from the web api
    @Generated
    public void getData() {
        //Adding the method to the queue by calling the method getDatafromServer
        requestQueue.add(getDataFromServer(questionCount));
        //Incrementing the course counter
        questionCount++;
    }

    private JsonArrayRequest getDataFromServer(int requestCount){
        //Initializing progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.quizFeedbackProgressBar);

        //Displaying ProgressBar
        progressBar.setVisibility( View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(webURL + String.valueOf(requestCount)+"&quizId="+QUIZ.ID,
                (response) -> {
                    //Calling method parseData to parse the json response
                    try {
                        parseData(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Hiding the progressBar
                    progressBar.setVisibility(View.GONE);


                },
                (error) -> {
                    progressBar.setVisibility(View.GONE);
                    //If an error occurs that means end of the list has been reached


                });
        //Returning the request
        return jsonArrayRequest;
    }
    //This method will parse json Data
    @Generated
    private void parseData(JSONArray array) throws JSONException {

        for (int i = 0; i< array.length(); i++) {
            // Creating the Course object
            QuestionV question = new QuestionV();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                /*/calculating marks
                intMarkObtained = intMarkObtained + Integer.parseInt(json.getString("questionMark"));
                markObtained = String.valueOf(intMarkObtained);*/

                //Adding data to the course object
                question.setQuestionText(json.getString("questionText"));
                question.setQuestionID(json.getString("questionId"));
                question.setQuestionMarkAlloc(json.getInt("questionMark"));

                question.setAnswerOption1(json.getString("0"));
                question.setAnswerOption2(json.getString("2"));
                question.setAnswerOption3(json.getString("4"));
                question.setAnswerOption4(json.getString("6"));
                question.setCorrectOption(json.getString("8"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listQuestions.add(question);
            adapter.notifyDataSetChanged();
        }

    }

    @Generated
    private void getAnswers() {
        //Adding the method to the queue by calling the method getTagData
        requestQueue.add(getAnswersFromServer());
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Generated
    private JsonArrayRequest getAnswersFromServer() {
        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getAnswersURL + USER.USER_NUM + "&quizID=" + QUIZ.ID,
                (response) -> {
                    //Calling method parseData to parse the json response
                    parseAnswerData(response);
                    //Hiding the progressBar
                },
                (error) -> {
                    //If an error occurs that means user was not found or does not exist
                    Toast.makeText(QuizFeedback.this, "Student's answers were not found", Toast.LENGTH_SHORT).show();
                });
        //Returning the request
        return jsonArrayRequest;
    }

    //This method will parse json Data
    @Generated
    private void parseAnswerData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {

            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //getting Answers from Quiz Attempts
                Answers = json.getString("Attempt_Answers");
                String mark = json.getString("Attempt_Mark");
                getQuizAttempt();

                //setting marks
                QuizScore.setVisibility(View.VISIBLE);
                QuizScore.setText(mark+" out of " + QUIZ.MARK_ALLOC);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getQuizAttempt(){
        String[] parts = Answers.split(";");
        //Toast.makeText(QuizFeedback.this,parts.length, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < QUIZ.NUM_QUESTIONS;i++){
            USER.attemptAnswers.put(Integer.toString(i+1),parts[i]);
        }
        getData();
    }

    //This method will check if the recyclerview has reached the bottom or not
    public boolean isLastItemDistplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLastItemDistplaying(recyclerView)) {
            //Calling the method getData again
            getData();
        }
    }

}