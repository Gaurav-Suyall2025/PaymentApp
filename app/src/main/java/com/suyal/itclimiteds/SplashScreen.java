package com.suyal.itclimiteds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.suyal.itclimiteds.fragments.FragmentMainActivity;
import com.suyal.itclimiteds.fragments.SignInFragment;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Window window = SplashScreen.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SplashScreen.this, R.color.black));
        //        mAuth = FirebaseAuth.getInstance();
//
//        if(mAuth.getCurrentUser()!=null){
//            Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashScreen.this, "Welcome to AapTak", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashScreen.this, FragmentMainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}