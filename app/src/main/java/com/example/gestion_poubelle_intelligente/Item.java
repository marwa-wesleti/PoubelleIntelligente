package com.example.gestion_poubelle_intelligente;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Item {

    String txtAd,txtPourcent,img;
    int valider;


    public Item( String txtAd, String txtPourcent,String img) {
        this.txtAd = txtAd;
        this.txtPourcent = txtPourcent;
        this.img=img;


    }



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTxtAd() {
        return txtAd;
    }

    public void setTxtAd(String txtAd) {
        this.txtAd = txtAd;
    }

    public String getTxtPourcent() {
        return txtPourcent;
    }

    public void setTxtPourcent(String txtPourcent) {
        this.txtPourcent = txtPourcent;
    }
}
