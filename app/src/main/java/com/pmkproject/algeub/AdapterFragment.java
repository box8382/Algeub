package com.pmkproject.algeub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterFragment extends FragmentPagerAdapter {

    private int mPageCount;
    private Fragment[] fragments=new Fragment[4];

    public AdapterFragment(@NonNull FragmentManager fm, int pageCount) {
        super(fm,pageCount);
        this.mPageCount=pageCount;

        fragments[0]=new FragmentHome();
        fragments[1]=new FragmentList();
        fragments[2]=new FragmentAlram();
        fragments[3]=new FragmentTalk();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

}
