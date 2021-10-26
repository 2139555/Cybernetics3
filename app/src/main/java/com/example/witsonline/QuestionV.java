package com.example.witsonline;

public class QuestionV{
    private String questionID;
    private int questionQuiz;
    private String questionText;
    private int questionMarkAlloc;
    private String answerOption1;
    private String answerOption2;
    private String answerOption3;
    private String answerOption4;
    private String correctOption;
    private int answerOption1ID;
    private int answerOption2ID;
    private int answerOption3ID;
    private int answerOption4ID;
    boolean opt1_selected = false;
    boolean opt2_selected = false;
    boolean opt3_selected = false;
    boolean opt4_selected = false;
    private String Selected_ans = "";
    private String selectedAnswerText = "";

    public boolean isOpt1_selected() {
        return opt1_selected;
    }

    public void setOpt1_selected(boolean opt1_selected) {
        this.opt1_selected = opt1_selected;
    }

    public boolean isOpt2_selected() {
        return opt2_selected;
    }

    public void setOpt2_selected(boolean opt2_selected) {
        this.opt2_selected = opt2_selected;
    }

    public boolean isOpt3_selected() {
        return opt3_selected;
    }

    public void setOpt3_selected(boolean opt3_selected) {
        this.opt3_selected = opt3_selected;
    }

    public boolean isOpt4_selected() {
        return opt4_selected;
    }

    public void setOpt4_selected(boolean opt4_selected) {
        this.opt4_selected = opt4_selected;
    }




    public String getSelcted_ans() {
        return Selected_ans;
    }

    public void setSelcted_ans(String selcted_ans) {
        Selected_ans = selcted_ans;
    }


    public void setSelectedAnsText(String ans) {selectedAnswerText = ans;}
    public String getSelectedAnswerText() { return selectedAnswerText; }




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
    void setAnswerOption1ID(int answerId){
        this.answerOption1ID = answerId;
    }
    void setAnswerOption2ID(int answerId){
        this.answerOption2ID = answerId;
    }
    void setAnswerOption3ID(int answerId){
        this.answerOption3ID = answerId;
    }
    void setAnswerOption4ID(int answerId){
        this.answerOption4ID = answerId;
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
    int getAnswerOption1ID(){
        return this.answerOption1ID;
    }
    int getAnswerOption2ID(){
        return this.answerOption2ID;
    }
    int getAnswerOption3ID(){
        return this.answerOption3ID;
    }
    int getAnswerOption4ID(){
        return this.answerOption4ID;
    }

}
