package com.example.healthylife.View;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthylife.Controller.Controller;
import com.example.healthylife.Model.AlimentEntity;
import com.example.healthylife.R;
import com.example.healthylife.View.AutoCompleteText.CustomAutoCompleteTextChangedListener;
import com.example.healthylife.View.AutoCompleteText.CustomAutoCompleteView;

import java.util.List;

public class AddDialog extends AppCompatDialogFragment implements AdapterView.OnItemClickListener {

    public ArrayAdapter<AlimentEntity> data;
    public CustomAutoCompleteView nameTxt;
    private Controller controller;
    public List<AlimentEntity> items;
    private AlimentEntity aliment ;
    private Listener listener;

    private View viewDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        viewDialog = inflater.inflate(R.layout.add_popup,null);


        nameTxt = (CustomAutoCompleteView) viewDialog.findViewById(R.id.myautocomplete);

        nameTxt.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this,controller));

        data = new ArrayAdapter<AlimentEntity>(getActivity(), android. R.layout.simple_list_item_1, items);
        nameTxt.setAdapter(data);
        nameTxt.setOnItemClickListener(this);

        viewDialog.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addTodayAliment(aliment.getName(),aliment.getRecommendedType(),aliment.getKcals(),aliment.getLipids(),aliment.getProteins(),aliment.getCarbohydrates(),aliment.getFiber(),aliment.getSugar());
                dismiss();
            }
        });


        builder.setView(viewDialog);
        return builder.create();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        aliment = (AlimentEntity) parent.getItemAtPosition(position);
        TextView description = viewDialog.findViewById(R.id.descriptionLbl);
        TextView kcals = viewDialog.findViewById(R.id.kcalsLbl);
        TextView proteins = viewDialog.findViewById(R.id.proteinsLbl);
        TextView lipids = viewDialog.findViewById(R.id.lipidsLbl);
        TextView sugar = viewDialog.findViewById(R.id.sugarLbl);
        TextView carbohydrates = viewDialog.findViewById(R.id.carbohydratesLbl);
        TextView fiber = viewDialog.findViewById(R.id.fiberLbl);

        String descriptionTxt = "Description : "+ aliment.getRecommendedType();
        String kcalsTxt = "Kcals : "+String.valueOf(aliment.getKcals())+" g";
        String proteinsTxt = "Proteins : "+String.valueOf(aliment.getProteins())+" g";
        String lipidsTxt = "Lipids : "+String.valueOf(aliment.getLipids())+" g";
        String sugarTxt = "Sugar : " + String.valueOf(aliment.getSugar())+" g";
        String carboTxt = "Carbohydrates : " +String.valueOf(aliment.getCarbohydrates())+" g";
        String fiberTxt = "Fiber : "+String.valueOf(aliment.getFiber())+" g";

        description.setText(descriptionTxt);
        kcals.setText(kcalsTxt);
        proteins.setText(proteinsTxt);
        lipids.setText(lipidsTxt);
        sugar.setText(sugarTxt);
        carbohydrates.setText(carboTxt);
        fiber.setText(fiberTxt);
    }
}
