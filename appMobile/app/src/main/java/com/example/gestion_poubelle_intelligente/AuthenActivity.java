package com.example.gestion_poubelle_intelligente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;


public class AuthenActivity extends AppCompatActivity {
    EditText edUserName,edPassUser;
    ImageView hide_eye;
    Button btnConnect;
    SQLiteDatabase db;
    Cursor cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);
        edUserName=(EditText) findViewById(R.id.edName);
        edPassUser=(EditText) findViewById(R.id.edPassUser);
        btnConnect=(Button) findViewById(R.id.btnConnect);
        hide_eye=(ImageView) findViewById(R.id.hide_eye);

        // creation de la base de données
        db=openOrCreateDatabase("poubelle_intelligente",MODE_PRIVATE,null);

        // creation de la table user
        db.execSQL("create table if not exists Admin (" +
                "id integer primary key autoincrement,"+
                "nom varchar ," +
                "ville varchar )");
        // creation de l'utilisateur admin
        SQLiteStatement s = db.compileStatement("select count(*) from Admin;");
        long c = s.simpleQueryForLong();
        if (c==0) {
            db.execSQL("insert into Admin (id,nom,ville) values (?,?,?);", new String[]{null,"admin", "Tunis"});
        }
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPassUser.getText().toString().equals("admin"))
                connecterAd();
                else
                    connecterEb(db);

            }
        });
        hideEye();

    }

    public void connecterAd(){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();

    }
    public void connecterEb(SQLiteDatabase db) {
        cu = db.rawQuery("select * from eboueur", null);
        cu.moveToFirst();
        if  (cu.getCount()>0){
        int i = 0;
        while (i < cu.getCount()) {
            if (edPassUser.getText().toString().equals(cu.getString(2))) {
                Intent intent = new Intent(getApplicationContext(), DetailsPoubelle.class);
                intent.putExtra("idEb", cu.getInt(0));
                intent.putExtra("nomE", cu.getString(1));
                startActivity(intent);
                finish();

                break;
            }
            else{
                Toast.makeText(this, "aucun eboueur existe déja", Toast.LENGTH_SHORT).show();
            }


            cu.moveToNext();
            i++;
        }

        }
        

    }
    private void hideEye(){
        hide_eye.setImageResource(R.drawable.ic_baseline_off);
        hide_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPassUser.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    edPassUser.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    hide_eye.setImageResource(R.drawable.ic_baseline_off);
                } else {
                    edPassUser.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    hide_eye.setImageResource(R.drawable.ic_baseline_on);
                }

            }
        });


    }
}