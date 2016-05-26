package me.codpoe.onlyweather.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> mStrings;
    private List<Fragment> mFragments;

    public MainFragmentAdapter(FragmentManager fragmentManager, List<String> strings, List<Fragment> fragments) {
        super(fragmentManager);
        mStrings = strings;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
