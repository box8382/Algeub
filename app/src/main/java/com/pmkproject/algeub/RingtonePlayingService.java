package com.pmkproject.algeub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class RingtonePlayingService extends Service {


    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String name = intent.getExtras().getString("name");
        int hour= intent.getExtras().getInt("hour");
        int min = intent.getExtras().getInt("min");
        String[] weeks=intent.getStringArrayExtra("weeks");
        String sound=intent.getExtras().getString("sound");

//        Log.e("weeks",Calendar.getInstance().DAY_OF_WEEK+" 오늘 요일");
        int nWeek=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String nStringWeek;
        boolean ringring=false;
        switch (nWeek){
            case 1:
                nStringWeek="일";
                break;
            case 2:
                nStringWeek="월";
                break;
            case 3:
                nStringWeek="화";
                break;
            case 4:
                nStringWeek="수";
                break;
            case 5:
                nStringWeek="목";
                break;
            case 6:
                nStringWeek="금";
                break;
            case 7:
                nStringWeek="토";
                break;
                default:
                    nStringWeek="몰라";
                    break;
        }
        for(int i=0;i<weeks.length;i++){
            if(weeks[i].equals(nStringWeek)){
                ringring=true;
            }
        }

        if(ringring){
            if (Build.VERSION.SDK_INT >= 26) {
                String CHANNEL_ID = "default";
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);

                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("알람시작")
                        .setContentText(name+" 알람이 재생됩니다.")
                        .setSmallIcon(R.drawable.ic_appicon)

                        .build();

                startForeground(1, notification);
            }

            Intent ring = new Intent(this,RingActivity.class);
            ring.putExtra("name",name);
            ring.putExtra("hour",hour);
            ring.putExtra("min",min);
            ring.putExtra("sound",sound);
            startActivity(ring.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "알람 파괴", Toast.LENGTH_SHORT).show();
    }
}
