package com.example.mylist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private Listener listener;
    private Context context;
    private Controller controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Item> shoppingList = new ArrayList<>();
        shoppingList = controller.getAllItems();

        if(shoppingList.size()>0){
            TextView emptyLbl = view.findViewById(R.id.emptyLbl);
            emptyLbl.setText("");
        }else{
            TextView emptyLbl = view.findViewById(R.id.emptyLbl);
            emptyLbl.setText("Empty");
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(shoppingList,context,controller);
        recyclerView.setAdapter(recyclerViewAdapter);


        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.addItemFragment();
            }
        });
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setController(Controller ctrl){this.controller = ctrl;}

}
