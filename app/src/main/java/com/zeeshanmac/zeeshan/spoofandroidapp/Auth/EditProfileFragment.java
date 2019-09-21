package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;


public class EditProfileFragment extends Fragment {

    private TextView tvNameTitle;
    private Button btnEditInfo;


    private DatabaseReference getUserDataReference;
    private FirebaseAuth mAuth;


    public EditProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        tvNameTitle = view.findViewById(R.id.tv_name_title);

        btnEditInfo = view.findViewById(R.id.btn_edit_info);

        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        //Root of FireBase Database
        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);
        getUserDataReference.keepSynced(true);


        getUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("username").getValue().toString();
                Log.d("print", "onDataChange: ");
                tvNameTitle.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editintent = new Intent(getContext(), ProfileSettingActivity.class);
                startActivity(editintent);
            }
        });
        return view;
    }

}
