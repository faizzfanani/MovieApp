package com.kontrakanelite.movieapp.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kontrakanelite.movieapp.adapter.TabAdapter;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.fragment.movieList;
import com.kontrakanelite.movieapp.fragment.tvShowList;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "dicoding channel";
    public TabAdapter adapter;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

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
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    };
    public void sendNotification(View view) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_favorite_fill)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_favorite_fill))
                .setContentTitle("judul")
                .setContentText("context")
                .setSubText("subtext")
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
        new Handler().postDelayed(runnable, 5000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}