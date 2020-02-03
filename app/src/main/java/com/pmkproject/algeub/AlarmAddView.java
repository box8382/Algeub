package com.pmkproject.algeub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmAddView extends AppCompatActivity {

    Toolbar toolbar;

    TimePicker timePicker;
    RelativeLayout clickRepeat,clickName,clickSound;
    TextView repeatTv,nameTv,soundTv;
    ArrayList<String> repeatList=new ArrayList<>();
    String[] items=new String[]{"월","화","수","목","금","토","일"};
    boolean[] choiceRepeatBool=new boolean[]{false,false,false,false,false,false,false};
    Button save,cancel;

    String repeat="안함";  //요일
    String name="알람";    //알람 제목
    Uri sound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);      //알람 uri


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add_view);

        timePicker=findViewById(R.id.alarm_view_picker);
        toolbar=findViewById(R.id.alarm_view_toolbar);
        clickRepeat=findViewById(R.id.alarm_view_repeat);
        clickName=findViewById(R.id.alarm_view_name);
        clickSound=findViewById(R.id.alarm_view_sound);

        repeatTv=findViewById(R.id.alarm_view_repeat_tv);
        nameTv=findViewById(R.id.alarm_view_name_tv);
        soundTv=findViewById(R.id.alarm_view_sound_tv);

        save=findViewById(R.id.alarm_view_save);
        cancel=findViewById(R.id.alarm_view_cancel);

        clickRepeat.setOnClickListener(alarmListener);
        clickName.setOnClickListener(alarmListener);
        clickSound.setOnClickListener(alarmListener);

        soundTv.setText(RingtoneManager.getRingtone(this,sound).getTitle(this));

        save.setOnClickListener(saveListener);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("알람 추가");
    }

    View.OnClickListener saveListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(repeat=="안함"){
                Toast.makeText(AlarmAddView.this, "반복을 설정해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                // 24시간체계로 시간을 줌
                Toast.makeText(AlarmAddView.this, "시간 : "+timePicker.getCurrentHour()+","+timePicker.getCurrentMinute()+"요일 : "+repeat+" 알람이름 : "+name+" 사운드 : "+
                        RingtoneManager.getRingtone(AlarmAddView.this,sound).getTitle(AlarmAddView.this), Toast.LENGTH_SHORT).show();

                //데이터 베이스 (sqlite) 추가하면 됨



            }

        }
    };

    View.OnClickListener alarmListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.alarm_view_repeat:
                    repeatDialog();
                    break;
                case R.id.alarm_view_name:
                    nameDialog();
                    break;
                case R.id.alarm_view_sound:
                    soundClick();
                    break;
            }
        }
    };

    private void repeatDialog(){
        AlertDialog.Builder repeatDialog=new AlertDialog.Builder(AlarmAddView.this);
        repeatDialog.setIcon(R.drawable.ic_access_time_black_24dp);
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
                    if(repeatList.size()-1==i) t.append(repeatList.get(i));
                    else t.append(repeatList.get(i)+", ");
                }
                repeatTv.setText(t.toString());

                repeat=repeatTv.getText().toString();
                if(repeatList.size()==0) repeatTv.setText("안함");

            }
        }).create().show();
    }

    private void nameDialog(){
        AlertDialog.Builder nameDialog=new AlertDialog.Builder(AlarmAddView.this);
        EditText et=new EditText(AlarmAddView.this);
        et.setText(nameTv.getText());
        et.setMaxLines(1);
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        nameDialog.setIcon(R.drawable.ic_assignment_black_24dp);
        nameDialog.setTitle("알람 이름 변경").setMessage("변경할 이름을 입력하세요").setView(et);
        nameDialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nameTv.setText(et.getText());
                if(et.getText().toString().equals("")) nameTv.setText("알람");
                name=nameTv.getText().toString();
            }
        }).create().show();
    }

    private void soundClick(){
        Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,"알람음 설정");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT,true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
        startActivityForResult(intent,50);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==50){
                //알람음 재생하는 코드
                Uri ring= data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                sound=ring;

                if(ring!=null){
                    soundTv.setText(RingtoneManager.getRingtone(this,ring).getTitle(this));
                }
            }
        }
    }

}
