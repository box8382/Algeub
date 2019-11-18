package com.pmkproject.algeub;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DialogHome {

    private Context context;
    private int year;
    private int month;
    private int day;

    TitleTextView date;
    RecyclerView recyclerView;
    AdapterHomeDialog adapter;

    ImageView exit;
    Button add;

    public DialogHome(Context context,int year,int month, int day) {
        this.context = context;
        this.year=year;
        this.month = month;
        this.day = day;
    }

    public void callFunction(){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_home);

        //맨뒤에 값은 체크박스 boolean값인데 아직 생각중임... Adapter에 아직 값도 추가안함

        recyclerView=dialog.findViewById(R.id.dialog_home_recycler);
        adapter=new AdapterHomeDialog(context);
        recyclerView.setAdapter(adapter);

        date=dialog.findViewById(R.id.dialog_home_date);
        exit=dialog.findViewById(R.id.dialog_home_exit);
        add=dialog.findViewById(R.id.dialog_home_add);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        date.setText(year+"년 "+month+"월 "+day+"일");



        //액티비티의 타이틀바를 숨긴다? (왜쓰는지 모르겠음)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);




        dialog.show();
    }

}
