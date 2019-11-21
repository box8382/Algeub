package com.pmkproject.algeub;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DialogHome extends Dialog implements View.OnClickListener{

    private Context context;
    private int year;
    private int month;
    private int day;
    private int position;

    private DialogHomeListener dialogHomeListener;

    TitleTextView date;
    RecyclerView recyclerView;
    AdapterHomeDialog adapter;

    ImageView exit;
    Button add;
    Button delete;

    interface DialogHomeListener{
        void onPositiveClicked(int position);
        void onNegativeClicked();
    }
    public void setDialogHomeListener(DialogHomeListener dialogHomeListener){
        this.dialogHomeListener=dialogHomeListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DialogHome(@NonNull Context context, int year, int month, int day) {
        super(context);
        this.context=context;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_home);

        recyclerView=findViewById(R.id.dialog_home_recycler);
        adapter=new AdapterHomeDialog(context,this);
        recyclerView.setAdapter(adapter);

        date=findViewById(R.id.dialog_home_date);
        exit=findViewById(R.id.dialog_home_exit);
        add=findViewById(R.id.dialog_home_add);
        delete=findViewById(R.id.dialog_home_delete);


        date.setText(year+"년 "+month+"월 "+day+"일");

        delete.setOnClickListener(this);
        exit.setOnClickListener(this);
        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_home_add:
                dialogHomeListener.onPositiveClicked(position);
                dismiss();
                break;
            case R.id.dialog_home_delete:
                dialogHomeListener.onNegativeClicked();
                dismiss();
                break;
            case R.id.dialog_home_exit:
                dismiss();
                break;
        }

    }
}
