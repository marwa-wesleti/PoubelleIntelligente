package com.example.gestion_poubelle_intelligente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsPoubelle extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SQLiteDatabase db;
    private AdapterPo adapte;
    private ArrayList<Item> item;
    private Cursor cu;
    private TextView txtWelc;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_poubelle);

        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        txtWelc=(TextView) findViewById(R.id.txtWelcom);
        Intent intent=getIntent();
        Integer idE=intent.getIntExtra("idEb",-1);
        String nomE=intent.getStringExtra("nomE");
        db=openOrCreateDatabase("poubelle_intelligente",MODE_PRIVATE,null);
        item=new ArrayList<>();
        cu=db.rawQuery("select * from poubelle where idEb=? ",new String[]{""+idE});
        cu.moveToFirst();
        txtWelc.setText("Les Poubelles de  "+nomE);
        for (int i=0;i<cu.getCount();i++){
            item.add(new Item(cu.getString(1),cu.getString(2),""));
            cu.moveToNext();

        }
        adapte=new AdapterPo(DetailsPoubelle.this,item);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsPoubelle.this));
        recyclerView.setAdapter(adapte);







    }


    }




