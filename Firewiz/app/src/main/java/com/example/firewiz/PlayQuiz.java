package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firewiz.model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayQuiz extends AppCompatActivity {
    Button b1,b2,b3,b4;
    TextView t1_question,timerTxt;

    int totalQuestion,curQue=0,correct=0,wrong=0,dur;

    DatabaseReference quizRef,questionRef,dbreference;
    private FirebaseUser user=null;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        if(user==null)
        {
            Toast.makeText(PlayQuiz.this,"Please Login First", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        quizRef=FirebaseDatabase.getInstance().getReference().child("Quiz").child("1");
        questionRef=FirebaseDatabase.getInstance().getReference("Question").child("1");

        t1_question=(TextView)findViewById(R.id.questionsText);
        timerTxt=(TextView)findViewById(R.id.timerTxt);
        quizRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dur=(Integer.parseInt(dataSnapshot.child("duration").getValue().toString()));
                reverseTimer(dur,timerTxt);
                Log.d("Durationm",String.valueOf(dur));
                totalQuestion=(Integer.parseInt(dataSnapshot.child("totalQuestion").getValue().toString()));
                updateQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void updateQuestion() {
        curQue++;
        if(curQue>totalQuestion)
        {
            Intent i=new Intent(this,Result.class);
            i.putExtra("total",String.valueOf(totalQuestion));
            i.putExtra("correct",String.valueOf(correct));
            i.putExtra("incorrect",String.valueOf(wrong));
            startActivityForResult(i,1);
        }
        else
        {
            dbreference= questionRef.child(String.valueOf(curQue));
            dbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final Question question=dataSnapshot.getValue(Question.class);
                    t1_question.setText(question.getQuestion());
                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b1.getText().toString().equals(question.getCorrectAnswer()))
                            {
                                Toast.makeText(getApplicationContext(),"Correct answer",Toast.LENGTH_SHORT);
                                b1.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Incorrect answer",Toast.LENGTH_SHORT);
                                wrong=wrong+1;
                                b1.setBackgroundColor(Color.RED);
                                if(b2.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);

                            }
                        }
                    });
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b2.getText().toString().equals(question.getCorrectAnswer()))
                            {
                                Toast.makeText(getApplicationContext(),"Correct answer",Toast.LENGTH_SHORT);
                                b2.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Incorrect answer",Toast.LENGTH_SHORT);
                                wrong=wrong+1;
                                b2.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);

                            }

                        }
                    });
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b3.getText().toString().equals(question.getCorrectAnswer()))
                            {
                                Toast.makeText(getApplicationContext(),"Correct answer",Toast.LENGTH_SHORT);
                                b3.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Incorrect answer",Toast.LENGTH_SHORT);
                                wrong=wrong+1;
                                b3.setBackgroundColor(Color.RED);
                                if(b2.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b1.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);

                            }
                        }
                    });
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b4.getText().toString().equals(question.getCorrectAnswer()))
                            {
                                Toast.makeText(getApplicationContext(),"Correct answer",Toast.LENGTH_SHORT);
                                b4.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Incorrect answer",Toast.LENGTH_SHORT);
                                wrong=wrong+1;
                                b4.setBackgroundColor(Color.RED);
                                if(b2.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b1.getText().toString().equals(question.getCorrectAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();

                                    }
                                },1500);
                            }
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void reverseTimer(int seconds,final TextView tv)
    {
        new CountDownTimer(seconds*1000+1000,1000){

            public void onTick(long millisUntilFinished){
                int seconds=(int)(millisUntilFinished/1000);
                int minutes=seconds/60;
                seconds=seconds%60;
                tv.setText(String.format("%02d",minutes)+":"+String.format("%02d",seconds));

            }
            public void onFinish()
            {
                tv.setText("Completed");
                Intent myIntent=new Intent(tv.getContext(),Result.class);
                myIntent.putExtra("total",String.valueOf(totalQuestion));
                myIntent.putExtra("correct",String.valueOf(correct));
                myIntent.putExtra("incorrect",String.valueOf(wrong));
                startActivityForResult(myIntent,1);
            }
        }.start();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            Log.d("Play Quiz","From Result");
            finish();
            return;
        }
    }
}
