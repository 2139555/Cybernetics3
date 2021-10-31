package com.example.witsonline;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionVTest {

    @Test
    public void isOpt1_selected() {
        QuestionV temp = new QuestionV();
        assertEquals(false,temp.isOpt1_selected());
    }

    @Test
    public void setOpt1_selected() {
        QuestionV temp = new QuestionV();
        temp.setOpt1_selected(true);
        assertEquals(true,temp.isOpt1_selected());
    }

    @Test
    public void isOpt2_selected() {
        QuestionV temp = new QuestionV();
        assertEquals(false,temp.isOpt2_selected());
    }

    @Test
    public void setOpt2_selected() {
        QuestionV temp = new QuestionV();
        temp.setOpt2_selected(true);
        assertEquals(true,temp.isOpt2_selected());
    }

    @Test
    public void isOpt3_selected() {
        QuestionV temp = new QuestionV();
        assertEquals(false,temp.isOpt3_selected());
    }

    @Test
    public void setOpt3_selected() {
        QuestionV temp = new QuestionV();
        temp.setOpt3_selected(true);
        assertEquals(true,temp.isOpt3_selected());
    }

    @Test
    public void isOpt4_selected() {
        QuestionV temp = new QuestionV();
        assertEquals(false,temp.isOpt4_selected());
    }

    @Test
    public void setOpt4_selected() {
        QuestionV temp = new QuestionV();
        temp.setOpt4_selected(true);
        assertEquals(true,temp.isOpt4_selected());
    }

    @Test
    public void getSelcted_ans() {
        QuestionV temp = new QuestionV();
        assertEquals("",temp.getSelcted_ans());
    }

    @Test
    public void setSelcted_ans() {
        QuestionV temp = new QuestionV();
        temp.setSelcted_ans("1");
        assertEquals("1",temp.getSelcted_ans());
    }

    @Test
    public void setSelectedAnsText() {
        QuestionV temp = new QuestionV();
        temp.setSelectedAnsText("1");
        assertEquals("1",temp.getSelectedAnswerText());
    }

    @Test
    public void getSelectedAnswerText() {
        QuestionV temp = new QuestionV();
        assertEquals("",temp.getSelectedAnswerText());
    }

    @Test
    public void setQuestionID() {
        QuestionV temp = new QuestionV();
        temp.setQuestionID("test");
        assertEquals("test",temp.getQuestionID());
    }

    @Test
    public void setQuestionQuiz() {
        QuestionV temp = new QuestionV();
        temp.setQuestionQuiz(1);
        assertEquals(1,temp.getQuestionQuiz());
    }

    @Test
    public void setQuestionMarkAlloc() {
        QuestionV temp = new QuestionV();
        temp.setQuestionMarkAlloc(1);
        assertEquals(1,temp.getQuestionMarkAlloc());
    }

    @Test
    public void setQuestionText() {
        QuestionV temp = new QuestionV();
        temp.setQuestionText("test");
        assertEquals("test",temp.getQuestionText());
    }

    @Test
    public void setAnswerOption1() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption1("test");
        assertEquals("test",temp.getAnswerOption1());
    }

    @Test
    public void setAnswerOption2() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption2("test");
        assertEquals("test",temp.getAnswerOption2());
    }

    @Test
    public void setAnswerOption3() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption3("test");
        assertEquals("test",temp.getAnswerOption3());
    }

    @Test
    public void setAnswerOption4() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption4("test");
        assertEquals("test",temp.getAnswerOption4());
    }

    @Test
    public void setCorrectOption() {
        QuestionV temp = new QuestionV();
        temp.setCorrectOption("test");
        assertEquals("test",temp.getCorrectOption());
    }

    @Test
    public void setAnswerOption1ID() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption1ID(1);
        assertEquals(1,temp.getAnswerOption1ID());
    }

    @Test
    public void setAnswerOption2ID() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption2ID(1);
        assertEquals(1,temp.getAnswerOption2ID());
    }

    @Test
    public void setAnswerOption3ID() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption3ID(1);
        assertEquals(1,temp.getAnswerOption3ID());
    }

    @Test
    public void setAnswerOption4ID() {
        QuestionV temp = new QuestionV();
        temp.setAnswerOption4ID(1);
        assertEquals(1,temp.getAnswerOption4ID());
    }

    @Test
    public void getQuestionID() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getQuestionID());
    }

    @Test
    public void getQuestionQuiz() {
        QuestionV temp = new QuestionV();
        assertEquals(0,temp.getQuestionQuiz());
    }

    @Test
    public void getQuestionMarkAlloc() {
        QuestionV temp = new QuestionV();
        assertEquals(0,temp.getQuestionMarkAlloc());
    }

    @Test
    public void getQuestionText() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getQuestionText());
    }

    @Test
    public void getAnswerOption1() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getAnswerOption1());
    }

    @Test
    public void getAnswerOption2() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getAnswerOption2());
    }

    @Test
    public void getAnswerOption3() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getAnswerOption3());
    }

    @Test
    public void getAnswerOption4() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getAnswerOption4());
    }

    @Test
    public void getCorrectOption() {
        QuestionV temp = new QuestionV();
        assertEquals(null,temp.getCorrectOption());

    }

    @Test
    public void getAnswerOption1ID() {
        QuestionV temp = new QuestionV();
        assertEquals(0,temp.getAnswerOption1ID());
    }

    @Test
    public void getAnswerOption2ID() {
        QuestionV temp = new QuestionV();
        assertEquals(0,temp.getAnswerOption2ID());
    }

    @Test
    public void getAnswerOption3ID() {
        QuestionV temp = new QuestionV();
        assertEquals(0,temp.getAnswerOption3ID());
    }

    @Test
    public void getAnswerOption4ID() {
        QuestionV temp = new QuestionV();
        assertEquals(0,temp.getAnswerOption4ID());
    }
}