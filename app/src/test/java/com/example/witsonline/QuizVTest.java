package com.example.witsonline;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuizVTest {

    @Test
    public void setQuizID() {
        QuizV temp =new QuizV();
        String expected="test";
        temp.setQuizID("test");
        assertEquals(expected,temp.getQuizID());
    }

    @Test
    public void setQuizName() {
        QuizV temp =new QuizV();
        String expected="test";
        temp.setQuizName("test");
        assertEquals(expected,temp.getQuizName());
    }

    @Test
    public void setQuizMarkAlloc() {
        QuizV temp =new QuizV();
        int expected=1;
        temp.setQuizMarkAlloc(1);
        assertEquals(expected,temp.getQuizMarkAlloc());
    }

    @Test
    public void setQuizNumQuestions() {
        QuizV temp =new QuizV();
        int expected=1;
        temp.setQuizNumQuestions(1);
        assertEquals(expected,temp.getQuizNumQuestions());
    }

    @Test
    public void setQuizVisibility() {
        QuizV temp =new QuizV();
        int expected=1;
        temp.setQuizVisibility(1);
        assertEquals(expected,temp.getQuizVisibility());
    }

    @Test
    public void getQuizID() {
        QuizV temp =new QuizV();
        assertEquals(null,temp.getQuizID());
    }

    @Test
    public void getQuizName() {
        QuizV temp =new QuizV();
        assertEquals(null,temp.getQuizName());
    }

    @Test
    public void getQuizMarkAlloc() {
        QuizV temp =new QuizV();
        assertEquals(0,temp.getQuizMarkAlloc());
    }

    @Test
    public void getQuizNumQuestions() {
        QuizV temp =new QuizV();
        assertEquals(0,temp.getQuizNumQuestions());
    }

    @Test
    public void getQuizVisibility() {
        QuizV temp =new QuizV();
        assertEquals(0,temp.getQuizVisibility());
    }
}