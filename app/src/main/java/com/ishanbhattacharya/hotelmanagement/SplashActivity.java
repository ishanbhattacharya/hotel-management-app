package com.ishanbhattacharya.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private ConstraintLayout gradBack;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);
        gradBack = findViewById(R.id.gradientLayout);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()==null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toLogin();
                }
            },2000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toWelcome();
                }
            },1000);
        }
    }

    public void toLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, gradBack, "shiftUp");
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, opts.toBundle());
    }

    public void toWelcome(){
        Intent intent = new Intent(this, WelcomeActivity.class);
        ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, gradBack, "shiftUp");
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, opts.toBundle());
    }
}