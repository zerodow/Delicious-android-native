package com.example.admin.appquanan.adapter;

;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.appquanan.fragment.AllFragment;
import com.example.admin.appquanan.fragment.FavoriteFragment;
import com.example.admin.appquanan.model.User;

/**
 * Created by Admin on 10/27/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private User user;

    private Context context;

    public void setUser(User user) {
        this.user = user;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        switch (position) {
            case 0: {
                frag = new AllFragment();
                frag.setHasOptionsMenu(true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USERACCOUNT", user);
                frag.setArguments(bundle);
                break;

            }
            case 1: {
                frag = new FavoriteFragment();
                frag.setHasOptionsMenu(true);

                Bundle bundle = new Bundle();
                bundle.putSerializable("USERACCOUNT", user);
                frag.setArguments(bundle);
                break;
            }
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Đồ ăn";
                break;
            case 1:
                title = "Ưa thích";
                break;
        }

        return title;
    }
}
