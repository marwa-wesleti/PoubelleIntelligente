package com.example.gestion_poubelle_intelligente;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EboueurActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private EditText EdsearchEb,Ednom,EdCIN,Edadresse,Edtel;
    private ImageView  searchEb;
    private ImageButton first,previous,next,last,addEb,updateEb,deleteEb;
    private Button saveEb,cancelEb,btnDetailP;
    private Cursor cu,cin;
    private String x;
    private int op;
    private EboueurActivity activity;
    private TextView txtid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eboueur);
        EdsearchEb=(EditText) findViewById(R.id.EdsearchEb);
        Ednom=(EditText) findViewById(R.id.Ednom);
        EdCIN=(EditText) findViewById(R.id.Edprenom);
        Edadresse=(EditText) findViewById(R.id.Edadresse);
        Edtel=(EditText) findViewById(R.id.Edtel);
        searchEb=(ImageView) findViewById(R.id.searchEb);
        addEb=(ImageButton) findViewById(R.id.addEb);
        updateEb=(ImageButton)findViewById(R.id.updateEb);
        deleteEb=(ImageButton)findViewById(R.id.deleteEb);
        first=(ImageButton)findViewById(R.id.first);
        previous=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        last=(ImageButton)findViewById(R.id.last);
        saveEb=(Button)findViewById(R.id.saveEb);
        cancelEb=(Button)findViewById(R.id.cancelEb);
        btnDetailP=(Button) findViewById(R.id.btnDetailP);
        txtid=(TextView) findViewById(R.id.txtid);

        this.activity=this;

        Ednom.setEnabled(false);
        EdCIN.setEnabled(false);
        Edadresse.setEnabled(false);
        Edtel.setEnabled(false);

        db=openOrCreateDatabase("poubelle_intelligente",MODE_PRIVATE,null);
        db.execSQL("create table if not exists eboueur (" +
                "id integer primary key autoincrement NOT NULL , " +
                "nom varchar ," +
                "CIN varchar unique," +
                "adresse varchar," +
                "tel varchar )");



        addEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEb();
            }
        });
        searchEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEb(db);
            }
        });

        updateEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEb();
            }
        });

        saveEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEb(db);
            }
        });

        cancelEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEb();
            }
        });
        deleteEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEb(db,activity);
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstEb();
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastEb();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextEb();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousEb();
            }
        });
        btnDetailP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtid.getText().toString().isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), DetailsPoubelle.class);
                    Integer id = Integer.parseInt(txtid.getText().toString());
                    intent.putExtra("idEb", id);
                    intent.putExtra("nomE",Ednom.getText().toString());
                    startActivity(intent);
                }
                else
                    Toast.makeText(EboueurActivity.this, "Sélectionner un eboueur", Toast.LENGTH_LONG).show();
            }
        });
    }







    void searchEb(SQLiteDatabase db)  {
        cu=db.rawQuery("select * from eboueur where (nom like ? or adresse like ?)"
                ,new String[]{"%"+EdsearchEb.getText().toString()+"%","%"+EdsearchEb.getText().toString()+"%"});
        try {
            cu.moveToFirst();
            txtid.setText(cu.getString(0));
            Ednom.setText(cu.getString(1));
            EdCIN.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));

            if (cu.getCount()==1){
                invisible();
            }
            else {
                visible();
                previous.setEnabled(false);
                next.setEnabled(true);
            }

        } catch (Exception e) {
            Toast.makeText(this, "aucun resultat", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            viderChamps();
            invisible();
        }
    }
    void firstEb(){
        try {
            cu.moveToFirst();
            txtid.setText(cu.getString(0));
            Ednom.setText(cu.getString(1));
            EdCIN.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            previous.setEnabled(false);
            next.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"aucun eboueur existe",Toast.LENGTH_SHORT).show();

        }
    }

    void lastEb(){
        try {
            cu.moveToLast();
            txtid.setText(cu.getString(0));
            Ednom.setText(cu.getString(1));
            EdCIN.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            previous.setEnabled(true);
            next.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"aucun eboueur existe",Toast.LENGTH_SHORT).show();
        }
    }

    void nextEb(){
        try {
            cu.moveToNext();
            txtid.setText(cu.getString(0));
            Ednom.setText(cu.getString(1));
            EdCIN.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            previous.setEnabled(true);
            if (cu.isLast()){
                next.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void previousEb(){
        try {
            cu.moveToPrevious();
            txtid.setText(cu.getString(0));
            Ednom.setText(cu.getString(1));
            EdCIN.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            next.setEnabled(true);
            if (cu.isFirst()){
                previous.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addEb(){
        op=1;
        EdsearchEb.setVisibility(View.INVISIBLE);
        searchEb.setVisibility(View.INVISIBLE);
        updateEb.setVisibility(View.INVISIBLE);
        deleteEb.setVisibility(View.INVISIBLE);
        addEb.setEnabled(false);
        saveEb.setVisibility(View.VISIBLE);
        cancelEb.setVisibility(View.VISIBLE);
        viderChamps();
        Ednom.setEnabled(true);
        EdCIN.setEnabled(true);
        Edadresse.setEnabled(true);
        Edtel.setEnabled(true);
       invisible();
       btnDetailP.setVisibility(View.INVISIBLE);

    }

    void updateEb() {
        try {
            x = cu.getString(0);
            op = 2;
            EdsearchEb.setVisibility(View.INVISIBLE);
            searchEb.setVisibility(View.INVISIBLE);
            addEb.setVisibility(View.INVISIBLE);
            deleteEb.setVisibility(View.INVISIBLE);
            updateEb.setEnabled(false);
            Ednom.setEnabled(true);
            EdCIN.setEnabled(true);
            Edadresse.setEnabled(true);
            Edtel.setEnabled(true);
            saveEb.setVisibility(View.VISIBLE);
            cancelEb.setVisibility(View.VISIBLE);
            invisible();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Selectionner un eboueur puis le modifier", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        btnDetailP.setVisibility(View.INVISIBLE);
    }

    void saveEb(SQLiteDatabase db){
        if (op==1){
            SQLiteStatement r=db.compileStatement("select count(*) from eboueur");
            double totalE=r.simpleQueryForLong();
            if (totalE>=1) {
                cin = db.rawQuery("select CIN from eboueur ", null);
                cin.moveToFirst();
                if (!EdCIN.getText().toString().equals(cin.getString(0))) {
                    db.execSQL("insert into eboueur (id,nom,CIN,adresse,tel) values(?,?,?,?,?);", new String[]{
                            null, Ednom.getText().toString(), EdCIN.getText().toString(), Edadresse.getText().toString(), Edtel.getText().toString()});
                    viderChamps();
                    Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "eboueur existant", Toast.LENGTH_SHORT).show();
                    viderChamps();
                }
            }
            else {
                db.execSQL("insert into eboueur (id,nom,CIN,adresse,tel) values(?,?,?,?,?);", new String[]{
                        null, Ednom.getText().toString(), EdCIN.getText().toString(), Edadresse.getText().toString(), Edtel.getText().toString()});
                viderChamps();
                Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
            }
        }

        else if(op==2){
            db.execSQL("update eboueur set  nom=?, CIN=? ,adresse=? , tel=?  where id=?;",
                    new String[]{Ednom.getText().toString(),EdCIN.getText().toString(),Edadresse.getText().toString(),
                            Edtel.getText().toString(),x});
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
            viderChamps();

        }
        EdsearchEb.setVisibility(View.VISIBLE);
        searchEb.setVisibility(View.VISIBLE);
        addEb.setVisibility(View.VISIBLE);
        updateEb.setVisibility(View.VISIBLE);
        deleteEb.setVisibility(View.VISIBLE);
        updateEb.setEnabled(true);
        addEb.setEnabled(true);
        saveEb.setVisibility(View.INVISIBLE);
        cancelEb.setVisibility(View.INVISIBLE);
        Ednom.setEnabled(false);
        EdCIN.setEnabled(false);
        Edadresse.setEnabled(false);
        Edtel.setEnabled(false);
        searchEb.performClick();
        invisible();
        viderChamps();
        btnDetailP.setVisibility(View.VISIBLE);

    }

    void cancelEb(){
        EdsearchEb.setVisibility(View.VISIBLE);
        searchEb.setVisibility(View.VISIBLE);
        addEb.setVisibility(View.VISIBLE);
        deleteEb.setVisibility(View.VISIBLE);
        updateEb.setVisibility(View.VISIBLE);
        updateEb.setEnabled(true);
        addEb.setEnabled(true);
        saveEb.setVisibility(View.INVISIBLE);
        cancelEb.setVisibility(View.INVISIBLE);
        if (cu.getCount()!=0) {
            Ednom.setText(cu.getString(1));
            EdCIN.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
        }
        else{
           viderChamps();

        }
        Ednom.setEnabled(false);
        EdCIN.setEnabled(false);
        Edadresse.setEnabled(false);
        Edtel.setEnabled(false);
        invisible();
        btnDetailP.setVisibility(View.VISIBLE);
    }

    void deleteEb(SQLiteDatabase db,EboueurActivity activity){
        try {
            x=cu.getString(0);
            AlertDialog.Builder bul1=new AlertDialog.Builder(activity)
                    .setTitle("Confirmation")
                    .setMessage("Est ce que vous voulez supprimer ce contrat?")
                    .setIcon(R.drawable.validate)
                    .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.execSQL("delete from eboueur where id=?;",new String[]{cu.getString(0)});
                            viderChamps();
                            cu.close();
                            dialog.dismiss();
                            invisible();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            bul1.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Selectionner un eboueur puis le supprimer ", Toast.LENGTH_SHORT).show();
        }
    }
    public void visible(){
        first.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        last.setVisibility(View.VISIBLE);
    }
    public void invisible(){
        first.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
        last.setVisibility(View.INVISIBLE);
    }
    public void viderChamps(){
        Ednom.setText("");
        EdCIN.setText("");
        Edadresse.setText("");
        Edtel.setText("");
        txtid.setText("");
    }


}