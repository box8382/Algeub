package com.pmkproject.algeub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

public class BoardSActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView tvNo;
    TextView tvTitle;
    TextView tvContent;
    TextView tvWriter;
    TextView tvDate;

    String writer;

    Intent intent;
    int no;     //키값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_s);

        tvNo = findViewById(R.id.boards_no);
        tvTitle = findViewById(R.id.boards_title);
        tvContent = findViewById(R.id.boards_content);
        tvWriter = findViewById(R.id.boards_writer);
        tvDate = findViewById(R.id.boards_date);
        toolbar = findViewById(R.id.boards_toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();

        no = intent.getIntExtra("No", 0);
        int num = intent.getIntExtra("Num", 0);
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        writer = intent.getStringExtra("Writer");
        String date = intent.getStringExtra("Date");

        tvNo.setText("no." + num);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvWriter.setText(writer + " 님의 게시글 입니다");
        tvDate.setText(date);

    }

    public void delete(View view) {
        if (G.user.getEmail().split("@")[0].equals(writer)) {
            //no값을 이용해 데이터삭제

            new AlertDialog.Builder(this).setMessage("글을 삭제하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteData();
                }
            }).setNegativeButton("취소",null).create().show();


        }else {
            Toast.makeText(this, "계정이 일치하지않습니다", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteData() {

        String serverUrl="http://boxoun.dothome.co.kr/Algeub/deleteDB.php";

        SimpleMultiPartRequest smpr=new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //new AlertDialog.Builder(BoardSActivity.this).setMessage(response).create().show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러 발생
                //Toast.makeText(BoardSActivity.this, "에러!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("no",no+"");

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);

    }

}
