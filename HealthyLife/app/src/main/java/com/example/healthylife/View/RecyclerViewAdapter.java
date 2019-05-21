package com.example.healthylife.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthylife.Model.AlimentEntity;
import com.example.healthylife.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<AlimentEntity> data;

    public RecyclerViewAdapter(List<AlimentEntity> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recyclerRow= layoutInflater.inflate(R.layout.list_recycler_item,parent,false);
        return new RecyclerViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        String item = data.get(position).toString();
        holder.itemTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        final TextView itemTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemTextView = itemView.findViewById(R.id.list_item);
        }
    }
}
