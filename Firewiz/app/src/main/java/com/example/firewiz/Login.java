package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    //EditText txtEmail,txtPassword;
    private FirebaseAuth firebaseAuth;
    private String email,password;
    private FirebaseUser user=null;

    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void btn_signUp(View view) {
        startActivity(new Intent(getApplicationContext(),Signup.class));
    }

    public void btn_Login(View view) {
        email=((EditText)findViewById(R.id.email)).getText().toString().trim();
        password=((EditText)findViewById(R.id.password)).getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user=firebaseAuth.getCurrentUser();
                            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        } else {
                            Toast.makeText(Login.this,"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
