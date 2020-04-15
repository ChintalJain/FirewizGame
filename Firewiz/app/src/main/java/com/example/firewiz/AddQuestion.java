package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.firewiz.model.Question;
import com.example.firewiz.model.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class AddQuestion extends AppCompatActivity {
    private Quiz quiz;
    private Question question;
    private long quizID=0,questionID=0;
    EditText que,opt1,opt2,opt3,opt4;
    Button nq,endQuiz;
    RadioGroup correctAnswer;
    DatabaseReference quizRef,questionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        quizRef=FirebaseDatabase.getInstance().getReference().child("Quiz");
        questionRef=FirebaseDatabase.getInstance().getReference().child("Question");
        quizRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    quizID=dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        this.quiz=new Quiz();
        quiz.setQuizName(getIntent().getStringExtra("qn"));
        quiz.setDescription(getIntent().getStringExtra("qd"));
        quiz.setDuration(Integer.parseInt(getIntent().getStringExtra("duration")));
        //quiz.setUserID(getIntent().getStringExtra("uid"));

        que=(EditText)findViewById(R.id.question);
        opt1=(EditText)findViewById(R.id.opt1);
        opt2=(EditText)findViewById(R.id.opt2);
        opt3=(EditText)findViewById(R.id.opt3);
        opt4=(EditText)findViewById(R.id.opt4);
        nq=(Button)findViewById(R.id.nextQuestion);
        endQuiz=(Button)findViewById(R.id.endQuiz);
        correctAnswer=(RadioGroup)findViewById(R.id.correctAnswer);
        endQuiz.setEnabled(false);

        nq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionToQuiz();
                que.requestFocus();
            }
        });

        endQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quiz.getQuestions().isEmpty())
                {
                    Toast.makeText(AddQuestion.this, "Add atleast 1 question.", Toast.LENGTH_SHORT).show();
                }
                else {
                    addQuizToDB();
                    finish();
                    return;
                }
            }
        });
    }

    private void addQuestionToQuiz()
    {
        int optionRBId=correctAnswer.getCheckedRadioButtonId();

        if(TextUtils.isEmpty(que.getText().toString()))
        {
            que.setError("Please Enter Question");
            que.requestFocus();
        }
        else if (TextUtils.isEmpty(opt1.getText().toString()))
        {
            opt1.setError("Please Enter Option1");
            opt1.requestFocus();
        }
        else if (TextUtils.isEmpty(opt2.getText().toString()))
        {
            opt2.setError("Please Enter Option2");
            opt2.requestFocus();
        }
        else if (TextUtils.isEmpty(opt3.getText().toString()))
        {
            opt3.setError("Please Enter Option3");
            opt3.requestFocus();
        }
        else if (TextUtils.isEmpty(opt4.getText().toString()))
        {
            opt4.setError("Please Enter Option4");
            opt4.requestFocus();
        }
        else if(optionRBId==-1)
        {
            Toast.makeText(AddQuestion.this, "Please select correct option.", Toast.LENGTH_SHORT).show();
        }
        else {
            question = new Question();
            question.setQuestion(que.getText().toString());
            question.setOption1(opt1.getText().toString());
            question.setOption2(opt2.getText().toString());
            question.setOption3(opt3.getText().toString());
            question.setOption4(opt4.getText().toString());
            if (((RadioButton)findViewById(R.id.opt1RB)).isChecked()) {
                question.setCorrectAnswer(question.getOption1());
            } else if (((RadioButton)findViewById(R.id.opt2RB)).isChecked()) {
                question.setCorrectAnswer(question.getOption2());
            } else if (((RadioButton)findViewById(R.id.opt3RB)).isChecked()) {
                question.setCorrectAnswer(question.getOption3());
            } else {
                question.setCorrectAnswer(question.getOption4());
            }
            if(quiz.findQuestion(question)==-1) {
                quiz.addQuestion(question);
            }
            else
            {
                Toast.makeText(this, "This Question is Already added..", Toast.LENGTH_SHORT).show();
            }
            que.setText("");
            opt1.setText("");
            opt2.setText("");
            opt3.setText("");
            opt4.setText("");
            ((RadioButton) findViewById(correctAnswer.getCheckedRadioButtonId())).setChecked(false);
            endQuiz.setEnabled(true);
        }
    }
    protected void addQuizToDB()
    {
        quiz.setTotalQuestion();
        int size=this.quiz.getTotalQuestion();
        int totalQuestion=0;
        String msg=Integer.toString(size);
        Log.d("Quiz",msg);

        ((ProgressBar)findViewById(R.id.endQuizPB)).setVisibility(View.VISIBLE);

        ArrayList<Question>questionArrayList=quiz.getQuestions();

        for (Iterator<Question> it = questionArrayList.iterator(); it.hasNext(); ) {
            totalQuestion++;
            Question q = it.next();
            questionRef.child(String.valueOf(quizID+1)).child(String.valueOf(totalQuestion)).setValue(q);
        }
        quizID++;
        quizRef.child(String.valueOf(quizID)).child("quizName").setValue(this.quiz.getQuizName());
        quizRef.child(String.valueOf(quizID)).child("description").setValue(this.quiz.getDescription());
        quizRef.child(String.valueOf(quizID)).child("duration").setValue(this.quiz.getDuration());
        quizRef.child(String.valueOf(quizID)).child("totalQuestion").setValue(this.quiz.getTotalQuestion())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((ProgressBar)findViewById(R.id.endQuizPB)).setVisibility(View.INVISIBLE);
                    }
                });
    }
}
