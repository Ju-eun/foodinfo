package com.project.foodinfo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.project.foodinfo.Fragment_info;
import com.project.foodinfo.Fragment_map;
import com.project.foodinfo.Fragment_menu;
import com.project.foodinfo.Fragment_review;

public class MenuPagerUserAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Fragment_menu_user fragment_menu_user;

    public MenuPagerUserAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    public MenuPagerUserAdapter(FragmentManager fm, int tabCount, Fragment_menu_user fragment_menu_user) {
        super(fm);
        this.tabCount = tabCount;
        this.fragment_menu_user = fragment_menu_user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_menu_user menu = new Fragment_menu_user();
                return menu;
            case 1:
                Fragment_info_user info = new Fragment_info_user();
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