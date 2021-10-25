package com.example.witsonline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

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

public class QuestionCardAdapter extends RecyclerView.Adapter<QuestionCardAdapter.ViewHolder> {
    private Context context;
    ArrayList<QuestionV> questions; //List to store all Courses
    //For determining if student is a tutor

    //create quiz dialog
    private Button dialogUpdateQuestion;
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

    //for getting feedback
    private RequestQueue requestQueue;
    String Answers = "";
    String getAnswersURL="https://lamp.ms.wits.ac.za/home/s2105624/QuizFeedback.php?Student_Number=";
    int iterator = -1;
    int answer = 0;

    private ProgressBar progressBarReq;

    //Constructor of this class
    public QuestionCardAdapter(ArrayList<QuestionV> questions, Context context) {
        super();
        //Getting all requests
        this.questions= questions;
        this.context = context;
    }


    @NonNull
    @Override
    @Generated
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    @Generated
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Getting the particular item from the list

        final QuestionV question = questions.get(position);
        requestQueue = Volley.newRequestQueue(context);
        getAnswers();

        String strContext = context.toString();

        if(strContext.contains("QuizFeedback")){
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]
                            {
                                    new int[]{-android.R.attr.state_enabled}, // Disabled
                                    new int[]{android.R.attr.state_enabled}   // Enabled
                            },
                    new int[]
                            {
                                    Color.RED, // disabled
                                    Color.GREEN   // enabled
                            }
            );

            holder.answerOption1.setClickable(false);
            holder.answerOption2.setClickable(false);
            holder.answerOption3.setClickable(false);
            holder.answerOption4.setClickable(false);

            holder.answerOption1.setChecked(false);
            holder.answerOption2.setChecked(false);
            holder.answerOption3.setChecked(false);
            holder.answerOption4.setChecked(false);

            holder.question.setText(questions.get(position).getQuestionText());
            holder.answerOption1.setText(question.getAnswerOption1());
            holder.answer1ID.setText(Integer.toString(question.getAnswerOption1ID()));
            holder.answerOption2.setText(question.getAnswerOption2());
            holder.answer2ID.setText(Integer.toString(question.getAnswerOption2ID()));
            holder.answerOption3.setText(question.getAnswerOption3());
            holder.answer3ID.setText(Integer.toString(question.getAnswerOption3ID()));
            holder.answerOption4.setText(question.getAnswerOption4());
            holder.answer4ID.setText(Integer.toString(question.getAnswerOption4ID()));
            holder.questionMarks.setText("(" + question.getQuestionMarkAlloc() + ")");

            String[] questNo = question.getQuestionID().split("-");
            holder.questionNo.setText(questNo[1] + ".");


            /*iterator = iterator + Integer.parseInt(questNo[1]);
            answer = Character.getNumericValue(Answers.charAt(iterator));
            if(answer == 2){
                holder.answerOption1.setChecked(true);
            }*/

            if (holder.answerOption1.getText().toString().equals(question.getCorrectOption())) {
                holder.answerOption1.setButtonTintList(colorStateList);
                holder.answerOption1.setTextColor(ContextCompat.getColor(context,android.R.color.holo_green_dark));
                holder.checkedID.setText("1");
            }
            else if (holder.answerOption2.getText().toString().equals(question.getCorrectOption())) {
                holder.answerOption2.setButtonTintList(colorStateList);
                holder.answerOption2.setTextColor(ContextCompat.getColor(context,android.R.color.holo_green_dark));
                holder.checkedID.setText("2");
            }
            else if (holder.answerOption3.getText().toString().equals(question.getCorrectOption())) {
                holder.answerOption3.setButtonTintList(colorStateList);
                holder.answerOption3.setTextColor(ContextCompat.getColor(context,android.R.color.holo_green_dark));
                holder.checkedID.setText("3");
            }
            else {
                holder.answerOption4.setButtonTintList(colorStateList);
                holder.answerOption4.setTextColor(ContextCompat.getColor(context,android.R.color.holo_green_dark));
                holder.checkedID.setText("4");
            }

        }
        else if (!USER.STUDENT) {

            holder.question.setText(questions.get(position).getQuestionText());
            holder.answerOption1.setText(question.getAnswerOption1());
            holder.answer1ID.setText(Integer.toString(question.getAnswerOption1ID()));
            holder.answerOption2.setText(question.getAnswerOption2());
            holder.answer2ID.setText(Integer.toString(question.getAnswerOption2ID()));
            holder.answerOption3.setText(question.getAnswerOption3());
            holder.answer3ID.setText(Integer.toString(question.getAnswerOption3ID()));
            holder.answerOption4.setText(question.getAnswerOption4());
            holder.answer4ID.setText(Integer.toString(question.getAnswerOption4ID()));
            holder.questionMarks.setText("(" + question.getQuestionMarkAlloc() + ")");

            String[] questNo = question.getQuestionID().split("-");
            holder.questionNo.setText(questNo[1] + ".");

            if (holder.answerOption1.getText().toString().equals(question.getCorrectOption())) {
                holder.answerOption1.setChecked(true);
                holder.checkedID.setText("1");
            } else if (holder.answerOption2.getText().toString().equals(question.getCorrectOption())) {
                holder.answerOption2.setChecked(true);
                holder.checkedID.setText("2");
            } else if (holder.answerOption3.getText().toString().equals(question.getCorrectOption())) {
                holder.answerOption3.setChecked(true);
                holder.checkedID.setText("3");
            } else {
                holder.answerOption4.setChecked(true);
                holder.checkedID.setText("4");
            }
        }
        else {
            holder.question.setText(questions.get(position).getQuestionText());
            holder.answerOption1.setText(question.getAnswerOption2());
            holder.answer1ID.setText(Integer.toString(question.getAnswerOption1ID()));
            holder.answerOption2.setText(question.getAnswerOption2());
            holder.answer2ID.setText(Integer.toString(question.getAnswerOption2ID()));
            holder.answerOption3.setText(question.getAnswerOption3());
            holder.answer3ID.setText(Integer.toString(question.getAnswerOption3ID()));
            holder.answerOption4.setText(question.getAnswerOption4());
            holder.answer4ID.setText(Integer.toString(question.getAnswerOption4ID()));
            holder.questionMarks.setText("(" + question.getQuestionMarkAlloc() + ")");
            String[] questNo = question.getQuestionID().split("-");
            holder.questionNo.setText(questNo[1] + ".");
            holder.answerOption1.setChecked(false);
            holder.answerOption2.setChecked(false);
            holder.answerOption3.setChecked(false);
            holder.answerOption4.setChecked(false);

            holder.rgAnswerOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int radioButtonID = group.getCheckedRadioButtonId();
                    View radioButton = group.findViewById(radioButtonID);
                    if (radioButton.getId() == holder.answerOption1.getId()) {
                        holder.answerOption1.setChecked(true);

                    }
                    if (radioButton.getId() == holder.answerOption2.getId()) {
                        holder.answerOption2.setChecked(true);

                    }
                    if (radioButton.getId() == holder.answerOption3.getId()) {
                        holder.answerOption3.setChecked(true);

                    }
                    if (radioButton.getId() == holder.answerOption4.getId()) {
                        holder.answerOption4.setChecked(true);
                    }
                }
            });
        }
    }

    @Override
    @Generated
    public int getItemCount() {
        return questions.size();
    }
    public ArrayList<QuestionV> getAll(){
        return questions;
    }

    @Generated
    class ViewHolder extends RecyclerView.ViewHolder {
        //Views
        public TextView question;
        public RadioGroup rgAnswerOptions;
        public TextView questionNo;
        public TextView questionMarks;
        public TextView checkedID;
        public RadioButton answerOption1;
        public RadioButton answerOption2;
        public RadioButton answerOption3;
        public RadioButton answerOption4;
        public TextView answer1ID;
        public TextView answer2ID;
        public TextView answer3ID;
        public TextView answer4ID;
        String ans = "";
        SparseBooleanArray checkedItems = new SparseBooleanArray();


        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            questionNo = (TextView) itemView.findViewById(R.id.questionNO);
            questionMarks = (TextView) itemView.findViewById(R.id.questionMarks);
            rgAnswerOptions = (RadioGroup) itemView.findViewById(R.id.rgAnswers);
            checkedID = (TextView) itemView.findViewById(R.id.correctAnswerID);
            answerOption1 = (RadioButton) itemView.findViewById(R.id.option1);
            answerOption2 = (RadioButton) itemView.findViewById(R.id.option2);
            answerOption3 = (RadioButton) itemView.findViewById(R.id.option3);
            answerOption4 = (RadioButton) itemView.findViewById(R.id.option4);

            answer1ID = (TextView) itemView.findViewById(R.id.Answer1ID);
            answer2ID = (TextView) itemView.findViewById(R.id.Answer2ID);
            answer3ID = (TextView) itemView.findViewById(R.id.Answer3ID);
            answer4ID = (TextView) itemView.findViewById(R.id.Answer4ID);

            //disable radio buttons if an instructor
            if (!USER.STUDENT){
                answerOption1.setEnabled(false);
                answerOption1.setTextColor(ContextCompat.getColor(context,android.R.color.black));
                answerOption2.setEnabled(false);
                answerOption2.setTextColor(ContextCompat.getColor(context,android.R.color.black));
                answerOption3.setEnabled(false);
                answerOption3.setTextColor(ContextCompat.getColor(context,android.R.color.black));
                answerOption4.setEnabled(false);
                answerOption4.setTextColor(ContextCompat.getColor(context,android.R.color.black));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                @Generated
                public void onClick(View v) {
                    if (QUIZ.VISIBILITY == 0){
                        String ansNum = checkedID.getText().toString();
                        String a1ID = answer1ID.getText().toString();
                        String a2ID = answer2ID.getText().toString();
                        String a3ID = answer3ID.getText().toString();
                        String a4ID = answer4ID.getText().toString();
                        createNewQuestionDialog(answerOption1,answerOption2,answerOption3,answerOption4,question,
                                questionMarks,questionNo,ansNum,a1ID,a2ID,a3ID,a4ID);
                    }

                }
            });
            answerOption1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(QuestionV Quest:questions ){
                        if(Quest.getQuestionText().equals( question.getText() )){
                            Quest.setSelcted_ans("1");
                            answerOption1.setChecked( true );
                            Quest.setOpt1_selected( true );
                        }
                    }

                }
            } );
            answerOption2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(QuestionV Quest:questions ){
                        if(Quest.getQuestionText().equals( question.getText() )){
                            Quest.setSelcted_ans("2");
                            answerOption2.setChecked( true );
                            Quest.setOpt2_selected( true );
                        }
                    }
                }
            } );
            answerOption3.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(QuestionV Quest:questions ){
                        if(Quest.getQuestionText().equals( question.getText() )){
                            Quest.setSelcted_ans("3");
                            answerOption3.setChecked( true );
                            Quest.setOpt3_selected( true );
                        }
                    }
                }
            } );
            answerOption4.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(QuestionV Quest:questions ){
                        if(Quest.getQuestionText().equals( question.getText() )){
                            Quest.setSelcted_ans("4");
                            answerOption4.setChecked( true );
                            Quest.setOpt4_selected( true );
                        }
                    }
                }
            } );


        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    public ArrayList<QuestionV> getSelected(){
        ArrayList<QuestionV> selected = new ArrayList<>();
        return selected;
    }
    @Generated
    public void createNewQuestionDialog(RadioButton answer1, RadioButton answer2,RadioButton answer3,RadioButton answer4, TextView questionText,
                                        TextView questionMarks, TextView questionNo, String correctAns, String a1ID, String a2ID,
                                        String a3ID, String a4ID) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View viewPopUp = LayoutInflater.from(context)
                .inflate(R.layout.add_question_dialog, null);

        dialogBuilder.setView(viewPopUp);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        String ans1 = answer1.getText().toString();
        String ans2 = answer2.getText().toString();
        String ans3 = answer3.getText().toString();
        String ans4 = answer4.getText().toString();
        String qtext = questionText.getText().toString();
        String qmark = questionMarks.getText().toString();
        String mark = qmark.substring(1,qmark.length()-1);
        String qnum =questionNo.getText().toString();
        String qno = qnum.substring(0,qnum.length()-1);
        int correctAnsNum = Integer.parseInt(correctAns);
        String answerOptions[] ={"Select answer","Answer option 1","Answer option 2","Answer option 3"," Answer option 4"};

        dialogUpdateQuestion = (Button) viewPopUp.findViewById(R.id.dialogAddQuestion);
        dialogUpdateQuestion.setText("Update");
        question = (TextInputLayout) viewPopUp.findViewById(R.id.dialogQuestionText);
        question.getEditText().setText(qtext);
        answerOption1 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption1);
        answerOption1.getEditText().setText(ans1);
        answerOption2 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption2);
        answerOption2.getEditText().setText(ans2);
        answerOption3 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption3);
        answerOption3.getEditText().setText(ans3);
        answerOption4 = (TextInputLayout) viewPopUp.findViewById(R.id.dialogOption4);
        answerOption4.getEditText().setText(ans4);
        markAlloc = (TextInputLayout) viewPopUp.findViewById(R.id.dialogMarkAlloc);
        markAlloc.getEditText().setText(mark);
        spinner = (Spinner)viewPopUp.findViewById(R.id.spCorrectAnswer);
        cancel = (Button) viewPopUp.findViewById(R.id.cancelAddQuestion);
        ArrayAdapter<String> adapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item,answerOptions){

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
        correctAnswer = correctAnsNum;
        spinner.setSelection(correctAnsNum,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            @Generated
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                correctAnswer = position;
            }

            @Override
            @Generated
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogUpdateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                isEmpty(question);
                isEmpty(answerOption1);
                isEmpty(answerOption2);
                isEmpty(answerOption3);
                isEmpty(answerOption4);
                invalidMark(markAlloc);
                if(!isEmpty(question) && !isEmpty(answerOption1)&& !isEmpty(answerOption2)&&!isEmpty(answerOption3)&&!isEmpty(answerOption4) && !invalidMark(markAlloc)){
                    String quest = question.getEditText().getText().toString();
                    String ans1 = answerOption1.getEditText().getText().toString();
                    String ans2 = answerOption2.getEditText().getText().toString();
                    String ans3 = answerOption3.getEditText().getText().toString();
                    String ans4 = answerOption4.getEditText().getText().toString();
                    String mark = markAlloc.getEditText().getText().toString();

                    try {
                        // Toast.makeText(context,QUIZ.ID+"-"+qnum, Toast.LENGTH_LONG).show();
                        //  Toast.makeText(context,QUIZ.ID+"-"+qno, Toast.LENGTH_LONG).show();
                        updateQuestion("updateQuestion.php",quest,ans1,ans2,ans3,ans4,correctAnswer,mark,qno,a1ID,a2ID,a3ID,a4ID);
                        dialog.dismiss();
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
    private void updateQuestion (String phpFile,String question,String answer1,String answer2,String answer3,String answer4, int answerCorrect,
                                 String mark, String qno, String a1ID, String a2ID, String a3ID,String a4ID) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/" + phpFile).newBuilder();
        urlBuilder.addQueryParameter("quizId", Integer.toString(QUIZ.ID));
        urlBuilder.addQueryParameter("questMark", mark);
        urlBuilder.addQueryParameter("questId", QUIZ.ID+"-"+qno);
        urlBuilder.addQueryParameter("text", question);
        urlBuilder.addQueryParameter("option1",answer1);
        urlBuilder.addQueryParameter("option2",answer2);
        urlBuilder.addQueryParameter("option3",answer3);
        urlBuilder.addQueryParameter("option4",answer4);
        urlBuilder.addQueryParameter("option1Id",a1ID);
        urlBuilder.addQueryParameter("option2Id",a2ID);
        urlBuilder.addQueryParameter("option3Id",a3ID);
        urlBuilder.addQueryParameter("option4Id",a4ID);
        urlBuilder.addQueryParameter("correctAnswer",Integer.toString(answerCorrect));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Activity cont = (Activity) context;

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
                cont.runOnUiThread(new Runnable() {
                    @Override
                    @Generated
                    public void run() {
                        Log.d("HERE",responseData.toString());
                        if(responseData.trim().equals("Successful")) {
                            Toast toast = Toast.makeText(context,"Successful", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(context, BrowseQuizQuestions.class);
                            cont.startActivity(intent);
                            cont.finish();
                        }
                        else{
                            Toast toast = Toast.makeText(context, "Couldn't update your question ", Toast.LENGTH_LONG);
                            toast.show();
                        }
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

    @RequiresApi(api = Build.VERSION_CODES.P)
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
                    Toast.makeText(context, "Student's answers were not found", Toast.LENGTH_SHORT).show();
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}