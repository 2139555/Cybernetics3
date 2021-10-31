package com.example.witsonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private Button submit;
    private TextView QuizTopic;
    private RecyclerView recyclerView;


    //Volley Request Queue
    private RequestQueue requestQueue;
    private int questionCount = 1;
    //Questions recyclerView
    private RecyclerView.LayoutManager layoutManager;
    private QuestionCardAdapter adapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    private ArrayList<QuestionV> listQuestions;
    String webURL = "https://lamp.ms.wits.ac.za/home/s2105624/questionFeed.php?page=";
    private RecyclerView.ViewHolder holder;

    @Override
    @Generated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_attempt_quiz );

        submit = (Button)findViewById( R.id.btnSubmitQuiz_st );
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

       showWarningDialog();

        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                SubmitQuiz(listQuestions);
            }
        } );


    }
    @Generated
    private void showWarningDialog() {
        AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(this);
        final View viewPopUp = LayoutInflater.from(this)
                .inflate(R.layout.dialog_quiz_warning_student, null);

        Button dlg_btn_ok = (Button)viewPopUp.findViewById( R.id.Ok_warning);
        DialogBuilder.setView(viewPopUp);
        AlertDialog DialoG = DialogBuilder.create();
        DialoG.show();
        dlg_btn_ok.setOnClickListener( new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                DialoG.dismiss();
            }
        } );


    }
    @Generated
    private void SubmitQuiz(ArrayList<QuestionV> listQuestions) {
        String AnswerStr = "";
        int mark = 0;

        int Verify = 0;
        long s;

        ArrayList<QuestionV> Quest = adapter.getAll();
        for(int i = 0; i<Quest.size();i++){
            if(i == Quest.size()-1){

                if(!(Quest.get(i).getSelcted_ans()).equals("")){
                    Verify++;
                    AnswerStr = AnswerStr + Quest.get( i ).getSelcted_ans();
                }
                else{
                    AnswerStr = AnswerStr + "-1";
                }

                //check if answer was correct
                if(Quest.get(i).getSelectedAnswerText().trim().equals(Quest.get(i).getCorrectOption().trim())){
                    mark = mark + Quest.get(i).getQuestionMarkAlloc();
                }


            }
            else{
                if(!(Quest.get(i).getSelcted_ans()).equals("")){
                    Verify++;
                    AnswerStr = AnswerStr + Quest.get( i ).getSelcted_ans()+";";
                }
                else{
                    AnswerStr = AnswerStr +"-1;";
                }

                //check if answer was correct
                if(Quest.get(i).getSelectedAnswerText().trim().equals(Quest.get(i).getCorrectOption().trim())){
                    mark = mark + Quest.get(i).getQuestionMarkAlloc();
                }
            }
        }
        boolean X = true;
        if(Verify != Quest.size()){
            X = false;
        }
        createSubmitDialog(X, AnswerStr, mark);




    }
    @Generated
    private void createSubmitDialog(boolean V,String answerStr, int mark) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View viewPopUp = LayoutInflater.from(this)
                .inflate(R.layout.dialog_submit_quiz_student, null);
        TextView question = (TextView)viewPopUp.findViewById( R.id.submit_Text);

        Button dlg_btn_yes = (Button)viewPopUp.findViewById( R.id.Yes_submit );
        Button dlg_btn_no = (Button)viewPopUp.findViewById( R.id.cancelSubmit );


        if(!V){
            //error dialog
            question.setText( "Note: Some questions are not answered!  \n Do you still want to submit this quiz?" );

        }

        dialogBuilder.setView(viewPopUp);
        dialog = dialogBuilder.create();
        dialog.show();
        dlg_btn_yes.setOnClickListener( new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                sendAnswers(answerStr,mark);
                dialog.dismiss();
                QuizSubmitedDialog();
            }
        } );
        dlg_btn_no.setOnClickListener( new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {

                dialog.dismiss();
            }
        } );

    }
    @Generated
    private void QuizSubmitedDialog() {
        AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(this);
        final View viewPopUp = LayoutInflater.from(this)
                .inflate(R.layout.dlg_quiz_submited_student, null);

        Button dlg_btn_ok = (Button)viewPopUp.findViewById( R.id.Ok_quiz_submitted);
        DialogBuilder.setView(viewPopUp);
        AlertDialog DialoG = DialogBuilder.create();
        DialoG.show();

        dlg_btn_ok.setOnClickListener( new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),activity_browse_quizes_student.class);
                startActivity( i );
            }
        } );
    }
    @Generated
    private void sendAnswers(String Answer, int mark) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/addAttempt.php").newBuilder();
        urlBuilder.addQueryParameter("attemptQuiz", Integer.toString(QUIZ.ID));
        urlBuilder.addQueryParameter("attemptStudent", USER.USERNAME);
        urlBuilder.addQueryParameter("attemptAnswers", Answer);
        urlBuilder.addQueryParameter("attemptMark", Integer.toString(mark));
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
                final String responseData = response.body().string();
                if(response.isSuccessful()){
                    activity_attempt_quiz.this.runOnUiThread( new Runnable() {
                        @Override
                        @Generated
                        public void run() {

                        }
                    } );
                }
            }
        });

    }



    //This method will get Data from the web api
    @Generated
    public void getData() {
        //Adding the method to the queue by calling the method getDatafromServer
        requestQueue.add(getDataFromServer(questionCount));
        //Incrementing the course counter
        questionCount++;
    }
    @Generated
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
            QuestionV question = new QuestionV();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

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
    @Generated
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


        if (isLastItemDistplaying(recyclerView)) {
            //Calling the method getData again
            getData();
        }
    }
}