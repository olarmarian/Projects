package com.example.healthylife.View;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.healthylife.Controller.Controller;
import com.example.healthylife.Model.AlimentEntity;
import com.example.healthylife.Model.AlimentRepository;
import com.example.healthylife.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TodayTab extends Fragment implements Listener {
    private static DecimalFormat df = new DecimalFormat("0.00");
    private TextView kcalsTxt;
    private TextView proteinsTxt;
    private TextView lipidsTxt;
    private TextView sugarTxt;
    private TextView fiberTxt;
    private TextView carboTxt;

    private Controller controller;
    private Listener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todays_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kcalsTxt = (TextView)view.findViewById(R.id.value_kcals_for_today);
        carboTxt = (TextView)view.findViewById(R.id.value_carbohydrates_for_today);
        fiberTxt = (TextView)view.findViewById(R.id.value_fiber_for_today);
        lipidsTxt = (TextView)view.findViewById(R.id.value_lipids_for_today);
        proteinsTxt = (TextView)view.findViewById(R.id.value_proteins_for_today);
        sugarTxt = (TextView)view.findViewById(R.id.value_sugar_for_today);

        String resetTime = "17:41:00";
        Date d = null;
        try {
            d = new SimpleDateFormat("HH:mm:ss").parse(resetTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        Date x = calendar.getTime();
        if (x.after(d)) {
            try {
                controller.deleteTodayAliments(controller.getTodayAliments());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<AlimentEntity> todayAliments = new ArrayList<>();
        try {
            todayAliments = controller.getTodayAliments();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(todayAliments.size()>0){
            TextView emptyLbl = view.findViewById(R.id.emptyLbl);
            double kcals_g = 0;
            double lipids_g = 0;
            double proteins_g = 0;
            double fiber_g = 0;
            double sugar_g = 0;
            double carbo_g = 0;
            for(AlimentEntity ae : todayAliments){
                kcals_g += ae.getKcals();
                lipids_g += ae.getLipids();
                proteins_g += ae.getProteins();
                fiber_g += ae.getFiber();
                sugar_g += ae.getSugar();
                carbo_g += ae.getCarbohydrates();
            }
            df.setRoundingMode(RoundingMode.DOWN);
            kcalsTxt.setText(String.valueOf(df.format(kcals_g)));
            proteinsTxt.setText(String.valueOf(df.format(proteins_g)));
            lipidsTxt.setText(String.valueOf(df.format(lipids_g)));
            carboTxt.setText(String.valueOf(df.format(carbo_g)));
            fiberTxt.setText(String.valueOf(df.format(fiber_g)));
            sugarTxt.setText(String.valueOf(df.format(sugar_g)));
            emptyLbl.setText("");
        }else{
            TextView emptyLbl = view.findViewById(R.id.emptyLbl);
            kcalsTxt.setText(String.valueOf(0));
            proteinsTxt.setText(String.valueOf(0));
            lipidsTxt.setText(String.valueOf(0));
            carboTxt.setText(String.valueOf(0));
            fiberTxt.setText(String.valueOf(0));
            sugarTxt.setText(String.valueOf(0));
            emptyLbl.setText("Empty");
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(todayAliments);
        recyclerView.setAdapter(recyclerViewAdapter);


        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void setController(Controller ctrl){this.controller = ctrl;}
    @Override
    public void addTodayAliment(String name, String recommendedType, double kcals, double lipids, double proteins, double carbohydrates, double fiber, double sugar) {
        controller.addTodayAliment( name,  recommendedType,  kcals,  lipids,  proteins,  carbohydrates,  fiber,  sugar);
        String newKcals = String.valueOf(df.format(Double.parseDouble(kcalsTxt.getText().toString().replace(",","."))+kcals));
        String newProteins = String.valueOf(df.format(Double.parseDouble(proteinsTxt.getText().toString().replace(",","."))+proteins));
        String newLipids = String.valueOf(df.format(Double.parseDouble(lipidsTxt.getText().toString().replace(",","."))+lipids));
        String newCarbo = String.valueOf(df.format(Double.parseDouble(carboTxt.getText().toString().replace(",","."))+carbohydrates));
        String newFiber = String.valueOf(df.format(Double.parseDouble(fiberTxt.getText().toString().replace(",","."))+fiber));
        String newSugar = String.valueOf(df.format(Double.parseDouble(sugarTxt.getText().toString().replace(",","."))+sugar));

        kcalsTxt.setText(newKcals);
        proteinsTxt.setText(newProteins);
        lipidsTxt.setText(newLipids);
        carboTxt.setText(newCarbo);
        fiberTxt.setText(newFiber);
        fiberTxt.setText(newSugar);
    }

    //25,30
    public void openDialog(){
        AddDialog dialog = new AddDialog();
        dialog.setController(this.controller);
        dialog.setListener(this);
        dialog.show(getFragmentManager(),"addDialog");
    }
}