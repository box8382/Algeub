package com.pmkproject.algeub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddListActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText name;      //알바이름
    EditText pay;       //시급
    TextView startTime; //시작 시간 tv
    int iStart=480;
    TextView lastTime;  //종료 시간 tv
    int iLast=1140;
    EditText memo;      //메모

    CheckBox checkBox1Delivery;
    EditText delivery;
    CheckBox checkBox2NightPay;
    CheckBox checkBox3Free;
    int iFree;

    TextView free;      //휴식시간

    ImageView iv;
    RelativeLayout config;
    boolean isConfig=false;

    SQLiteDatabase db; //데이터 베이스
    String dbName="Data.db";
    String tableName="list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        toolbar=findViewById(R.id.config_toolbar);
        setSupportActionBar(toolbar);

        //설정액티비티 앱 제목변경
        getSupportActionBar().setTitle("알바목록 추가");
        //뒤로가기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=findViewById(R.id.list_name);
        pay=findViewById(R.id.list_pay);
        startTime=findViewById(R.id.list_start_time);
        lastTime=findViewById(R.id.list_last_time);
        free=findViewById(R.id.list_free);
        iv=findViewById(R.id.list_iv);
        config=findViewById(R.id.list_config);
        memo=findViewById(R.id.list_memo);

        checkBox1Delivery=findViewById(R.id.check1_delivery);
        delivery=findViewById(R.id.list_delivery);
        checkBox2NightPay=findViewById(R.id.check2_nightpay);
        checkBox3Free=findViewById(R.id.check3_free);

        startTime.setOnClickListener(time);
        lastTime.setOnClickListener(time);

        name.addTextChangedListener(new listTextWatcher(name));
        pay.addTextChangedListener(new listTextWatcher(pay));

        //데이터베이스 파일 생성 또는 열기
        db=openOrCreateDatabase(dbName,MODE_PRIVATE,null);
        //db에는 boolean값이 없어서 0,1로 한다고함..

        //앞에서부터
        //알바이름값, 시급값, 시작시간값, 종료시간값, 메모값, 배달건당액수, 야간수당boolean, 휴식시간값
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_add_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //고급설정 클릭시
    public void clickConfig(View view) {
        if(isConfig==false) {
            iv.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);

            Animation animation1=AnimationUtils.loadAnimation(this,R.anim.creat_visible);
            config.setAnimation(animation1);
            config.startAnimation(animation1);
            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    config.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            isConfig = true;
        }else{
            iv.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            Animation animation2=AnimationUtils.loadAnimation(this,R.anim.delete_visible);
            config.setAnimation(animation2);
            config.startAnimation(animation2);
            animation2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    config.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            isConfig=false;
        }
    }

    boolean validateName(){
        if(name.getText().toString().trim().isEmpty()){
            name.setError("알바명을 입력해주세요");
            requestFocus(name);
            return false;
        }
        return true;
    }

    boolean validatePay(){
        if(pay.getText().toString().trim().isEmpty()){
            pay.setError("시급을 입력해주세요");
            requestFocus(pay);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    class listTextWatcher implements TextWatcher{

        View view;

        public listTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()){
                case R.id.list_name:
                    validateName();
                    break;
                case R.id.list_pay:
                    validatePay();
                    break;
            }
        }
    }




    //시간설정관련
    View.OnClickListener time=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.list_start_time:

                    TimePickerDialog startTimePickerDialog=new TimePickerDialog(AddListActivity.this,android.R.style.Theme_Material_Dialog, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            String startTimeFormat=String.format("%02d:%02d",hour,minute);
                            startTime.setText(startTimeFormat);
                            iStart=hour*60+minute; //저장할 시간값
                        }
                    },8,0,true);
                    startTimePickerDialog.setMessage("시작시간");
                    startTimePickerDialog.show();

                    break;
                case R.id.list_last_time:

                    TimePickerDialog lastTimePickerDialog=new TimePickerDialog(AddListActivity.this,android.R.style.Theme_Material_Dialog, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            String lastTimeFormat=String.format("%02d:%02d",hour,minute);
                            lastTime.setText(lastTimeFormat);
                            iLast=hour*60+minute; //저정할 시간값
                        }
                    },19,0,true);
                    lastTimePickerDialog.setMessage("종료시간");
                    lastTimePickerDialog.show();

                    break;
            }
        }
    };



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case  R.id.list_save:
                if(!validateName()){
                    break;
                }
                if(!validatePay()){
                    break;
                }
                //작성완료 했을때 save 기능
                new AlertDialog.Builder(this).setMessage("저장을 완료하시겠습니까?").setNegativeButton("취소",null).setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveData();
                        finish();
                    }
                }).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void saveData(){
        String listName=name.getText().toString();      //알바이름
        int listPay=Integer.parseInt(pay.getText().toString());        //시급
        int listStart=iStart;                           //시작시간 int 값 시간*60+분
        int listLast=iLast;                             //종료시간 int 값 시간*60+분
        String listMemo=memo.getText().toString();      //메모

        int listDelivery=0;
        int listFree=0;
        if(delivery.getText().toString()!=null && checkBox1Delivery.isChecked()) {
            listDelivery = Integer.parseInt(delivery.getText().toString());   //건당 액수
        }
        if(iFree!=0 && checkBox3Free.isChecked()){
            listFree=iFree;
        }

        int listNightPay=(checkBox2NightPay.isChecked())?1:0;


        //추가로 고급설정에 있는값도 저장해야댐

//        Log.e("SAVE","listName : "+listName);
//        Log.e("SAVE","listPay : "+listPay);
//        Log.e("SAVE","listStart : "+listStart);
//        Log.e("SAVE","listLast : "+listLast);
//        Log.e("SAVE","listMemo : "+listMemo);
//
//        Log.e("SAVE",checkBox1Delivery.isChecked()+" 배달");
//        Log.e("SAVE",listDelivery+" 건당 액수");
//        Log.e("SAVE",checkBox2NightPay.isChecked()+" 야간수당");
//        Log.e("SAVE",checkBox3Free.isChecked()+" 휴식시간");
//        Log.e("SAVE",listFree+" 총 시간");
        Intent intent=getIntent();
        int num=intent.getIntExtra("PaySize",0);
        intent.putExtra("ListName",listName);
        intent.putExtra("ListPay",listPay);
        intent.putExtra("ListStart",listStart);
        intent.putExtra("ListLast",listLast);
        intent.putExtra("ListMemo",listMemo);

        intent.putExtra("ListDelivery",listDelivery);
        intent.putExtra("ListNightPay",listNightPay);
        intent.putExtra("ListFree",listFree);


        Log.e("QQQ","추가할때 데이터베이스에 저장되는 값은 : "+ num);
        db.execSQL("INSERT INTO "+tableName+"(num, name, pay, start, last, memo, delivery, nightPay, free) " +
                "VALUES('"+num+"','"+listName+"','"+listPay+"','"+listStart+"','"+listLast+"','"+listMemo+"','"+listDelivery+"','"+listNightPay+"','"+listFree+"')");


        setResult(RESULT_OK,intent);
        db.close();
    }


    public void clickFree(View view) {
        TimePickerDialog startTimePickerDialog=new TimePickerDialog(AddListActivity.this,android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                free.setText("일한시간중 "+hour+" 시간 "+minute+" 분 제외");
                iFree=hour*60+minute;
            }
        },0,0,true);
        startTimePickerDialog.setMessage("휴식시간");
        startTimePickerDialog.show();
    }

}
