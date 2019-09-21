package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;


public class MatchTabbedActivity extends AppCompatActivity {


    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsPagerAdapter myTabsPagerAdapter;
    private int[] tabIcons = {
            R.drawable.ic_matchlist,
            R.drawable.ic_profile
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_tabbed);

        myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsPagerAdapter);
        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        setupTabIcons();


        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

               // tab.getIcon().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupTabIcons()
    {
         //   myTabLayout.addTab(myTabLayout.newTab().setText("Abc").setIcon(tabIcons[0]));
           // myTabLayout.addTab(myTabLayout.newTab().setText("Abc2").setIcon(tabIcons[1]));

       myTabLayout.getTabAt(0).setIcon(tabIcons[0]);
       myTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
