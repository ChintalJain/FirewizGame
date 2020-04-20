package com.example.firewiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation topanim,bottomanim;
    ImageView image;
    TextView logo;

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bootom_anim);

        image=findViewById(R.id.firewizLogo);
        logo=findViewById(R.id.firewizText);

        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,Home.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
                overridePendingTransition(0,0);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
