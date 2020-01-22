package com.pmkproject.algeub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmAddView extends AppCompatActivity {

    Toolbar toolbar;

    RelativeLayout clickRepeat,clickName,clickSound;
    TextView repeatTv,nameTv,soundTv;
    ArrayList<String> repeatList=new ArrayList<>();
    String[] items=new String[]{"월","화","수","목","금","토","일"};
    boolean[] choiceRepeatBool=new boolean[]{false, false, false, false, false, false, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add_view);

        toolbar=findViewById(R.id.alarm_view_toolbar);
        clickRepeat=findViewById(R.id.alarm_view_repeat);
        clickName=findViewById(R.id.alarm_view_name);
        clickSound=findViewById(R.id.alarm_view_sound);

        repeatTv=findViewById(R.id.alarm_view_repeat_tv);
        nameTv=findViewById(R.id.alarm_view_name_tv);
        soundTv=findViewById(R.id.alarm_view_sound_tv);

        clickRepeat.setOnClickListener(alarmListener);
        clickName.setOnClickListener(alarmListener);
        clickSound.setOnClickListener(alarmListener);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("알람 추가");
    }

    View.OnClickListener alarmListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.alarm_view_repeat:
                    repeatDialog();
                    break;
                case R.id.alarm_view_name:

                    break;
                case R.id.alarm_view_sound:

                    break;
            }
        }
    };

    private void repeatDialog(){
        AlertDialog.Builder repeatDialog=new AlertDialog.Builder(AlarmAddView.this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        repeatDialog.setTitle("요일을 선택하세요").setMultiChoiceItems(items,choiceRepeatBool, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) repeatList.add(items[which]);
                else repeatList.remove(items[which]);
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer t= new StringBuffer();
                for(int i=0;i<repeatList.size();i++){
                    if(repeatList.size()-1==i) t.append(repeatList.get(i)+"  ▶");
                    else t.append(repeatList.get(i)+", ");
                }
                repeatTv.setText(t.toString());
            }
        }).create().show();
    }


}
