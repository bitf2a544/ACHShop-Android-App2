package com.zeeshanmac.zeeshan.spoofandroidapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshanmac.zeeshan.spoofandroidapp.Auth.EditProfileFragment;
import com.zeeshanmac.zeeshan.spoofandroidapp.Auth.TabsPagerAdapter;
import com.zeeshanmac.zeeshan.spoofandroidapp.fragment.DashBoardFragment;
import com.zeeshanmac.zeeshan.spoofandroidapp.fragment.SearchFragment;
import com.zeeshanmac.zeeshan.spoofandroidapp.fragment.ShoppingFragment;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.TransparentProgressDialog;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.inloop.localmessagemanager.LocalMessageManager;

import static com.zeeshanmac.zeeshan.spoofandroidapp.fragment.DashBoardFragment.UPDATE_ARTICLES;

public class MainActivity extends FragmentActivity {

    public static List<Items> selectedItemsList = new ArrayList<>();

    // Button btnNext;
    @BindView(R.id.leftIVD)
    ImageView leftIV;
    @BindView(R.id.rightIVD)
    ImageView rightIV;
    @BindView(R.id.centerTVD)
    TextView centerTV;

    private ViewPager myViewPager;
    private TabLayout myTabLayout;

    private TabsPagerAdapter myTabsPagerAdapter;
    private int[] tabIcons = {
            R.drawable.ic_matchlist,
            R.drawable.ic_profile
    };


    TransparentProgressDialog transparentProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        transparentProgressDialog = new TransparentProgressDialog(this, R.drawable.prosessing_icon);

        myViewPager = (ViewPager) findViewById(R.id.viewpager);
        myTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsPagerAdapter);
        myTabLayout = (TabLayout) findViewById(R.id.tabs);
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

        rightIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
        rightIV.setTag(R.drawable.ic_add_black_24dp);
        centerTV.setText("Articles");
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((Integer) rightIV.getTag() == R.drawable.ic_search_black_24dp) {


                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                   // intent.putExtra("selcted_item",item);
                    startActivity(intent);

                   // centerTV.setText("Searching");
                    //rightIV.setVisibility(View.INVISIBLE);

//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Fragment fragment = new SearchFragment();
//                    fragmentTransaction.replace(R.id.containerFLD, fragment,"SearchFragment_1").
//                            addToBackStack("SearchFragment").
//                            commit();
                }
            }
        });

//        getRecordsFromFirebaseDB();

        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocalMessageManager.getInstance().send(UPDATE_ARTICLES);
                onBackPressed();
            }
        });

    }

    private void setupTabIcons() {
        myTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        myTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    public void updateFragment(String fragmentText) {
        centerTV.setText(fragmentText);
        rightIV.setVisibility(View.VISIBLE);
        rightIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
        rightIV.setTag(R.drawable.ic_search_black_24dp);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new ShoppingFragment();
        fragmentTransaction.replace(R.id.containerFLD, fragment,"ShoppingFragment_1").
                addToBackStack("ShoppingFragment").
                commit();
    }
}
