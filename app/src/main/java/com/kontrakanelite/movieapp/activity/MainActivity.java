package com.kontrakanelite.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kontrakanelite.movieapp.adapter.TabAdapter;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.fragment.movieList;
import com.kontrakanelite.movieapp.fragment.tvShowList;

public class MainActivity extends AppCompatActivity {
    public TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String tab1 = getString(R.string.tab1);
        String tab2 = getString(R.string.tab2);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new movieList(), tab1);
        adapter.addFragment(new tvShowList(), tab2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageView btnSetting = findViewById(R.id.iv_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}