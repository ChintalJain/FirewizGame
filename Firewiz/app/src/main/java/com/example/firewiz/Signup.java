package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.firewiz.model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    UserDetails usd=new UserDetails();
    Button btn_register;
    EditText confirm_password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDBR;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        firebaseDBR= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        btn_register=(Button)findViewById(R.id.register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
                Log.d("Signup","Register button clicked");
                usd.setUserName(((EditText)findViewById(R.id.userName)).getText().toString().trim());
                usd.setEmail(((EditText)findViewById(R.id.email)).getText().toString().trim());
                usd.setPassword(((EditText)findViewById(R.id.password)).getText().toString().trim());
                usd.setFullName(((EditText)findViewById(R.id.fullName)).getText().toString().trim());
                usd.setDateOfBirth(((EditText)findViewById(R.id.dob)).getText().toString().trim());
                usd.setMobileNumber(((EditText)findViewById(R.id.mobileNumber)).getText().toString().trim());
                confirm_password=(EditText)findViewById(R.id.confirmPassword);
                if(((RadioButton)findViewById(R.id.maleRB)).isChecked())
                {
                    usd.setGender("Male");
                }
                else if(((RadioButton)findViewById(R.id.femaleRB)).isChecked())
                {
                    usd.setGender("Female");
                }
                else
                {
                    Toast.makeText(Signup.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                    ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
                }
                if(validateUser())
                {
                    firebaseAuth.createUserWithEmailAndPassword(usd.getEmail(),usd.getPassword())
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Signup.this, "Registered Successfully...", Toast.LENGTH_SHORT).show();
                                        makeEntryOfUser(task.getResult().getUser().getUid());
                                        finish();
                                        return;
                                    }
                                    else
                                    {
                                        ((EditText)findViewById(R.id.email)).setError("This e-mail id is already used.\nTry different one.");
                                        ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }
                else {
                    ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void makeEntryOfUser(String UserId)
    {
        firebaseDBR.child("User_Details").child(UserId).setValue(usd);
    }
    private boolean validateUser()
    {
        if(!usd.validateUserName())
        {
            ((EditText)findViewById(R.id.userName)).setError("Username must be of 6-15 characters");
            return false;
        }
        if(!usd.validateEmail())
        {
            ((EditText)findViewById(R.id.email)).setError("Enter valid e-mail");
            return false;
        }
        if(!usd.validatePassword())
        {
            ((EditText)findViewById(R.id.password)).setError("Length of password must be of 6-10 including atleast one digit & one special character.");
            return false;
        }
        if(!usd.getPassword().equals(confirm_password.getText().toString().trim()))
        {
            confirm_password.setError("Password & Confirm Password must be same.");
            return false;
        }
        if(!usd.validateDateOfBirth())
        {
            ((EditText)findViewById(R.id.dob)).setError("Enter valid Birth date");
            return false;
        }
        if(!usd.getMobileNumber().equals("") && !usd.validateMobileNumber())
        {
            ((EditText)findViewById(R.id.dob)).setError("Enter valid Mobile Number");
            return false;
        }
        return true;
    }
}