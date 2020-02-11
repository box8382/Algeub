package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RingActivity extends AppCompatActivity {

    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);

        Intent intent=getIntent();
        String name=intent.getExtras().getString("name");
        int hour=intent.getExtras().getInt("hour");
        int min=intent.getExtras().getInt("min");

        tv1.setText(hour+":"+min);
        tv2.setText(name);

    }
}
