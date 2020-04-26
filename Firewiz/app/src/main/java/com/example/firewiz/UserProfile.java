package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class UserProfile extends AppCompatActivity {

    private FirebaseUser user=null;
    private FirebaseAuth firebaseAuth;

    DatabaseReference userDetails=null,quizPlayed,quiz;
    //private Button b1,b2,b3;

    @Override
    protected void onStart() {
        super.onStart();
        if(checkUserSignIn())
        {
            changeLoginText("Update");
            changeSignupText("Sign Out");
            userDetails=FirebaseDatabase.getInstance().getReference().child("User_Details").child(user.getUid());
            userDetails.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fullname,userName,email,dob;
                    fullname=dataSnapshot.child("fullName").getValue().toString();
                    userName=dataSnapshot.child("userName").getValue().toString();
                    email=dataSnapshot.child("email").getValue().toString();
                    dob=dataSnapshot.child("dateOfBirth").getValue().toString();
                    ((TextView)findViewById(R.id.fullName)).setText(email);
                    ((TextView)findViewById(R.id.userName)).setText(userName);
                    ((EditText)findViewById(R.id.upUsername)).setText(userName);
                    ((EditText)findViewById(R.id.upFullName)).setText(fullname);
                    ((EditText)findViewById(R.id.upDob)).setText(dob);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            quizPlayed=FirebaseDatabase.getInstance().getReference().child("QuizPlayed").child(user.getUid());
            quizPlayed.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ((TextView)findViewById(R.id.quiz)).setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            quiz=FirebaseDatabase.getInstance().getReference().child("Quiz");
            quiz.orderByChild("userId").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ((TextView)findViewById(R.id.quiz3)).setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            changeLoginText("Login");
            changeSignupText("Sign up");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firebaseAuth=FirebaseAuth.getInstance();

        ((Button)findViewById(R.id.LoginButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)findViewById(R.id.LoginButton)).getText().equals("Login"))
                    UserProfile.this.changeActivity("Login");
                else
                {
                    final String username,fullname,dob;
                    username=((EditText)findViewById(R.id.upUsername)).getText().toString();
                    fullname=((EditText)findViewById(R.id.upFullName)).getText().toString();
                    dob=((EditText)findViewById(R.id.upDob)).getText().toString();
                    if(!Pattern.matches("\\S{6,15}",username))
                    {
                        ((EditText)findViewById(R.id.upUsername)).setError("Username must be of 6-15 characters");
                    }
                    else if(!Pattern.matches("[a-z|A-Z]+\\s[a-z|A-Z]+",fullname))
                    {
                        ((EditText)findViewById(R.id.upFullName)).setError("Enter valid full name with space separated first & last name");
                    }
                    else if(!(Pattern.matches("[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}",dob)))
                    {
                        ((EditText)findViewById(R.id.upDob)).setError("Enter Birthdate in DD/MM/YYYY form");
                    }
                    else {
                        String dob1[]=dob.split("/");

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c);
                        String cdate[]=formattedDate.split("/");
                        if(Integer.parseInt(dob1[2])<Integer.parseInt(cdate[2]) && Integer.parseInt(dob1[0])>0 && Integer.parseInt(dob1[0])<32 && Integer.parseInt(dob1[1])>0 && Integer.parseInt(dob1[1])<13)
                        {
                            userDetails = FirebaseDatabase.getInstance().getReference().child("User_Details").child(user.getUid());
                            userDetails.child("userName").setValue(username);
                            userDetails.child("fullName").setValue(fullname);
                            userDetails.child("dateOfBirth").setValue(dob).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    ((TextView)findViewById(R.id.userName)).setText(username);
                                }
                            });
                        }
                        else
                        {
                            ((EditText)findViewById(R.id.upDob)).setError("Enter Valid Birthdate");
                        }
                    }
                }
            }
        });
        ((Button)findViewById((R.id.SignupButton))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)findViewById(R.id.SignupButton)).getText().equals("Sign up"))
                    UserProfile.this.changeActivity("Signup");
                else
                {
                    firebaseAuth.signOut();
                    Toast.makeText(UserProfile.this,"Signout Successful",Toast.LENGTH_SHORT).show();
                    changeLoginText("Login");
                    changeSignupText("Sign up");
                    ((TextView)findViewById(R.id.fullName)).setText("Email");
                    ((TextView)findViewById(R.id.userName)).setText("Username");
                    ((EditText)findViewById(R.id.upUsername)).setText("Username");
                    ((EditText)findViewById(R.id.upFullName)).setText("Full Name");
                    ((EditText)findViewById(R.id.upFullName)).setText("Date Of Birth(DD/MM/YYYY)");
                    ((TextView)findViewById(R.id.quiz3)).setText("0");
                    ((TextView)findViewById(R.id.quiz3)).setText("0");
                }
            }
        });
    }

    private void changeActivity(String activityName)
    {
        Intent intent=null;
        if(activityName.equals("Login"))
        {
            intent=new Intent(this,Login.class);
        }
        else if(activityName.equals("Signup"))
        {
            intent=new Intent(this,Signup.class);
        }
        startActivity(intent);
    }

    private boolean checkUserSignIn()
    {
        user=firebaseAuth.getCurrentUser();
        if(user!=null)
            return true;
        else
            return false;
    }

    private void changeLoginText(String msg)
    {
        ((Button)findViewById(R.id.LoginButton)).setText(msg);
    }
    private void changeSignupText(String msg)
    {
        ((Button)findViewById(R.id.SignupButton)).setText(msg);
    }
}
