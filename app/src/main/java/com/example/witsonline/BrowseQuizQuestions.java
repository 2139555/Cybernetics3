package com.example.witsonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BrowseQuizQuestions extends AppCompatActivity implements View.OnScrollChangeListener{
    TextView quizName;
    private ArrayList<Question> listQuestions;
    private RecyclerView recyclerView;
    //Volley Request Queue
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private int questionCount = 1;
    String webURL = "https://lamp.ms.wits.ac.za/home/s2105624/questionFeed.php?page=";
    String ansURL = "https://lamp.ms.wits.ac.za/home/s2105624/answerFeed.php?page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_quiz_questions);
        quizName = findViewById(R.id.quizName);
        quizName.setText(QUIZ.NAME);

        recyclerView = (RecyclerView)findViewById(R.id.questionsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our Course list
        listQuestions = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //Calling method getData to fetch data
        getData();

        //Adding an scroll change listener to recyclerView
        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new QuestionCardAdapter(listQuestions, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
    @Generated
    private JsonArrayRequest getDataFromServer(int requestCount){
        //Initializing progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.questionProgressBar);

        //Displaying ProgressBar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(webURL + String.valueOf(requestCount)+"&quizId="+QUIZ.ID,
                (response) -> {
                    //Calling method parseData to parse the json responce
                    try {
                        parseData(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Hiding the progressBar
                    progressBar.setVisibility(View.GONE);

                    if(listQuestions.isEmpty()){
                       /* TextView noLessons = (TextView)findViewById(R.id.noLessonItems);
                        noLessons.setVisibility(View.VISIBLE);*/
                    }
                },
                (error) -> {
                    progressBar.setVisibility(View.GONE);
                    //If an error occurs that means end of the list has been reached

                    if(listQuestions.isEmpty()){
                       /* TextView noLessons = (TextView)findViewById(R.id.noLessonItems);
                        noLessons.setVisibility(View.VISIBLE);*/
                    }
                    else{
                        Toast.makeText(BrowseQuizQuestions.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get Data from the web api
    @Generated
    private void getData(){
        //Adding the method to the queue by calling the method getDatafromServer
        requestQueue.add(getDataFromServer(questionCount));
        //Incrementing the course counter
        questionCount++;
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listQuestions.add(question);

            adapter.notifyDataSetChanged();

        }
    }
    //This method will check if the recyclerview has reached the bottom or not
    public boolean isLastItemDistplaying(RecyclerView recyclerView){
        if(recyclerView.getAdapter().getItemCount() != 0){
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() -1){
                return true;
            }
        }
        return false;
    }

    @Override
    @Generated
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //if Scrolled at last then
        if(isLastItemDistplaying(recyclerView)){
            //Calling the method getData again
            getData();
        }
    }
}