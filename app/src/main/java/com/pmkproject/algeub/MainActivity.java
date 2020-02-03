package com.pmkproject.algeub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    NonSwipeViewPager mViewPager;
    TabLayout mTabLayout;
    AdapterFragment mAdapterFragment;

    Toolbar toolbar;

    SharedPreferences pref;
    String patternPassward;
    //설정파일명
    String fileConfig="Config";

    //NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String[] permisstions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permisstions,100);
            }
        }



        //잠금화면 설정
        pref= getApplicationContext().getSharedPreferences(fileConfig,MODE_PRIVATE);
        patternPassward=pref.getString("passward",null);
        Log.e("TAG","비밀번호의 값은 : "+patternPassward);
        if(patternPassward!=null){
            //앱 잠금이 걸려있다면? 즉 저장되있는 비밀번호가 있다면?
            Intent intent=new Intent(this, PatternLockMainActivity.class);
            startActivityForResult(intent,10);
        }

        mViewPager=findViewById(R.id.viewpager);
        mTabLayout=findViewById(R.id.tablayout);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer);
        //nav=findViewById(R.id.nav_view);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==3){
                    toggle.setDrawerIndicatorEnabled(true);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                }else{
                    toggle.setDrawerIndicatorEnabled(false);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



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
