package com.zeeshanmac.zeeshan.spoofandroidapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zeeshanmac.zeeshan.spoofandroidapp.MainActivity;
import com.zeeshanmac.zeeshan.spoofandroidapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardFragment extends Fragment {


    @BindView(R.id.btnNextD)
    Button btnNext;
    @BindView(R.id.shareTextBtnD)
    Button shareTextBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this, view);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).updateFragment("Shopping");
                // ((YourActivityClassName)getActivity()).yourPublicMethod();

            }
        });
        shareTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < MainActivity.selectedItemsList.size(); i++) {

                    String name = MainActivity.selectedItemsList.get(i).getItemName();
                    int quantity = MainActivity.selectedItemsList.get(i).getItemQuantity();
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


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }




}
