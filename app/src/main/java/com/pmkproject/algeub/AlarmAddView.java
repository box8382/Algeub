package com.pmkproject.algeub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
                    nameDialog();
                    break;
                case R.id.alarm_view_sound:

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

            }
        }).create().show();

    }


}
