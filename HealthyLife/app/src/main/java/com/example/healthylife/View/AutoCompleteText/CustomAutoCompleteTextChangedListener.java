package com.example.healthylife.View.AutoCompleteText;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.example.healthylife.Controller.Controller;
import com.example.healthylife.Model.AlimentEntity;
//import com.example.healthylife.View.AddAlimentPopUp;
import com.example.healthylife.View.AddDialog;


public class CustomAutoCompleteTextChangedListener implements TextWatcher {

    private AppCompatDialogFragment context;
    private Controller controller;
    public CustomAutoCompleteTextChangedListener(AppCompatDialogFragment ctx, Controller ctrl){
        this.context = ctx;
        this.controller = ctrl;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        AddDialog ctx = (AddDialog) (context);
        try {
            ctx.items = controller.getAlimentsByName('%'+s.toString()+'%');
            ctx.data.notifyDataSetChanged();
            ctx.data = new ArrayAdapter<AlimentEntity>(ctx.getContext(), android.R.layout.simple_dropdown_item_1line, ctx.items);
            ctx.nameTxt.setAdapter(ctx.data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
