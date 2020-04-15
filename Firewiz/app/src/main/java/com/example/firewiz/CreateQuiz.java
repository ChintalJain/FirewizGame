package com.example.firewiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class CreateQuiz extends AppCompatActivity {
    private FirebaseUser user=null;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        if(user==null)
        {
            Toast.makeText(CreateQuiz.this,"Please Login First", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        ((Button)findViewById(R.id.createQuiz)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quizName=((EditText)findViewById(R.id.quizName)).getText().toString();
                String des=((EditText)findViewById(R.id.quizDescription)).getText().toString();
                String dur=((EditText)findViewById(R.id.quizTimer)).getText().toString();
                Intent i=null;
                if(TextUtils.isEmpty(quizName))
                {
                    ((EditText)findViewById(R.id.quizName)).setError("Quiz Name is require..");
                    ((EditText)findViewById(R.id.quizName)).requestFocus();
                }
                else if(TextUtils.isEmpty(des))
                {
                    ((EditText)findViewById(R.id.quizDescription)).setError("Description is require..");
                    ((EditText)findViewById(R.id.quizDescription)).requestFocus();
                }
                else if(TextUtils.isEmpty(dur))
                {
                    ((EditText)findViewById(R.id.quizTimer)).setError("Time is required..");
                    ((EditText)findViewById(R.id.quizTimer)).requestFocus();
                }
                else if(!(Pattern.matches("\\d+",dur)) || dur.equals("0"))
                {
                    ((EditText)findViewById(R.id.quizTimer)).setError("Enter integer value..");
                    ((EditText)findViewById(R.id.quizTimer)).requestFocus();
                }
                else
                {
                    i=new Intent(getApplicationContext(),AddQuestion.class);
                    i.putExtra("qn",quizName);
                    i.putExtra("qd",des);
                    i.putExtra("duration", dur);
                    //i.putExtra("uid",user.getUid());
                    startActivityForResult(i,1);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            Log.d("Quiz","From Question");
            finish();
            return;
        }
    }
}
