package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class RingActivity extends AppCompatActivity {

    TextView ringName,ringTime;
    CardView ringStop;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        ringName=findViewById(R.id.ring_name);
        ringTime=findViewById(R.id.ring_time);
        ringStop=findViewById(R.id.ring_stop);

        Intent intent=getIntent();
        String name=intent.getExtras().getString("name");
        int hour=intent.getExtras().getInt("hour");
        int min=intent.getExtras().getInt("min");
        Uri sound=Uri.parse(intent.getExtras().getString("sound"));
//        Toast.makeText(this, "sound uri : "+sound, Toast.LENGTH_SHORT).show();

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this,sound);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();


        String time=String.format("%02d:%02d",hour,min);
        ringName.setText(time);
        ringTime.setText(name);
        ringStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                finish();
            }
        });

    }
}
