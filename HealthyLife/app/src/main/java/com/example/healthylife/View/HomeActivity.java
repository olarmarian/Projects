package com.example.healthylife.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.healthylife.Controller.Controller;
import com.example.healthylife.Model.AlimentRepository;
import com.example.healthylife.R;


public class HomeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller(new AlimentRepository(this));

        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        TodayTab tt = new TodayTab();
        tt.setController(controller);

        SuggestedTab st = new SuggestedTab();
//        st.setController(controller);

        adapter.addFragment(tt,"Today");
        adapter.addFragment(st,"Suggested");
        viewPager.setAdapter(adapter);
    }
}
