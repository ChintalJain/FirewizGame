package com.example.firewiz.model;

import android.util.Log;

import java.util.ArrayList;

public class Quiz {
    private String quizName,description,userID;
    private ArrayList<Question> questions;
    private int duration,totalQuestion;

    public Quiz(String quizName,String description,ArrayList<Question> questions,int duration)
    {
        this.quizName=quizName;
        this.description=description;
        this.questions=questions;
        this.duration=duration;
        this.totalQuestion=0;
    }
    public Quiz()
    {
        this.questions=new ArrayList<Question>();
        this.totalQuestion=0;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion() {
        this.totalQuestion=this.questions.size();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
    public void addQuestion(Question q)
    {
        this.questions.add(q);
    }
    public int findQuestion(Question q)
    {
        return questions.indexOf(q);
    }
}
