package com.example.witsonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class activity_attempt_quiz extends AppCompatActivity implements View.OnScrollChangeListener{
    Button Submit;
    TextView QuizTopic;
    RecyclerView recyclerView;
    //Volley Request Queue
    private RequestQueue requestQueue;
    private int questionCount = 1;
    //Questions recyclerView
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    private ArrayList<Question> listQuestions;
    String webURL = "https://lamp.ms.wits.ac.za/home/s2105624/questionFeed.php?page=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_attempt_quiz );

        Submit = (Button)findViewById( R.id.btnSubmitQuiz_st );
        QuizTopic = (TextView)findViewById( R.id.quiz_topic_st);

        recyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerView_st);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);

        listQuestions = new ArrayList<>();
        getData();


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
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.questionProgressBar_AttemptQuiz);

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

                    if(listQuestions.isEmpty()){
                        TextView noQuestions = (TextView)findViewById(R.id.noQuestions_AttempQuiz);
                        noQuestions.setVisibility(View.VISIBLE);
                    }
                },
                (error) -> {
                    progressBar.setVisibility(View.GONE);
                    //If an error occurs that means end of the list has been reached

                    if(listQuestions.isEmpty()){
                        TextView noQuestions = (TextView) findViewById(R.id.noQuestions_AttempQuiz);
                        noQuestions.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(activity_attempt_quiz.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
        //Returning the request
        return jsonArrayRequest;
    }
    //This method will parse json Data
    @Generated
    private void parseData(JSONArray array) throws JSONException {
        for (int i = 0; i< array.length(); i++) {
            // Creating the Course object
            Question question = new Question();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the course object
                question.setQuestionText(json.getString("questionText"));
                question.setQuestionID(json.getString("questionId"));
                question.setQuestionMarkAlloc(json.getInt("questionMark"));

                question.setAnswerOption1(json.getString("0"));
                question.setAnswerOption2(json.getString("1"));
                question.setAnswerOption3(json.getString("2"));
                question.setAnswerOption4(json.getString("3"));

                question.setCorrectOption(json.getString("4"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listQuestions.add(question);
            adapter.notifyDataSetChanged();

        }
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