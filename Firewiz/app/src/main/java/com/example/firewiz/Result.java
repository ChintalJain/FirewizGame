package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Result extends AppCompatActivity {

    TextView t1,t2,t3;
    DatabaseReference quizPlayed;
    private long quizPlayedId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d("Result",getIntent().getStringExtra("uid"));
        final String uid=getIntent().getStringExtra("uid");
        final String qid=getIntent().getStringExtra("qid");
        quizPlayed=FirebaseDatabase.getInstance().getReference().child("QuizPlayed").child(uid);
        t1=(TextView)findViewById(R.id.textView4);
        t2=(TextView)findViewById(R.id.textView5);
        t3=(TextView)findViewById(R.id.textView6);

        Intent i=getIntent();
        final String questions=i.getStringExtra("total");
        final String correct=i.getStringExtra("correct");
        final String wrong=i.getStringExtra("incorrect");

        t1.setText(questions);
        t2.setText(correct);
        t3.setText(wrong);
        quizPlayed.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizPlayedId=dataSnapshot.getChildrenCount();
                Log.d("Result", String.valueOf(quizPlayedId));
                quizPlayedId++;
                Log.d("Result",String.valueOf(quizPlayedId));
                quizPlayed.child(String.valueOf(quizPlayedId)).child("TotalQuestion").setValue(questions);
                quizPlayed.child(String.valueOf(quizPlayedId)).child("Correct").setValue(correct);
                quizPlayed.child(String.valueOf(quizPlayedId)).child("Wrong").setValue(wrong);
                quizPlayed.child(String.valueOf(quizPlayedId)).child("QuizId").setValue(qid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //addResultToDb(questions,correct,wrong,qid);
    }
    private void addResultToDb(String questions,String correct,String wrong,String qid)
    {
        //quizPlayedId=Integer.parseInt(((TextView)findViewById(R.id.playedQuiz)).getText().toString());
        Log.d("Result",String.valueOf(quizPlayedId));
        quizPlayed.child(String.valueOf(quizPlayedId)).child("TotalQuestion").setValue(questions);
        quizPlayed.child(String.valueOf(quizPlayedId)).child("Correct").setValue(correct);
        quizPlayed.child(String.valueOf(quizPlayedId)).child("Wrong").setValue(wrong);
        quizPlayed.child(String.valueOf(quizPlayedId)).child("QuizId").setValue(qid);
    }
}
