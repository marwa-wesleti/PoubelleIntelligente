package com.example.gestion_poubelle_intelligente;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

public class PoubelleActivity extends AppCompatActivity {
    private EditText edSearchPo,edAdrePo, edNomEbPo, edTelEbPo,etatPo;
    private ImageButton  firstPo, previousPo, nextPo, lastPo;
    private ImageView imgsearchPo,imgsearchEbPo,addPo,updatePo, deletePo;
    private Button btnsavePo, btncancelPo;
    private SQLiteDatabase db;
    private Cursor cu , cu1;
    private  int op;
    private PoubelleActivity activity;
    private String x;
    private TextView txtIdEb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poubelle);
        db=openOrCreateDatabase("poubelle_intelligente",MODE_PRIVATE,null);
        db.execSQL("create table if not exists poubelle (" +
                "id integer primary key autoincrement NOT NULL , " +
                "adresse varchar NOT NULL, " +
                "etat integer,"+
                "idEb integer,"+
                "FOREIGN KEY (idEb) REFERENCES eboueur(id)) ");
        this.activity=this;
        edSearchPo=(EditText)findViewById(R.id.edSeaEbPo);
        edAdrePo = (EditText) findViewById(R.id.edAdrePo);
        edNomEbPo = (EditText) findViewById(R.id.edNomEbPo);
        edTelEbPo = (EditText) findViewById(R.id.EdTelEbPo);
        etatPo=(EditText) findViewById(R.id.etatPo);
        firstPo = (ImageButton) findViewById(R.id.firstPo);
        previousPo = (ImageButton) findViewById(R.id.previousPo);
        nextPo = (ImageButton) findViewById(R.id.nextPo);
        lastPo = (ImageButton) findViewById(R.id.lastPo);
        imgsearchEbPo=(ImageView) findViewById(R.id.imgsearchEbPo);
        imgsearchPo=(ImageView) findViewById(R.id.imgsearchPo);
        addPo=(ImageView)findViewById(R.id.addPo);
        updatePo=(ImageView)findViewById(R.id.updatePo);
        deletePo=(ImageView)findViewById(R.id.deletePo);
        btnsavePo=(Button)findViewById(R.id.btnsavePo);
        btncancelPo=(Button)findViewById(R.id.btncancelPo);
        txtIdEb=(TextView)findViewById(R.id.txtIdEb);

        edAdrePo.setEnabled(false);
        etatPo.setEnabled(false);
        edNomEbPo.setEnabled(false);
        edTelEbPo.setEnabled(false);



        imgsearchPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recherchePoubelle(db);
            }
        });
        imgsearchEbPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rechercherEboueurPoubelle();
            }
        });
        addPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ajouterPoubelle();
            }
        });
        updatePo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePoubelle();
            }
        });
        btnsavePo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePoubelle(db);
            }
        });

        deletePo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePoubelle(db,activity);
            }
        });

        btncancelPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelPoubelle();
            }
        });
        firstPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirstPoubelle();
            }
        });
        lastPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastPoubelle();
            }
        });

        nextPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextPoubelle();
            }
        });

        previousPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                previousPoubelle();
            }
        });




    }


    void recherchePoubelle(SQLiteDatabase db){
        cu=db.rawQuery("select P.adresse,P.etat,E.nom,E.tel,P.id ,E.id from poubelle P,eboueur E where " +
                "E.id=P.idEb and (P.adresse like ? or E.nom like ? )",new String[]{"%"+edSearchPo.getText().toString()+"%",
                "%"+edSearchPo.getText().toString()+"%"});
        try {
            cu.moveToFirst();
            edAdrePo.setText(cu.getString(0));
            etatPo.setText(cu.getString(1));
            edNomEbPo.setText(cu.getString(2));
            edTelEbPo.setText(cu.getString(3));
            txtIdEb.setText(cu.getString(5));
            if (cu.getCount()==1){
                invisible();
            }
            else {
                visible();
                previousPo.setEnabled(false);
                nextPo.setEnabled(true);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            viderChamps();
            invisible();
        }
    }
    void rechercherEboueurPoubelle() {
        startActivityForResult(new Intent(getApplicationContext(),RechercherEboueur.class),1);
        viderChamps();
    }
    void FirstPoubelle(){
        try {
            cu.moveToFirst();
            edAdrePo.setText(cu.getString(0));
            etatPo.setText(cu.getString(1));
            edNomEbPo.setText(cu.getString(2));
            edTelEbPo.setText(cu.getString(3));
            txtIdEb.setText(cu.getString(5));
            visible();
            previousPo.setEnabled(false);
            nextPo.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "aucun resultat", Toast.LENGTH_SHORT).show();
        }

    }
    void lastPoubelle(){
        try {
            cu.moveToLast();
            edAdrePo.setText(cu.getString(0));
            etatPo.setText(cu.getString(1));
            edNomEbPo.setText(cu.getString(2));
            edTelEbPo.setText(cu.getString(3));
            txtIdEb.setText(cu.getString(5));
            visible();
            previousPo.setEnabled(true);
            nextPo.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "aucun resultat", Toast.LENGTH_SHORT).show();
        }
    }
    void nextPoubelle(){
        try {
            cu.moveToNext();
            edAdrePo.setText(cu.getString(0));
            etatPo.setText(cu.getString(1));
            edNomEbPo.setText(cu.getString(2));
            edTelEbPo.setText(cu.getString(3));
            txtIdEb.setText(cu.getString(5));
            previousPo.setEnabled(true);
            if (cu.isLast()){
                nextPo.setEnabled(false);
            }


        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    void previousPoubelle(){
        try {
            cu.moveToPrevious();
            edAdrePo.setText(cu.getString(0));
            etatPo.setText(cu.getString(1));
            edNomEbPo.setText(cu.getString(2));
            edTelEbPo.setText(cu.getString(3));
            txtIdEb.setText(cu.getString(5));
            nextPo.setEnabled(true);
            if (cu.isFirst()){
                previousPo.setEnabled(false);
            }


        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    void ajouterPoubelle() {
        op = 1;

        imgsearchPo.setVisibility(View.INVISIBLE);
        updatePo.setVisibility(View.INVISIBLE);
        deletePo.setVisibility(View.INVISIBLE);
        btnsavePo.setVisibility(View.VISIBLE);
        btncancelPo.setVisibility(View.VISIBLE);
        addPo.setEnabled(false);
        edAdrePo.setEnabled(true);
        etatPo.setEnabled(true);
        invisible();
        viderChamps();


    }

    void updatePoubelle() {
        try {
            x=cu.getString(4);
            op = 2;
            Log.d("id",cu.getString(4));
            imgsearchPo.setVisibility(View.VISIBLE);
            addPo.setVisibility(View.INVISIBLE);
            deletePo.setVisibility(View.INVISIBLE);
            btnsavePo.setVisibility(View.VISIBLE);
            btncancelPo.setVisibility(View.VISIBLE);
            updatePo.setEnabled(false);
            edAdrePo.setEnabled(true);
            etatPo.setEnabled(true);
            invisible();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Sélectionner une poubelle puis le modifier", Toast.LENGTH_SHORT).show();
        }


    }

    void savePoubelle(SQLiteDatabase db) {
        if (op == 1) {
            db.execSQL("insert into poubelle (id,adresse,etat,idEb) values (?,?,?,?);"
                    , new String[]{null,edAdrePo.getText().toString(),etatPo.getText().toString(),txtIdEb.getText().toString()});
            Toast.makeText(this, "successfull ", Toast.LENGTH_SHORT).show();
            viderChamps();


        } else if (op == 2) {
            Log.d("i",x);
            db.execSQL("update poubelle set adresse=?,etat=? where id=?", new String[]{edAdrePo.getText().toString(),
                    etatPo.getText().toString(),x});
            Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
            viderChamps();
        }

        imgsearchPo.setVisibility(View.VISIBLE);
        updatePo.setVisibility(View.VISIBLE);
        deletePo.setVisibility(View.VISIBLE);
        btnsavePo.setVisibility(View.INVISIBLE);
        btncancelPo.setVisibility(View.INVISIBLE);
        addPo.setEnabled(true);
        updatePo.setEnabled(true);
        addPo.setVisibility(View.VISIBLE);
        edAdrePo.setEnabled(false);
        etatPo.setEnabled(false);
        invisible();
    }

    void cancelPoubelle(){

        imgsearchPo.setVisibility(View.VISIBLE);
        updatePo.setVisibility(View.VISIBLE);
        deletePo.setVisibility(View.VISIBLE);
        btnsavePo.setVisibility(View.INVISIBLE);
        btncancelPo.setVisibility(View.INVISIBLE);
        addPo.setEnabled(true);
        updatePo.setEnabled(true);
        addPo.setVisibility(View.VISIBLE);
        edAdrePo.setEnabled(false);
        etatPo.setEnabled(false);
        invisible();





    }

    void deletePoubelle(SQLiteDatabase db,PoubelleActivity activity){
        try {
            x=cu.getString(4);
            AlertDialog.Builder bul=new AlertDialog.Builder(activity)
                    .setTitle("Confirmation")
                    .setMessage("Est ce que vous voulez supprimer cet poubelle?")
                    .setIcon(R.drawable.validate)
                    .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.execSQL("delete from poubelle where id=?;",new String[]{x});
                            viderChamps();
                            invisible();
                            cu.close();
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            bul.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Selectionner une poubelle puis la supprimer ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == 1) {

                String nom = data.getStringExtra("nom");
                String id = data.getStringExtra("id");
                String tel=data.getStringExtra("tel");
                txtIdEb.setText(id);
                edNomEbPo.setText(nom);
                edTelEbPo.setText(tel);



            }
        }
    }

    public void visible(){
        firstPo.setVisibility(View.VISIBLE);
        nextPo.setVisibility(View.VISIBLE);
        previousPo.setVisibility(View.VISIBLE);
        lastPo.setVisibility(View.VISIBLE);
    }
    public void invisible(){
        firstPo.setVisibility(View.INVISIBLE);
        nextPo.setVisibility(View.INVISIBLE);
        previousPo.setVisibility(View.INVISIBLE);
        lastPo.setVisibility(View.INVISIBLE);
    }
    public void viderChamps(){
        edAdrePo.setText("");
        etatPo.setText("");
        txtIdEb.setText("");
    }
}