package com.example.gluck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;

public class TabsAcessorAdapter extends FragmentPagerAdapter {

    public TabsAcessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BarFragment barFragment = new BarFragment();
                return barFragment;
            case 1:

                RestaurantFragment restaurantFragment = new RestaurantFragment();
                return restaurantFragment;
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return 2;
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Bar";
            case 1:
                return "Restaurant";
            default:
                return null;
        }
    }
}
