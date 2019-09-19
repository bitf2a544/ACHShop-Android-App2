package com.zeeshanmac.zeeshan.spoofandroidapp;

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

public class MainActivity extends FragmentActivity {
    //   private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SharedPreferences sharedpreferences;

    public static List<Items> selectedItemsList = new ArrayList<>();

    // Button btnNext;
    @BindView(R.id.leftIVD)
    ImageView leftIV;
    @BindView(R.id.rightIVD)
    ImageView rightIV;
    @BindView(R.id.centerTVD)
    TextView centerTV;

    TransparentProgressDialog transparentProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        transparentProgressDialog = new TransparentProgressDialog(this, R.drawable.prosessing_icon);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //  btnNext = (Button) findViewById(R.id.btnNextD);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        rightIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));

        rightIV.setTag(R.drawable.ic_add_black_24dp);


        //  btnNext.setVisibility(View.VISIBLE);

        centerTV.setText("Articles");

        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     if (rightIV.getDrawable() == getResources().getDrawable(R.drawable.ic_search_black_24dp)) {

                if ((Integer) rightIV.getTag() == R.drawable.ic_search_black_24dp) {

                    centerTV.setText("Searching");
                    rightIV.setVisibility(View.INVISIBLE);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new SearchFragment();
                    fragmentTransaction.replace(R.id.containerFLD, fragment).
                            addToBackStack(null).
                            commit();
                }
            }
        });

        getRecordsFromFirebaseDB();

        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    void getRecordsFromFirebaseDB() {
        Log.e("getDataFirebaseDB:", "inside");
        if (Utility.isNetworkAvailable(this)) {
            transparentProgressDialog.show();
            try {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                database.child("Items").child("-LmKL3ucfS0hf6g71W8u").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("getDataFirebaseDB_DC:", "inside");
                        List<Items> items = new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Items items1 = noteDataSnapshot.getValue(Items.class);
                            items1.setKey(noteDataSnapshot.getKey().toString());
                            items.add(items1);
                        }

                        Log.e("Size:", items.size() + "_zz");
                        selectedItemsList = items;
                        transparentProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("onCancelled:", "inside");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  //below is observable
            database.child("Items").child("-LmKL3ucfS0hf6g71W8u").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.e("getRecordsFromFirebaseDB_DC:", "inside");
                    List<Items> items = new ArrayList<>();
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Items items1 = noteDataSnapshot.getValue(Items.class);
                        items1.setKey(noteDataSnapshot.getKey().toString());
                        items.add(items1);
                    }

                    Log.e("Size:", items.size() + "_zz");
                    selectedItemsList = items;
                    selectedItemsAdapter.updateList(selectedItemsList);
                    transparentProgressDialog.cancel();

                    // preparing list data
                    prepareListData();

                    //adapter.updateList(notes);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("onCancelled:", "inside");
                }
            });*/
        } else {
            Toast.makeText(this, "No Network Available!", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateFragment(String fragmentText) {
        centerTV.setText(fragmentText);
        //btnNext.setVisibility(View.GONE);
        rightIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
        rightIV.setTag(R.drawable.ic_search_black_24dp);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new ShoppingFragment();
        fragmentTransaction.replace(R.id.containerFLD, fragment).
                addToBackStack(null).
                commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DashBoardFragment(), "Articles");
        viewPagerAdapter.addFragment(new DashBoardFragment(), "Profile");

        viewPager.setAdapter(viewPagerAdapter);

      //  View view = LayoutInflater.from(this).inflate(R.layout.tab_custom_layout, null);
//
//        TextView tabOneTV = (TextView) view;
//        tabOneTV.setText("Articles");
//        tabOneTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_format_list_bulleted_black_24dp, 0, 0);
//        tabLayout.getTabAt(0).setCustomView(tabOneTV);
//
//        TextView tabOneTV2 = (TextView) view;
//        tabOneTV2.setText("Profile");
//        tabOneTV2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_person_black_24dp, 0, 0);
//        tabLayout.getTabAt(1).setCustomView(tabOneTV);

    }


    public void updateTitles(String name1, String name2) {
        viewPagerAdapter.updateTitles(1, name1);
        viewPagerAdapter.updateTitles(2, name2);
        viewPagerAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Gallery Name's are Updated.", Toast.LENGTH_LONG).show();


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void updateTitles(int position, String title) {
            mFragmentTitleList.set(position, title);
            // notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
