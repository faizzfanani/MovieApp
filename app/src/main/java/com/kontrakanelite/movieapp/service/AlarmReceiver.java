package com.kontrakanelite.movieapp.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.activity.DetailFilmActivity;
import com.kontrakanelite.movieapp.activity.MainActivity;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver{

    public static final String TYPE_DAILY_REMINDER = "DailyReminder";
    public static final String TYPE_RELEASE_TODAY_REMINDER = "ReleaseTodayReminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private final int ID_DAILY = 100;
    private final int ID_RELEASE = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        assert type != null;
        String title = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? TYPE_DAILY_REMINDER : TYPE_RELEASE_TODAY_REMINDER;
        int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY : ID_RELEASE;
        if (type.equalsIgnoreCase(TYPE_DAILY_REMINDER))
            showDailyAlarmNotification(context, title, message, notifId);
        else if (type.equalsIgnoreCase(TYPE_RELEASE_TODAY_REMINDER)){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String sDate = dateFormat.format(date);

//            setMovies(context, );
//            setTvShow();
        }
    }

    public void setRepeatingAlarm(Context context, String type, String time , String message) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        int ID_DAILY_ALARM = 102;
        int ID_RELEASE_ALARM = 103;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type.equalsIgnoreCase(TYPE_DAILY_REMINDER)? ID_DAILY_ALARM : ID_RELEASE_ALARM, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    private void showDailyAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmReceiver channel";
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }
    private void showReleaseMovieAlarmNotification(Context context, String title, MovieModel movie, Bitmap bitmap, int notifId){
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmReceiver channel";
        Intent intent = new Intent(context, DetailFilmActivity.class);
        intent.putExtra(DetailFilmActivity.MOVIE_EXTRA,movie);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(movie.getTitle())
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setLargeIcon(bitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }
    private void showReleaseTvShowAlarmNotification(Context context, String title, MovieModel tvShow, Bitmap bitmap, int notifId){
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmReceiver channel";
        Intent intent = new Intent(context, DetailFilmActivity.class);
        intent.putExtra(DetailFilmActivity.TVSHOW_EXTRA,tvShow);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(tvShow.getTitle())
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setLargeIcon(bitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }
    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

//    public void requestMovies(String date) {
//        ApiService.getInstance().getTodaysMoviesRelease(date,date).enqueue(new Callback<RootMovieResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<RootMovieResponse> call,@NonNull Response<RootMovieResponse> response) {
//                assert response.body() != null;
//                view.setMovies(context,response.body().getResults(),notifId);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RootMovieResponse> call,@NonNull Throwable t) {
//
//            }
//        });
//    }
//
//    public void requestTvShows(String date) {
//        ApiService.getInstance().getTodaysTvShowsRelease(date,date).enqueue(new Callback<RootTvShowResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<RootTvShowResponse> call,@NonNull Response<RootTvShowResponse> response) {
//                assert response.body() != null;
//                view.setTvShow(context,response.body().getResults(),notifId);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RootTvShowResponse> call,@NonNull Throwable t) {
//
//            }
//        });
//    }
    public void setMovies(final Context context, final ArrayList<MovieModel> movies, final int notifId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = notifId;
                for (MovieModel movie:movies){
                    try {
                        URL url = new URL(movie.getImage());
                        showReleaseMovieAlarmNotification(context,context.getString(R.string.new_release),movie, BitmapFactory.decodeStream(url.openConnection().getInputStream()),id);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    id++;
                }
            }
        }).start();
    }

    public void setTvShow(final Context context, final ArrayList<MovieModel> tvShows, final int notifId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = notifId;
                for (MovieModel tvShow:tvShows){
                    try {
                        URL url = new URL(tvShow.getImage());
                        showReleaseTvShowAlarmNotification(context,context.getString(R.string.new_release),tvShow, BitmapFactory.decodeStream(url.openConnection().getInputStream()),id);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    id++;
                }
            }
        }).start();
    }
}
