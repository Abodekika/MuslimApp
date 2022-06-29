package com.example.azkar.data.notification.prayernotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.azkar.R;

public class AzanNotification extends Worker {
    public static final String CHANNEL_ID = "AZAN_CHANNEL";
    public static final String CHANNEL_NAme = "azan_CHANNEL";
    Context context;

    public AzanNotification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }



    public NotificationCompat.Builder createNotificationBuilder
            (String title, String content, Uri sound) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        builder.setContentText(content)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()).
                setSound(sound)
                .setAutoCancel(true);
        return builder;
    }


    public static void createNotificationChannel(NotificationManager manager, Uri sound) {

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                CHANNEL_NAme,
                NotificationManager.IMPORTANCE_HIGH
        );

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        notificationChannel.setSound(sound, audioAttributes);


        manager.createNotificationChannel(notificationChannel);
    }

    public void sendNotification(String content,
                                 String title, Uri sound) {

        NotificationManager manager = (NotificationManager) getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);


        createNotificationChannel(manager, sound);
        NotificationCompat.Builder notificationBuilder = createNotificationBuilder(title, content, sound);


        manager.notify(0, notificationBuilder.build());
    }

    @NonNull
    @Override
    public Result doWork() {
        Uri azanSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/raw/azan.wav");
        Data input = getInputData();
        String title = input.getString("Title");
        String content = input.getString("Con");

        sendNotification(content, title, azanSound);
        return Result.success();
    }
}
