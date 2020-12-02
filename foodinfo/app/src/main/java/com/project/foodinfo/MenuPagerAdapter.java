package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.project.foodinfo.Fragment_info;
import com.project.foodinfo.Fragment_map;
import com.project.foodinfo.Fragment_menu;
import com.project.foodinfo.Fragment_review;

public class MenuPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MenuPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_menu menu = new Fragment_menu();
                return menu;
            case 1:
                Fragment_info info = new Fragment_info();
                return info;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}