package com.example.gestion_poubelle_intelligente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    ImageView imgEboueur,imgPoubelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imgEboueur=(ImageView) findViewById(R.id.imgEboueur);
        imgPoubelle=(ImageView) findViewById(R.id.imgPoubelle);

        imgEboueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EboueurActivity.class));
            }
        });
        imgPoubelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PoubelleActivity.class));
            }
        });

    }
}