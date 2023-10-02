package com.bluetoothkeychainapp54.bluetoothkeychain;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Music> musics;
    LayoutInflater layoutInflater;
    Context context;
    MainActivity ma;


    public RecyclerViewAdapter (ArrayList<Music> musics, Context context ,MainActivity ma ){
        this.musics = musics;
        this.context = context;
        this.ma = ma;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Her bir satır için temsil edilecek arayüz seçilir.
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.row_list,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.linearLayout.setTag(holder);
        final  String name = musics.get(position).getName();
        holder.txtContent.setText(name);
        holder.linearLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ma.playMusic(name);
            }
        });
    }

    @Override
    public int getItemCount() {//Gelen verilerin boyutunu döndürür.
        return musics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView  txtContent;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtContent = itemView.findViewById(R.id.txtContent);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
