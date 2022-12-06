package com.example.gestion_poubelle_intelligente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class RechercherEboueur extends AppCompatActivity {
    private EditText edSearchNom, edRechNom, edRechId,edRechTel;
    private ImageButton  RechFirst, RechPrevious, RechNext, RechLast;
    private ImageView imgRech;
    private Button btnValider, btnCancel;
    private SQLiteDatabase db;
    private Cursor cu,cu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_eboueur);
        edSearchNom = (EditText) findViewById(R.id.edSearchNom);
        edRechNom = (EditText) findViewById(R.id.edRechNom);
        edRechId = (EditText) findViewById(R.id.edRechId);
        edRechTel = (EditText) findViewById(R.id.edRechTel);
        imgRech = (ImageView) findViewById(R.id.imgRech);
        RechFirst = (ImageButton) findViewById(R.id.rechFirst);
        RechPrevious = (ImageButton) findViewById(R.id.rechPrevious);
        RechNext = (ImageButton) findViewById(R.id.rechNext);
        RechLast = (ImageButton) findViewById(R.id.rechLast);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        db = openOrCreateDatabase("poubelle_intelligente", MODE_PRIVATE, null);

        imgRech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechercherNomEboueur(db);
            }
        });
        RechFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechFirst();
            }
        });

        RechPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechprevious();
            }
        });
        RechNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechnext();
            }
        });

        RechLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechlast();
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valider();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });




    }

    public void RechercherNomEboueur(SQLiteDatabase db) {
        cu = db.rawQuery("select id,nom,tel from eboueur where nom like ?  ", new String[]{"%"+edSearchNom.getText().toString()+"%"});
        cu.moveToFirst();

        try {
            cu.moveToFirst();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechTel.setText(cu.getString(2));
            if (cu.getCount() == 1) {
                invisible();
            } else {
                visible();
                RechPrevious.setEnabled(false);
                RechNext.setEnabled(true);
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "aucun resultat", Toast.LENGTH_SHORT).show();
            edRechId.setText("");
            edRechNom.setText("");
            edRechTel.setText("");
            invisible();
        }
    }
    public void RechFirst(){
        try {
            cu.moveToFirst();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechTel.setText(cu.getString(2));
            RechPrevious.setEnabled(false);
            RechNext.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"aucun eboueur n'est existant.",Toast.LENGTH_SHORT).show();
        }

    }
    public void Rechnext(){
        try {
            cu.moveToNext();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechTel.setText(cu.getString(2));
            RechPrevious.setEnabled(true);
            if (cu.isLast()){
                RechNext.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void Rechprevious(){
        try {
            cu.moveToPrevious();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechTel.setText(cu.getString(2));
            RechNext.setEnabled(true);
            if (cu.isLast()){
                RechPrevious.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Rechlast(){
        try {
            cu.moveToLast();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechTel.setText(cu.getString(2));
            RechPrevious.setEnabled(true);
            RechNext.setEnabled(false);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void valider(){

        String nom=edRechNom.getText().toString();
        String id=edRechId.getText().toString();
        String tel=edRechTel.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("nom",nom);
        intent.putExtra("id",id);
        intent.putExtra("tel",tel);
        setResult(RESULT_OK,intent);
        finish();


    }
    public void cancel(){
        Intent intent=new Intent();
        intent.putExtra("nom","");
        intent.putExtra("id","");
        intent.putExtra("tel","");

        setResult(RESULT_OK,intent);
        finish();
    }
    public void visible(){
        RechFirst.setVisibility(View.VISIBLE);
        RechNext.setVisibility(View.VISIBLE);
        RechPrevious.setVisibility(View.VISIBLE);
        RechLast.setVisibility(View.VISIBLE);
    }
    public void invisible(){
        RechFirst.setVisibility(View.VISIBLE);
        RechNext.setVisibility(View.VISIBLE);
        RechPrevious.setVisibility(View.VISIBLE);
        RechLast.setVisibility(View.VISIBLE);
    }

}