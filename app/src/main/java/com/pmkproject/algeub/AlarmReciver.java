package com.pmkproject.algeub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;

        this.context = context;
        // intent로부터 전달받은 string
        String name = intent.getExtras().getString("name");
        int hour = intent.getExtras().getInt("hour",0);
        int min = intent.getExtras().getInt("min",0);
        String[] weeks=intent.getExtras().getStringArray("weeks");

        // RingtonePlayingService 서비스 intent 생성
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        // RingtonePlayinService로 extra string값 보내기
        service_intent.putExtra("name", name);
        service_intent.putExtra("hour", hour);
        service_intent.putExtra("min", min);
        service_intent.putExtra("weeks", weeks);
        // start the ringtone service

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            this.context.startForegroundService(service_intent);
        }else{
            this.context.startService(service_intent);
        }
    }
}
