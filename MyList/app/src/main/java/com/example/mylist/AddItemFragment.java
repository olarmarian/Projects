package com.example.mylist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddItemFragment extends Fragment {
    private Listener listener;
    private Spinner spinner;
    private List<String> dataSourceSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private EditText description;
    private EditText quantity;
    private Button addBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.add_item_fragment, container, false);
        description = (EditText)fragmentView.findViewById(R.id.nameItemTxt);
        quantity = (EditText)fragmentView.findViewById(R.id.quantityItemTxt);
        spinner = (Spinner)fragmentView.findViewById(R.id.types_quantitySpinner);
        addBtn = (Button)fragmentView.findViewById(R.id.addBtn);
        setRetainInstance(true);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errors = validate(description.getText().toString(),quantity.getText().toString());
                if(errors.length()!=0){
                    Toast.makeText(getContext(),errors,Toast.LENGTH_LONG).show();
                }else{
                    listener.addItem(description.getText().toString(),Float.parseFloat(quantity.getText().toString()),spinner.getSelectedItem().toString());
                    Snackbar.make(view,"Item added !", Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();
                    getActivity().onBackPressed();
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataSourceSpinner = getSource();
        arrayAdapter=getAdapter();
        spinner.setAdapter(arrayAdapter);
        final Spinner finalSpinner = spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }

    private String validate(String description, String quantity){
        String errors = "";
        if(description.equals("")) {
            errors += "Invalid description !\n";
        }
        try{
             float check = Float.parseFloat(quantity);
        }catch (Exception ex){
            errors +="Invalid quantity !\n";
        }
        return errors;
    }

    private List<String> getSource(){
        List<String> types = new ArrayList<>();
        types.add("kg");
        types.add("gr");
        types.add("pieces");
        types.add("l");
        types.add("packs");
        return types;
    }
    private ArrayAdapter<String> getAdapter(){
        return new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataSourceSpinner);
    }

}
