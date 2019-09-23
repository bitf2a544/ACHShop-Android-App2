package com.zeeshanmac.zeeshan.spoofandroidapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshanmac.zeeshan.spoofandroidapp.Auth.MatchTabbedActivity;
import com.zeeshanmac.zeeshan.spoofandroidapp.MainActivity;
import com.zeeshanmac.zeeshan.spoofandroidapp.R;
import com.zeeshanmac.zeeshan.spoofandroidapp.adapter.ParentExpandableAdapter;
import com.zeeshanmac.zeeshan.spoofandroidapp.adapter.DashBoardAdapter;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.TransparentProgressDialog;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.inloop.localmessagemanager.LocalMessage;
import eu.inloop.localmessagemanager.LocalMessageCallback;
import eu.inloop.localmessagemanager.LocalMessageManager;

import static com.zeeshanmac.zeeshan.spoofandroidapp.MainActivity.selectedItemsList;

public class DashBoardFragment extends Fragment implements LocalMessageCallback {

    public static final int UPDATE_ARTICLES = 101;
    public static final int UPDATE_ITEM_SELECTED_LIST = 102;
    @BindView(R.id.articleTVD)
    TextView articleTV;
    @BindView(R.id.shareIVD)
    ImageView shareTextIV;

    @BindView(R.id.recyclerViewD)
    RecyclerView recyclerView;

    DashBoardAdapter dashBoardAdapter;
    TransparentProgressDialog transparentProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("onCreateView", "inside_Dash Fragment");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this, view);

        LocalMessageManager.getInstance().addListener(this);

        transparentProgressDialog = new TransparentProgressDialog(getActivity(), R.drawable.prosessing_icon);

        articleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).updateFragment("Shopping");
            }
        });
        shareTextIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < selectedItemsList.size(); i++) {

                    String name = selectedItemsList.get(i).getItemName();
                    int quantity = selectedItemsList.get(i).getItemQuantity();
                    stringBuilder.append(name + " = " + quantity + "\n");

                }
                // String shareBody = "Here is the share content body";
                String shareBody = stringBuilder.toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
            }
        });
        getRecordsFromFirebaseDB();
        //     articleTV.setText(selectedItemsList.size() + " articles");

        initRecyclerView();

        Log.e("onCreateView", "inside_Dash Fragment_1");
        return view;
    }


    public void initRecyclerView() {

        List<Items> list = new ArrayList<>();
        list.add(new Items());

        dashBoardAdapter = new DashBoardAdapter(getActivity(), list);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dashBoardAdapter);

    }

    @Override
    public void onResume() {
        Log.e("onResume", "inside_Dash Fragment_2");
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    void getRecordsFromFirebaseDB() {
        Log.e("getDataFirebaseDB:", "inside");
        if (Utility.isNetworkAvailable(getActivity())) {
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
                        articleTV.setText(selectedItemsList.size() + " articles");

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

        } else {
            Toast.makeText(getActivity(), "No Network Available!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleMessage(@NonNull final LocalMessage msg) {
        switch (msg.getId()) {
            case UPDATE_ARTICLES: {
                articleTV.setText(selectedItemsList.size() + " articles");
            }
            break;
        }
    }

}
