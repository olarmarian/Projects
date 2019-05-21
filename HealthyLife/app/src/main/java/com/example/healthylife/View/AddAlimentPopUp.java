//package com.example.healthylife.View;
//
//import android.annotation.SuppressLint;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.healthylife.Controller.Controller;
//import com.example.healthylife.Model.AlimentEntity;
//import com.example.healthylife.Model.AlimentRepository;
//import com.example.healthylife.R;
//import com.example.healthylife.View.AutoCompleteText.CustomAutoCompleteTextChangedListener;
//import com.example.healthylife.View.AutoCompleteText.CustomAutoCompleteView;
//import com.miguelcatalan.materialsearchview.MaterialSearchView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AddAlimentPopUp extends AppCompatActivity implements AdapterView.OnItemClickListener{
//    private Listener listener;
//    public ArrayAdapter<AlimentEntity> data;
//    public CustomAutoCompleteView nameTxt;
//    private Controller controller;
//    public List<AlimentEntity> items;
//    private AlimentEntity aliment ;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_popup);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        items = new ArrayList<>();
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        controller = new Controller(new AlimentRepository(this));
//        getWindow().setLayout((int)(width*.8),(int)(height*0.7));
//
//        nameTxt = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);
//
//        nameTxt.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this,controller));
//
//        data = new ArrayAdapter<AlimentEntity>(this, android. R.layout.simple_list_item_1, items);
//        nameTxt.setAdapter(data);
//        nameTxt.setOnItemClickListener(this);
//
//        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                controller.addTodayAliment(aliment.getName(),aliment.getRecommendedType(),aliment.getKcals(),aliment.getLipids(),aliment.getProteins(),aliment.getCarbohydrates(),aliment.getFiber(),aliment.getSugar());
//                finish();
////                setResult(AddAlimentPopUp.RESULT_OK);
//            }
//        });
//
//
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.x = 0;
//        params.y = -20;
//        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
//        getWindow().setAttributes(params);
//    }
//
//    public void setController(Controller controller) {
//        this.controller = controller;
//    }
//
//    public void setListener(Listener listener){
//        this.listener = listener;
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        aliment = (AlimentEntity) parent.getItemAtPosition(position);
//        TextView description = findViewById(R.id.descriptionLbl);
//        TextView kcals = findViewById(R.id.kcalsLbl);
//        TextView proteins = findViewById(R.id.proteinsLbl);
//        TextView lipids = findViewById(R.id.lipidsLbl);
//        TextView sugar = findViewById(R.id.sugarLbl);
//        TextView carbohydrates = findViewById(R.id.carbohydratesLbl);
//        TextView fiber = findViewById(R.id.fiberLbl);
//
//        String descriptionTxt = "Description : "+ aliment.getRecommendedType();
//        String kcalsTxt = "Kcals : "+String.valueOf(aliment.getKcals())+" g";
//        String proteinsTxt = "Proteins : "+String.valueOf(aliment.getProteins())+" g";
//        String lipidsTxt = "Lipids : "+String.valueOf(aliment.getLipids())+" g";
//        String sugarTxt = "Sugar : " + String.valueOf(aliment.getSugar())+" g";
//        String carboTxt = "Carbohydrates : " +String.valueOf(aliment.getCarbohydrates())+" g";
//        String fiberTxt = "Fiber : "+String.valueOf(aliment.getFiber())+" g";
//
//        description.setText(descriptionTxt);
//        kcals.setText(kcalsTxt);
//        proteins.setText(proteinsTxt);
//        lipids.setText(lipidsTxt);
//        sugar.setText(sugarTxt);
//        carbohydrates.setText(carboTxt);
//        fiber.setText(fiberTxt);
//    }
//}
