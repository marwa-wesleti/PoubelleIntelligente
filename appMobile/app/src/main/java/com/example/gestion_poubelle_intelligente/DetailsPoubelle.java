package com.example.gestion_poubelle_intelligente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsPoubelle extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SQLiteDatabase db;
    private Cursor cu;
    private TextView txtWelc,txtValAd,txtPercent;
    private DatabaseReference database,database1;
    double firebasePercent;
    Integer percentInt,result,valcheck=0;
    private Button btnouv,btnferm;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_poubelle);
        txtWelc=(TextView) findViewById(R.id.txtWelcom);
        txtValAd=(TextView) findViewById(R.id.txtValAd);
        txtPercent=(TextView) findViewById(R.id.txtPercent);
        btnouv=(Button) findViewById(R.id.btnouv);
        btnferm=(Button) findViewById(R.id.btnferm);

        Intent intent=getIntent();
        Integer idE=intent.getIntExtra("idEb",-1);
        String nomE=intent.getStringExtra("nomE");
        db=openOrCreateDatabase("poubelle_intelligente",MODE_PRIVATE,null);
        cu=db.rawQuery("select * from poubelle where idEb=? ",new String[]{""+idE});
        cu.moveToFirst();
        txtWelc.setText("Les Poubelles de  "+nomE);
        txtValAd.setText(cu.getString(1));


       database = FirebaseDatabase.getInstance().getReference().child("test").child("json").child("value");
       database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebasePercent = (double) dataSnapshot.getValue();
                 percentInt=Integer.valueOf((int) firebasePercent);
                 result=((19-percentInt)*100)/19;
                 String percent=String.valueOf(result);
                txtPercent.setText(percent+"%");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database1=FirebaseDatabase.getInstance().getReference().child("test").child("json").child("ouvrir");

        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                btnouv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valcheck=1;
                        database1.setValue(valcheck);
                    }
                });
                btnferm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valcheck=0;
                        database1.setValue(valcheck);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }


    }




