package com.hacksu.CookieStore.TabAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.hacksu.CookieStore.Fragments.AboutFragment;
import com.hacksu.CookieStore.Fragments.CartFragment;
import com.hacksu.CookieStore.Fragments.HomeRootFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
    private FragmentManager fragmentManager;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new HomeRootFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new AboutFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // shalt thou count to three, no more, no less. Three shall be the number thou shalt count,
        // and the number of the counting shall be three. Four shalt thou not count, neither count thou two,
        // excepting that thou then proceed to three. Five is right out.
        return 3;
    }

}