package com.nayan.bloodbank;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;


    private class SplashAct extends Thread{
        public void run(){
            try{
                int SLEEP_TIMER = 3;
                sleep(1000 * SLEEP_TIMER);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashActivity.this, Login2Activity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        SplashAct SplashAct = new SplashAct();
        SplashAct.start();


        logo = (ImageView)findViewById(R.id.logo);
        logo.animate().alpha(1).setDuration(2000);

    }
}
