package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zeeshanmac.zeeshan.spoofandroidapp.fragment.DashBoardFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:

                DashBoardFragment existingMatchFragment = new DashBoardFragment();
                return existingMatchFragment;

            case 1:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                return editProfileFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position)
        {
            case 0:
                return "Matches";
            case 1:
                return "Profile";
            default:
                return null;
        }
    }
}
