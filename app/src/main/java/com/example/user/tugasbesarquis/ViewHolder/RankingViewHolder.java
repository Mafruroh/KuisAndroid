package com.example.user.tugasbesarquis.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.tugasbesarquis.Interface.ItemClickListener;
import com.example.user.tugasbesarquis.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_nama,txt_skor;
    private ItemClickListener itemClickListener;
    public RankingViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_nama = (TextView) itemView.findViewById(R.id.txt_nama);
        txt_skor = (TextView) itemView.findViewById(R.id.txt_skor);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
