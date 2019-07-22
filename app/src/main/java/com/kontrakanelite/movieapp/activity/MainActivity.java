package com.kontrakanelite.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new movieList(), "Movies");
        adapter.addFragment(new tvShowList(), "TV Shows");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageView btnSetting = findViewById(R.id.iv_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });
    }
}