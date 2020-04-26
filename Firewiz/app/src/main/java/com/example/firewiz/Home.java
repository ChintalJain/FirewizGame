package com.example.firewiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView play;
    AlertDialog alertDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);
        //Setting up toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.home_toolbar));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.account_circle_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        play=(TextView)findViewById(R.id.play);
        firebaseAuth= FirebaseAuth.getInstance();

        //bottom navigation
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        //bottomNavigationView.setSelectedItemId(R.id.loginButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_create:
                        startActivity(new Intent(getApplicationContext(), CreateQuiz.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_play:
                        FirebaseUser u=firebaseAuth.getCurrentUser();
                        if(u==null)
                        {
                            Toast.makeText(Home.this, "Please Login First.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            btn_showDialog();
                        }
                        //startActivity(new Intent(getApplicationContext(), PlayQuiz.class));
                        //overridePendingTransition(0,0);
                        return true;
                    case R.id.action_history:
                        //startActivity(new Intent(getApplicationContext(),Loginn.class));
                        //overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=null;
        switch (item.getItemId()) {
            case R.id.QuizSearch:
                intent=new Intent(this,Search.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                intent=new Intent(this,UserProfile.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void btn_showDialog() {
        final AlertDialog.Builder alert=new AlertDialog.Builder(Home.this);
        View mView =getLayoutInflater().inflate(R.layout.custom_dialog,null);
        final EditText txt_gamepin=(EditText)mView.findViewById(R.id.txt_gamepin);
        Button btn_cancel=(Button)mView.findViewById(R.id.btn_cancel);
        Button btn_ok=(Button)mView.findViewById(R.id.btn_ok);
        alert.setView(mView);
        alertDialog=alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();;
            }


        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), PlayQuiz.class);
                i.putExtra("QuizId",txt_gamepin.getText().toString());
                FirebaseUser user=firebaseAuth.getCurrentUser();
                i.putExtra("UserId",user.getUid());
                startActivityForResult(i,1);
                overridePendingTransition(0,0);
            }
        });
        alertDialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            alertDialog.dismiss();
        }
    }

}
