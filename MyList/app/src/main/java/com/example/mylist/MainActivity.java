package com.example.mylist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements Listener {
    private boolean condition = false;
    private Context context = this;
    private IRepository<String,Item> repository;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new DatabaseRepository(this);
        controller = new Controller(repository);
        MainFragment fragment = new MainFragment();
        fragment.setContext(this);
        fragment.setListener(this);
        fragment.setController(this.controller);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment,fragment,MainFragment.class.getSimpleName())
                .addToBackStack(MainFragment.class.getSimpleName())
                .commit();

    }

    @Override
    public void onBackPressed() {
        if(condition == true){
            condition = false;

            MainFragment fragment = new MainFragment();
            fragment.setContext(context);
            fragment.setListener(this);
            fragment.setController(controller);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment,fragment,MainFragment.class.getSimpleName())
                    .commit();
        }else{
            this.finishAndRemoveTask();
        }

    }

    @Override
    public void addItemFragment() {
        condition = true;
        AddItemFragment fragment = new AddItemFragment();
        fragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment,fragment,AddItemFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void addItem(String description,Float quantity, String type) {
        try {
            controller.addItem(description,quantity, type);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Adaugat");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Eroare la adaugare !~~~~~~~~~~~~~");
        }
    }

    @Override
    public void removeItem(String description,Float quantity, String type) {
        try {
            controller.removeItem(description,quantity, type);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Eroare la stergere !~~~~~~~~~~~~~");
        }
    }

    @Override
    public void getAllItems() {

    }
}
