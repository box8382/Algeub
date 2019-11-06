package com.pmkproject.algeub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    NonSwipeViewPager mViewPager;
    TabLayout mTabLayout;
    AdapterFragment mAdapterFragment;

    Toolbar toolbar;

    SharedPreferences pref;
    String patternPassward;
    //설정파일명
    String fileConfig="Config";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //잠금화면 설정
        pref= getApplicationContext().getSharedPreferences(fileConfig,MODE_PRIVATE);
        patternPassward=pref.getString("passward",null);
        Log.e("TAG","비밀번호의 값은 : "+patternPassward);
        if(patternPassward!=null){
            //앱 잠금이 걸려있다면? 즉 저장되있는 비밀번호가 있다면?
            Intent intent=new Intent(this, PatternLockMainActivity.class);
            startActivityForResult(intent,10);
        }



        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager=findViewById(R.id.viewpager);
        mTabLayout=findViewById(R.id.tablayout);

        mAdapterFragment=new AdapterFragment(getSupportFragmentManager(),4);
        mViewPager.setAdapter(mAdapterFragment);


        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_list);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_alram);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_talk);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //정상적인 패턴을 풀고 왔을때

                }else{
                    //뒤로가기버튼으로 눌러서 메인에 왔을때 그냥 끝내버림
                    finish();
                }
                break;
        }
    }

}//MainActivity class..
