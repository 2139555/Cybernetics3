package com.example.witsonline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BrowseQuizQuestions extends AppCompatActivity implements View.OnScrollChangeListener{
    TextView quizName;
    private ArrayList<QuestionV> listQuestions;
    private String answerOptions[] ={"Select answer","Answer option 1","Answer option 2","Answer option 3"," Answer option 4"};
    private RecyclerView recyclerView;
    //Volley Request Queue
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private int questionCount = 1;
    //create quiz dialog
    private Button dialogAddQuestion;
    private Button cancel;
    private TextInputLayout question;
    private TextInputLayout answerOption1;
    private TextInputLayout answerOption2;
    private TextInputLayout answerOption3;
    private TextInputLayout answerOption4;
    private TextInputLayout markAlloc;
    private  Button addQuestion;
    private ImageView quizEye;
    private Spinner spinner;
    private int correctAnswer;
    private boolean correctAnswerSelected = false;

    //quiz visibility dialog
    private TextView visibilityText;
    private Button dialogVisibilityYes;
    private Button dialogVisibilityNo;


    String webURL = "https://lamp.ms.wits.ac.za/home/s2105624/questionFeed.php?page=";
    String ansURL = "https://lamp.ms.wits.ac.za/home/s2105624/answerFeed.php?page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_quiz_questions);
        quizName = findViewById(R.id.quizName);
        quizName.setText(QUIZ.NAME);
        addQuestion = findViewById(R.id.addQuestion);
        recyclerView = (RecyclerView)findViewById(R.id.questionsRecyclerView);
        quizEye = findViewById(R.id.editQuizVisibility);
        if (QUIZ.VISIBILITY == 1){
            quizEye.setImageResource(R.drawable.ic_baseline_visibility_24);
        }
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

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewQuestionDialog();
            }
        });

        quizEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewVisibilityDialog();
            }
        });
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
                    //Calling method parseData to parse the json response
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
                        TextView noQuestions = (TextView) findViewById(R.id.noQuizQuestions);
                        noQuestions.setVisibility(View.VISIBLE);
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
                question.setAnswerOption1ID(json.getInt("1"));
                question.setAnswerOption2(json.getString("2"));
                question.setAnswerOption2ID(json.getInt("3"));
                question.setAnswerOption3(json.getString("4"));
                question.setAnswerOption3ID(json.getInt("5"));
                question.setAnswerOption4(json.getString("6"));
                question.setAnswerOption4ID(json.getInt("7"));
                question.setCorrectOption(json.getString("8"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listQuestions.add(question);
            adapter.notifyDataSetChanged();

        }
    }
    @Generated
    public void createNewQuestionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View viewPopUp = LayoutInflater.from(this)
                .inflate(R.layout.add_question_dialog, null);

        dialogBuilder.setView(viewPopUp);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        dialogAddQuestion = (Button) viewPopUp.findViewById(R.id.dialogAddQuestion);
        question = (TextInputLayout) viewPopUp.findViewById(R.id.dialogQuestionText);
        answerOption1 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption1);
        answerOption2 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption2);
        answerOption3 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption3);
        answerOption4 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption4);
        markAlloc = (TextInputLayout) viewPopUp.findViewById(R.id.dialogMarkAlloc);
        spinner = (Spinner)viewPopUp.findViewById(R.id.spCorrectAnswer);
        cancel = (Button) viewPopUp.findViewById(R.id.cancelAddQuestion);
        ArrayAdapter<String> adapter = new ArrayAdapter(BrowseQuizQuestions.this,android.R.layout.simple_spinner_item,answerOptions){

            @Override
            @Generated
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    //Disable the first item of spinner.
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            @Generated
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(Color.GRAY);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return view;
            }};
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            @Generated
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                correctAnswer = position;
                correctAnswerSelected = true;
            }

            @Override
            @Generated
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                isEmpty(question);
                isEmpty(answerOption1);
                isEmpty(answerOption2);
                isEmpty(answerOption3);
                isEmpty(answerOption4);
                invalidMark(markAlloc);
                if(!noAnswerSelected() && !isEmpty(question) && !isEmpty(answerOption1)&& !isEmpty(answerOption2)&&!isEmpty(answerOption3)&&!isEmpty(answerOption4) && !invalidMark(markAlloc)){
                    String quest = question.getEditText().getText().toString();
                    String ans1 = answerOption1.getEditText().getText().toString();
                    String ans2 = answerOption2.getEditText().getText().toString();
                    String ans3 = answerOption3.getEditText().getText().toString();
                    String ans4 = answerOption4.getEditText().getText().toString();
                    String mark = markAlloc.getEditText().getText().toString();
                    try {
                        addQuestion("addQuestion.php",quest,ans1,ans2,ans3,ans4,correctAnswer,mark);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Generated
    private void addQuestion (String phpFile,String question,String answer1,String answer2,String answer3,String answer4, int answerCorrect,String mark) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/" + phpFile).newBuilder();
        urlBuilder.addQueryParameter("quizId", Integer.toString(QUIZ.ID));
        urlBuilder.addQueryParameter("questMark", mark);
        urlBuilder.addQueryParameter("text", question);
        urlBuilder.addQueryParameter("option1",answer1);
        urlBuilder.addQueryParameter("option2",answer2);
        urlBuilder.addQueryParameter("option3",answer3);
        urlBuilder.addQueryParameter("option4",answer4);
        urlBuilder.addQueryParameter("correctAnswer",Integer.toString(answerCorrect));
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
                BrowseQuizQuestions.this.runOnUiThread(new Runnable() {
                    @Override
                    @Generated
                    public void run() {
                        Log.d("HERE",responseData.toString());
                        if(responseData.trim().equals("Successful")) {
                            Toast toast = Toast.makeText(BrowseQuizQuestions.this,"Successful", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(BrowseQuizQuestions.this, BrowseQuizQuestions.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast toast = Toast.makeText(BrowseQuizQuestions.this, "Couldn't add your question ", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        });
    }

    @Generated
    public void createNewVisibilityDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View viewPopUp = LayoutInflater.from(this)
                .inflate(R.layout.set_quiz_visibility_dialog, null);

        dialogBuilder.setView(viewPopUp);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();


        visibilityText = viewPopUp.findViewById(R.id.dialog_question_visibility);
        dialogVisibilityYes = viewPopUp.findViewById(R.id.btn_visibiity_yes);
        dialogVisibilityNo = viewPopUp.findViewById(R.id.btn_visibility_no);

        if (QUIZ.VISIBILITY==1){
            //visibilityText.setText("Do you want to hide this quiz?");
            //check whether any attempt was made
            try {
                doAttemptRequest("checkQuizAttempts.php");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            visibilityText.setText("Do you want to publish this quiz?");
        }

        dialogVisibilityYes.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                if (QUIZ.VISIBILITY == 0){
                    quizEye.setImageResource(R.drawable.ic_baseline_visibility_24);
                    QUIZ.VISIBILITY = 1;
                    try {
                        setQuizVisibility("updateQuizVisibility.php");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (visibilityText.getText().toString().trim().equals("Do you want to hide this quiz?")){
                    quizEye.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    QUIZ.VISIBILITY =0;
                    try {
                        setQuizVisibility("updateQuizVisibility.php");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });

        dialogVisibilityNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Generated
    private void doAttemptRequest(String phpFile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/" + phpFile).newBuilder();
        urlBuilder.addQueryParameter("quizID", Integer.toString(QUIZ.ID));
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
                BrowseQuizQuestions.this.runOnUiThread(new Runnable() {
                    @Override
                    @Generated
                    public void run() {
                            if (responseData.trim().equals("attempt")) {
                                visibilityText.setText("This quiz cannot be hidden as it has already been attempted.");
                                dialogVisibilityYes.setText("OKAY");
                                dialogVisibilityNo.setText("CANCEL");
                            }
                            else{
                                visibilityText.setText("Do you want to hide this quiz?");
                            }

                    }
                });
            }
        });
    }


    @Generated
    private void setQuizVisibility (String phpFile) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/" + phpFile).newBuilder();
        urlBuilder.addQueryParameter("quizID", Integer.toString(QUIZ.ID));
        urlBuilder.addQueryParameter("quizVisibility",Integer.toString(QUIZ.VISIBILITY));
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
                BrowseQuizQuestions.this.runOnUiThread(new Runnable() {
                    @Override
                    @Generated
                    public void run() {
                            Toast toast = Toast.makeText(BrowseQuizQuestions.this,"Successful", Toast.LENGTH_LONG);
                            toast.show();
                    }
                });
            }
        });
    }

    public boolean isEmpty(TextInputLayout text) {
        boolean empty = false;
        text.setError(null);
        if (text.getEditText().getText().toString().isEmpty()) {
            text.setError("Field can't be empty");
            empty = true;
        }

        return empty;
    }

    public boolean invalidMark(TextInputLayout text) {
        boolean invalid = false;
        text.setError(null);
        String mark = text.getEditText().getText().toString();
        if (mark.isEmpty()) {
            text.setError("Field can't be empty");
            invalid = true;
        }
        else {
           int m = Integer.parseInt(mark);
            if (m == 0) {
                text.setError("Mark must be greater than 0");
                invalid = true;
            }
        }
        return invalid;
    }

    public boolean noAnswerSelected() {
        if (!correctAnswerSelected) {
            Toast.makeText(BrowseQuizQuestions.this,"Select a correct answer", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


    //This me+thod will check if the recyclerview has reached the bottom or not
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
    @Override
    @Generated
    public void onBackPressed(){
        Intent intent = new Intent(BrowseQuizQuestions.this, QuizActivity.class);
        startActivity(intent);
        finish();
    }

}