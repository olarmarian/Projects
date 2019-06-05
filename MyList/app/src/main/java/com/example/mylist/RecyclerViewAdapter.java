package com.example.mylist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<Item> data;
    private Controller ctrl;
    private Context context;
    private ItemClickListener itemClickListener;

    public RecyclerViewAdapter(List<Item> data,Context context,Controller ctrl) {
        this.data = data;
        this.context = context;
        this.ctrl = ctrl;
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
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Snackbar.make(view, "Long Click" + data.get(position).toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{
                    Snackbar.make(view, "" + data.get(position).toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    removeAt(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeAt(int position) {
//        data.remove(position);
        try {
            ctrl.removeItem(data.get(position).getDescription(),data.get(position).getQuantity(),data.get(position).getType());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data = ctrl.getAllItems();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{

        final TextView itemTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemTextView = itemView.findViewById(R.id.list_item);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener item){
            itemClickListener = item;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
    }
}
