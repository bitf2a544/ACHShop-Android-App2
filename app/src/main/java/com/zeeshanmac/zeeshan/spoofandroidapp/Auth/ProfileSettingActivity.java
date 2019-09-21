package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;

public class ProfileSettingActivity extends AppCompatActivity {

    private EditText edtFirstName;
    private Button btnSaveChanges;
    private Button btnSubmit;
    private ImageView ivBackArrow;

    private DatabaseReference getUserDataReference;      // FireBase Database Reference
    private FirebaseAuth mAuth;

    Bitmap thumb_bitmap = null;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        edtFirstName = findViewById(R.id.edt_first_name);
        btnSaveChanges = findViewById(R.id.btn_save_changes);
        ivBackArrow = findViewById(R.id.iv_back_arrow);


        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        //Root of FireBase Database
        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);
        getUserDataReference.keepSynced(true);

        loadingBar = new ProgressDialog(this);

        getUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("username").getValue().toString();
                edtFirstName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfileSettings();
            }
        });

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void changeProfileSettings(){

        String newUseranme = edtFirstName.getText().toString();

        getUserDataReference.child("username").setValue(newUseranme)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        }
                    }
                });
       finish();
    }
}
