package com.example.witsonline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.HashMap;
import java.util.HashSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuizCardAdapter extends RecyclerView.Adapter<QuizCardAdapter.ViewHolder> {
    private Context context;
    ArrayList<QuizV> QuizVs; //List to store all Courses
    //For determining if student is a tutor

    //Dialog
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ProgressBar progressBar;
    private Button btnAttempt, btnCancel;
    private TextView attemptText;
    private Button dialogCreateQuiz;
    private Button cancel;
    private Button dialogEditQuiz;
    private Button dialogViewQuiz;
    private TextInputLayout dialogQuizName;

    //Constructor of this class
    public QuizCardAdapter(ArrayList<QuizV> quizVs, Context context) {
        super();
        //Getting all requests
        this.QuizVs = quizVs;
        this.context = context;
    }

    @NonNull
    @Override
    @Generated
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    @Generated
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Getting the particular item from the list
        final QuizV quizV = QuizVs.get(position);

        //Showing data on the views
        holder.setIsRecyclable(false);
        holder.quizID.setText(quizV.getQuizID());
        holder.quizName.setText(quizV.getQuizName());
        if (quizV.getQuizMarkAlloc()==1){
            holder.quizMarkAlloc.setText(quizV.getQuizMarkAlloc()+" point");
        }
        else{
            holder.quizMarkAlloc.setText(quizV.getQuizMarkAlloc()+" points");
        }
        if (quizV.getQuizNumQuestions()==1){
            holder.quizNumQuestions.setText(quizV.getQuizNumQuestions() + " question");
        }
        else{
            holder.quizNumQuestions.setText(quizV.getQuizNumQuestions() + " questions");
        }

        holder.quizVisibility.setText(""+quizV.getQuizVisibility());
        if (USER.STUDENT){
            holder.quizEye.setVisibility(View.GONE);
        }
        else if (quizV.getQuizVisibility()==1){
            holder.quizEye.setImageResource(R.drawable.ic_baseline_visibility_24);
        }
        else{
            holder.quizEye.setImageResource(R.drawable.ic_baseline_visibility_off_24);
        }

    }

    @Override
    @Generated
    public int getItemCount() {
        return QuizVs.size();
    }

    @Generated
    class ViewHolder extends RecyclerView.ViewHolder {

        //Views
        public TextView quizID;
        public TextView quizName;
        public TextView quizMarkAlloc;
        public TextView quizNumQuestions;
        public ImageView quizEye;
        public TextView quizVisibility;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            quizID = (TextView) itemView.findViewById(R.id.quizID);
            quizName = (TextView) itemView.findViewById(R.id.cardQuizName);
            quizEye = (ImageView) itemView.findViewById(R.id.quizVisibility);
            quizMarkAlloc = (TextView) itemView.findViewById(R.id.quizMarkAlloc);
            quizNumQuestions = (TextView) itemView.findViewById(R.id.quizNumQuestions);
            quizVisibility = (TextView) itemView.findViewById(R.id.quizVisibilityText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                @Generated
                public void onClick(View view) {
                    QUIZ.ID = Integer.parseInt(quizID.getText().toString());
                    QUIZ.NAME = quizName.getText().toString();
                    QUIZ.VISIBILITY = Integer.parseInt(quizVisibility.getText().toString());

                    if(USER.STUDENT){
                        createNewAttemptDialog();
                        // i = new Intent(context,activity_attempt_quiz.class);
                        //context.startActivity(i);
                    }
                    else{
                        editViewQuizDialog();
                    }

                }
            });
        }
    }

    @Generated
    public void createNewAttemptDialog() {
        dialogBuilder = new AlertDialog.Builder(context);
        final View viewPopUp = LayoutInflater.from(context)
                .inflate(R.layout.student_quiz_dialog, null);

        attemptText = (TextView) viewPopUp.findViewById(R.id.attemptText);
        btnAttempt = (Button) viewPopUp.findViewById(R.id.AttemptQuiz);
        btnCancel = (Button) viewPopUp.findViewById(R.id.cancelAttemptQuiz);
        progressBar = (ProgressBar) viewPopUp.findViewById(R.id.progressBarQuizDialog);
        progressBar.setVisibility(View.VISIBLE);
        dialogBuilder.setView(viewPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        //check whether attempt was made
        try {
            doAttemptRequest("checkQuizAttempt.php");
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                if (btnAttempt.getText().toString().trim().equals("ATTEMPT")) {
                    //attempt quiz
                    Intent i = new Intent(context,activity_attempt_quiz.class);
                    context.startActivity(i);
                    dialog.dismiss();
                }
                else{
                    //view quiz feedback
                    Toast.makeText(context, "add quiz feedback intent here", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Generated
    private void doAttemptRequest(String phpFile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/" + phpFile).newBuilder();
        urlBuilder.addQueryParameter("studentNumber", USER.USERNAME);
        urlBuilder.addQueryParameter("quizID", Integer.toString(QUIZ.ID));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            private Activity cont = (Activity) context;

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
                        if (btnAttempt != null) {
                            if (responseData.trim().equals("attempt")) {
                                btnAttempt.setText("VIEW");
                                attemptText.setText("Do you want to view feedback for this quiz?");
                            }
                            if (progressBar != null) {
                                attemptText.setVisibility(View.VISIBLE);
                                btnAttempt.setVisibility(View.VISIBLE);
                                btnCancel.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                    }
                });
            }
        });
    }

    public void editQuizDialog() {
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        final View viewPopUp = LayoutInflater.from(context)
                .inflate(R.layout.create_quiz_dialog, null);

        dialogBuilder.setView(viewPopUp);
        androidx.appcompat.app.AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        dialogCreateQuiz = (Button) viewPopUp.findViewById(R.id.dialogCreateQuiz);
        dialogCreateQuiz.setText("EDIT");
        cancel = (Button) viewPopUp.findViewById(R.id.cancelQuizCreation);
        dialogQuizName = (TextInputLayout) viewPopUp.findViewById(R.id.dialogQuizName);
        dialogQuizName.getEditText().setText(QUIZ.NAME);

        dialogCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                String quizName = dialogQuizName.getEditText().getText().toString().trim();
                if (quizName.isEmpty()){
                    dialogQuizName.setError("Quiz name can't be empty");
                }
                else{
                    dialogQuizName.setError(null);
                    try {
                        editQuiz("updateQuizName.php",dialogQuizName.getEditText().getText().toString());
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

    public void editViewQuizDialog() {
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        final View viewPopUp = LayoutInflater.from(context)
                .inflate(R.layout.view_edit_quiz_dialog, null);

        dialogBuilder.setView(viewPopUp);
        androidx.appcompat.app.AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        dialogEditQuiz = (Button) viewPopUp.findViewById(R.id.editQuiz);
        dialogViewQuiz = (Button) viewPopUp.findViewById(R.id.viewQuiz);

        dialogEditQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            @Generated
            public void onClick(View v) {
                editQuizDialog();
                dialog.dismiss();
            }
        });

        dialogViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,BrowseQuizQuestions.class);
                context.startActivity(i);
                dialog.dismiss();
            }
        });
    }
   @Generated
    private void editQuiz (String editQuizPHP, String quizName) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2105624/" + editQuizPHP).newBuilder();
        urlBuilder.addQueryParameter("quizID", Integer.toString(QUIZ.ID));
        urlBuilder.addQueryParameter("quizName", quizName);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            private Activity cont = (Activity) context;
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
                            Intent intent = new Intent(context, QuizActivity.class);
                            context.startActivity(intent);
                        }
                        else{
                            Toast toast = Toast.makeText(context, "Couldn't edit your quiz ", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        });
    }
}
