package com.pmkproject.algeub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterFragment extends FragmentPagerAdapter {

    private int mPageCount;

    public AdapterFragment(@NonNull FragmentManager fm, int pageCount) {
        super(fm,pageCount);
        this.mPageCount=pageCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentHome fragmentHome=new FragmentHome();
                return fragmentHome;
            case 1:
                FragmentList fragmentList=new FragmentList();
                return fragmentList;
            case 2:
                FragmentAlram fragmentAlram=new FragmentAlram();
                return fragmentAlram;
            case 3:
                FragmentTalk fragmentTalk=new FragmentTalk();
                return fragmentTalk;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

}
