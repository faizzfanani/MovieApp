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
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.activity.DetailFilmActivity;
import com.kontrakanelite.movieapp.activity.MainActivity;
import com.kontrakanelite.movieapp.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver{

    public static final String TYPE_DAILY_REMINDER = "DailyReminder";
    public static final String TYPE_RELEASE_TODAY_REMINDER = "ReleaseTodayReminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    ArrayList<MovieModel>movieModels;
    private final int ID_DAILY = 100;
    private final int ID_UPCOMING = 101;

    int notifId;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        assert type != null;
        String title = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? TYPE_DAILY_REMINDER : TYPE_RELEASE_TODAY_REMINDER;
        notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY : ID_UPCOMING;
        if (type.equalsIgnoreCase(TYPE_DAILY_REMINDER))
            showDailyNotification(context, title, message, notifId);
        else if (type.equalsIgnoreCase(TYPE_RELEASE_TODAY_REMINDER)){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String sDate = dateFormat.format(date);

            getMovieRelease(context, "2019-09-06");
            getTvShowRelease(context, "2019-09-28");
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
        int ID_DAILY = 102;
        int ID_UPCOMING = 103;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type.equalsIgnoreCase(TYPE_DAILY_REMINDER)? ID_DAILY : ID_UPCOMING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    private void showDailyNotification(Context context, String title, String message, int notifId) {
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
    private void showUpcomingMovie(Context context, String title, MovieModel movie, Bitmap bitmap, int notifId){
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
    private void showUpcomingTvShow(Context context, String title, MovieModel tvShow, Bitmap bitmap, int notifId){
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
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY : ID_UPCOMING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void getMovieRelease(final Context context, String date){
        String URL_MOVIE_RELEASE = "https://api.themoviedb.org/3/discover/movie?api_key=bda489bfab0d87f4b3c4af88e206e0a4&primary_release_date.gte="+date+"&primary_release_date.lte="+date;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL_MOVIE_RELEASE, null, new Response.Listener
                <JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.optJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movie = jsonArray.optJSONObject(i);
                        movieModels.add(new MovieModel(
                                movie.getString("id"),
                                movie.getString("title"),
                                movie.getString("overview"),
                                movie.getString("vote_average"),
                                movie.getString("release_date"),
                                movie.getString("poster_path")
                        ));
                        Log.i("releasenotif",movie.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(context));
        requestQueue.add(objectRequest);
        setMovies(context, movieModels, notifId);
    }
    public void getTvShowRelease(Context context,String date){
        String URL_TVSHOW_RELEASE = "https://api.themoviedb.org/3/discover/tv?api_key=bda489bfab0d87f4b3c4af88e206e0a4&primary_release_date.gte="+date+"&primary_release_date.lte="+date;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL_TVSHOW_RELEASE, null, new Response.Listener
                <JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.optJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movie = jsonArray.optJSONObject(i);
                        movieModels.add(new MovieModel(
                            movie.getString("id"),
                            movie.getString("name"),
                            movie.getString("overview"),
                            movie.getString("vote_average"),
                            movie.getString("first_air_date"),
                            movie.getString("poster_path")
                        ));
                        Log.i("releasenotif",movie.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(context));
        requestQueue.add(objectRequest);
        setTvShow(context, movieModels, notifId);
    }

    public void setMovies(final Context context, final ArrayList<MovieModel> movies, final int notifId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = notifId;
                for (MovieModel movie:movies){
                    try {
                        URL url = new URL(movie.getImage());
                        showUpcomingMovie(context,context.getString(R.string.new_release),movie, BitmapFactory.decodeStream(url.openConnection().getInputStream()),id);
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
                        showUpcomingTvShow(context,context.getString(R.string.new_release),tvShow, BitmapFactory.decodeStream(url.openConnection().getInputStream()),id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    id++;
                }
            }
        }).start();
    }
}
