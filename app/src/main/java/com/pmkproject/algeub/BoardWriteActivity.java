package com.pmkproject.algeub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardWriteActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView tvWriter;  //글 작성자 닉네임(이메일 앞부분임..)
    TextView tvTitle;   //글 제목
    TextView titleSize; //글 제목 글자 제한 30
    TextView tvContent; //내용
    TextView contentSize;//내용의 글제 제한 500

    int count;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        toolbar=findViewById(R.id.board_add_toolbar);
        setSupportActionBar(toolbar);

        tvWriter=findViewById(R.id.board_add_writer);
        tvTitle=findViewById(R.id.board_add_title);
        titleSize=findViewById(R.id.board_add_title_size);
        tvContent=findViewById(R.id.board_add_content);
        contentSize=findViewById(R.id.board_add_content_size);

        intent=getIntent();
        count=intent.getIntExtra("num",0);
        tvWriter.setText(intent.getStringExtra("writer")+" 님의 게시글 작성공간 입니다");



        tvTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                int size=tvTitle.getText().toString().length();
                titleSize.setText(size+"/30");

            }
        });

        tvContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                int size=tvContent.getText().toString().length();
                contentSize.setText(size+"/500");
            }
        });

    }


    public void cancel(View view) {
        new AlertDialog.Builder(this).setMessage("작성을 취소하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("취소",null).create().show();
    }

    public void post(View view) {

        new AlertDialog.Builder(this).setMessage("게시글을 작성하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadData();
            }
        }).setNegativeButton("취소",null).create().show();

    }


    public void uploadData(){
        String serverUrl="http://boxoun.dothome.co.kr/Algeub/insertDB.php";

        String nick=G.user.getEmail().split("@")[0];
        String title=tvTitle.getText().toString();
        String content=tvContent.getText().toString();
        Log.e("qqq",tvTitle.getText().toString());
        SimpleDateFormat sdfNow=new SimpleDateFormat("yyyy/MM/dd");
        String date=sdfNow.format(new Date());


        SimpleMultiPartRequest smpr=new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //new AlertDialog.Builder(BoardWriteActivity.this).setMessage(response).create().show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러 발생
                //Toast.makeText(BoardWriteActivity.this, "에러!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("nick",nick);
        smpr.addStringParam("title",title);
        smpr.addStringParam("content",content);
        smpr.addStringParam("date",date);


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);
    }
}
