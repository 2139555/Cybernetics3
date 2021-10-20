package com.example.witsonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;

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

public class activity_browse_quizes_student extends AppCompatActivity implements View.OnScrollChangeListener {
    private TextView course;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    private ArrayList<QuizV> listQuizVs;

    String webURL = "https://lamp.ms.wits.ac.za/home/s2105624/getQuizes_student.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_browse_quizes_student );
        course = (TextView)findViewById( R.id.quizCourseCode_st);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_quizes_st);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        course.setText(COURSE.CODE);
        listQuizVs = new ArrayList<>();
        getDataFromServer();

        //Adding an scroll change listener to recyclerView
        recyclerView.setOnScrollChangeListener( activity_browse_quizes_student.this );

        //initializing our adapter
        adapter = new QuizCardAdapter(listQuizVs, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);



    }


    private void parseData(String array) throws JSONException {
        JSONObject json = new JSONObject(array);
        JSONArray jsonArray = json.getJSONArray( "myArr" );
        if(jsonArray.length() == 0){
            ((ProgressBar)findViewById( R.id.quizProgressBar_st )).setVisibility( View.GONE );
            TextView NoQuizes = (TextView)findViewById( R.id.noQuizItems_st );
            NoQuizes.setVisibility( View.VISIBLE );
        }
        else{
            for (int i = 0; i < jsonArray.length(); i++) {
                // Creating the Quiz object
                QuizV quizV = new QuizV();
                JSONObject jsonO = null;
                try {
                    //Getting json
                    jsonO = jsonArray.getJSONObject(i);

                    //Adding data to the quiz object
                    quizV.setQuizID(jsonO.getString("Quiz_ID"));
                    quizV.setQuizName(jsonO.getString("Quiz_Name"));
                    quizV.setQuizMarkAlloc(jsonO.getInt("Quiz_Mark_Alloc"));
                    quizV.setQuizNumQuestions(jsonO.getInt("Quiz_Num_Questions"));
                    quizV.setQuizVisibility(jsonO.getInt("Quiz_Visibility"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((ProgressBar)findViewById( R.id.quizProgressBar_st )).setVisibility( View.GONE );

                listQuizVs.add(quizV);
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }
    private void getDataFromServer() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.quizProgressBar_st);
        progressBar.setVisibility( View.VISIBLE );
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(webURL + "&courseCode=" + COURSE.CODE).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            @Generated
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            @Generated
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                if(response.isSuccessful()){

                    activity_browse_quizes_student.this.runOnUiThread( new Runnable() {
                        @Override
                        @Generated
                        public void run() {
                            //Toast.makeText( CourseHomePageInstructor.this, "Retrieval Successful", Toast.LENGTH_SHORT ).show();
                            try {
                                parseData(responseData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ;

                        }
                    } );
                }
                else{
                    activity_browse_quizes_student.this.runOnUiThread( new Runnable() {
                        @Override
                        @Generated
                        public void run() {

                        }
                    } );

                }
            }
        });

    }
}