package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    NonSwipeViewPager mViewPager;
    TabLayout mTabLayout;
    AdapterFragment mAdapterFragment;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


}//MainActivity class..
