package com.example.witsonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionCardAdapter extends RecyclerView.Adapter<QuestionCardAdapter.ViewHolder> {
    private Context context;
    ArrayList<Question> questions; //List to store all Courses
    //For determining if student is a tutor


    private ProgressBar progressBarReq;


    //Constructor of this class
    public QuestionCardAdapter(ArrayList<Question> questions, Context context) {
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

    @Override
    @Generated
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Getting the particular item from the list
        final Question question= questions.get(position);
        if(!USER.STUDENT){


            holder.question.setText(questions.get(position).getQuestionText());
            holder.answerOption1.setText(question.getAnswerOption1());
            holder.answerOption2.setText(question.getAnswerOption2());
            holder.answerOption3.setText(question.getAnswerOption3());
            holder.answerOption4.setText(question.getAnswerOption4());
            holder.questionMarks.setText("(" + question.getQuestionMarkAlloc() + ")");
            String[] questNo = question.getQuestionID().split("-");
            holder.questionNo.setText(questNo[1] + ".");
            if(holder.answerOption1.getText().toString().equals(question.getCorrectOption())){
                holder.answerOption1.setChecked(true);
            }
            else if(holder.answerOption2.getText().toString().equals(question.getCorrectOption())){
                holder.answerOption2.setChecked(true);
            }
            else if(holder.answerOption3.getText().toString().equals(question.getCorrectOption())){
                holder.answerOption3.setChecked(true);
            }
            else{
                holder.answerOption4.setChecked(true);
            }
        }
        else{
            holder.question.setText(questions.get(position).getQuestionText());
            holder.answerOption1.setText(question.getAnswerOption1());
            holder.answerOption2.setText(question.getAnswerOption2());
            holder.answerOption3.setText(question.getAnswerOption3());
            holder.answerOption4.setText(question.getAnswerOption4());
            holder.questionMarks.setText("(" + question.getQuestionMarkAlloc() + ")");
            String[] questNo = question.getQuestionID().split("-");
            holder.questionNo.setText(questNo[1] + ".");

        }
    }


    @Override
    @Generated
    public int getItemCount() {
        return questions.size();
    }

    @Generated
    class ViewHolder extends RecyclerView.ViewHolder {
        //Views
        public TextView question;
        public RadioGroup rgAnswerOptions;
        public TextView questionNo;
        public TextView questionMarks;
        public RadioButton answerOption1;
        public RadioButton answerOption2;
        public RadioButton answerOption3;
        public RadioButton answerOption4;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            questionNo = (TextView) itemView.findViewById(R.id.questionNO);
            questionMarks = (TextView) itemView.findViewById(R.id.questionMarks);
            rgAnswerOptions = (RadioGroup) itemView.findViewById(R.id.rgAnswers);
            answerOption1 = (RadioButton) itemView.findViewById(R.id.option1);
            answerOption2 = (RadioButton) itemView.findViewById(R.id.option2);
            answerOption3 = (RadioButton) itemView.findViewById(R.id.option3);
            answerOption4 = (RadioButton) itemView.findViewById(R.id.option4);

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
            else{
                rgAnswerOptions.clearCheck();
            }

        }
    }


}
