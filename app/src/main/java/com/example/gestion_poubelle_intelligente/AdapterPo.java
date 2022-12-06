package com.example.gestion_poubelle_intelligente;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterPo extends RecyclerView.Adapter<AdapterPo.viewHolder> {
    Context c;
    List<Item> item;

    public AdapterPo(Context c,List<Item> item){
        this.c=c;
        this.item=item;

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(c).inflate(R.layout.item_poubelle,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPo.viewHolder holder, int position) {
        Item i=item.get(position);
        if (Integer.parseInt(i.getTxtPourcent())>=80){
            holder.txtPourcent.setTextColor(Color.RED);
            holder.txtp.setTextColor(Color.RED);
            holder.txtPourcent.setText(i.getTxtPourcent());
        }
        else{
            holder.txtPourcent.setText(i.getTxtPourcent());
        }

        holder.txtAd.setText(i.getTxtAd());

    }



    @Override
    public int getItemCount() {
        return item.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txtAd,txtPourcent,txtp;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtAd=(TextView) itemView.findViewById(R.id.txtValAd);
            txtPourcent=(TextView) itemView.findViewById(R.id.txtPercent);
            txtp=(TextView) itemView.findViewById(R.id.txtp);
        }
    }
}

