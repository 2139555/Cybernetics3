package com.example.witsonline;

public class Question{
    private String questionID;
    private int questionQuiz;
    private String questionText;
    private int questionMarkAlloc;
    private String answerOption1;
    private String answerOption2;
    private String answerOption3;
    private String answerOption4;
    private String correctOption;


    //Setters
    void setQuestionID(String id){
        this.questionID = id;
    }
    void setQuestionQuiz(int quiz){
        this.questionQuiz = quiz;
    }
    void setQuestionMarkAlloc(int mark){
        this.questionMarkAlloc = mark;
    }
    void setQuestionText(String text){
        this.questionText = text;
    }
    void setAnswerOption1(String answer1){
        this.answerOption1 = answer1;
    }
    void setAnswerOption2(String answer2){
        this.answerOption2 = answer2;
    }
    void setAnswerOption3(String answer3){
        this.answerOption3 = answer3;
    }
    void setAnswerOption4(String answer4){
        this.answerOption4 = answer4;
    }
    void  setCorrectOption(String answer){
        this.correctOption =answer;
    }
    //Getters
    String getQuestionID(){
        return this.questionID;
    }
    int getQuestionQuiz(){
        return this.questionQuiz;
    }
    int getQuestionMarkAlloc(){
        return this.questionMarkAlloc;
    }
    String getQuestionText(){
        return this.questionText;
    }
    String getAnswerOption1(){
        return  this.answerOption1;
    }
    String getAnswerOption2(){
        return this.answerOption2;
    }
    String getAnswerOption3(){
        return this.answerOption3;
    }
    String getAnswerOption4(){
        return  this.answerOption4;
    }
    String getCorrectOption(){
        return this.correctOption;
    }

}
