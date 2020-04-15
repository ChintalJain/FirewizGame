package com.example.firewiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {

    private FirebaseUser user=null;
    private FirebaseAuth firebaseAuth;
    //private Button b1,b2,b3;


    @Override
    protected void onStart() {
        super.onStart();
        if(checkUserSignIn())
        {
            changeLoginText("Already Logged in..");
        }
        else
        {
            changeLoginText("Sign In");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firebaseAuth=FirebaseAuth.getInstance();

        ((Button)findViewById(R.id.loginButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile.this.changeActivity("Login");
            }
        });
        ((Button)findViewById((R.id.signupButton))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile.this.changeActivity("Signup");
            }
        });
        ((Button)findViewById(R.id.signoutButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(UserProfile.this,"Signout Successful",Toast.LENGTH_SHORT).show();
                changeLoginText("Sign In");
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
        ((Button)findViewById(R.id.loginButton)).setText(msg);
    }
}
