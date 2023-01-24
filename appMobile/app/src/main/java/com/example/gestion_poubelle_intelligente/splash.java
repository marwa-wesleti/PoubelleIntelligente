package com.example.gestion_poubelle_intelligente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class splash extends AppCompatActivity {
    TextView txt1 ,txt2;
    Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txt2=(TextView) findViewById(R.id.txt2);
        txt1=(TextView) findViewById(R.id.txt1);

        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .playOn(txt1);
        YoYo.with(Techniques.ZoomIn)
                .duration(3000)
                .playOn(txt2);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),AuthenActivity.class));
                finish();
            }
        },6000);



    }
}