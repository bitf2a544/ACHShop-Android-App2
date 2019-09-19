package com.zeeshanmac.zeeshan.spoofandroidapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.TransparentProgressDialog;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditItemActivity extends AppCompatActivity {

    @BindView(R.id.leftIVD)
    ImageView leftIV;

    @BindView(R.id.quantyETD)
    EditText quantyET;

    @BindView(R.id.descETD)
    EditText descET;


    @BindView(R.id.btnSaveD)
    Button btnSave;

    @BindView(R.id.btncCancelD)
    Button btncCancel;
    TransparentProgressDialog transparentProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_activity);
        ButterKnife.bind(this);

        transparentProgressDialog = new TransparentProgressDialog(this, R.drawable.prosessing_icon);

        final Items selectedItem = (Items) getIntent().getSerializableExtra("selcted_item");

        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantity = Integer.parseInt(quantyET.getText().toString());
                String desc = descET.getText().toString();


                if (Utility.isNetworkAvailable(EditItemActivity.this)) {
                    transparentProgressDialog.show();

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                    database.child("Items").child("-LmKL3ucfS0hf6g71W8u").
                            child(selectedItem.getKey()).child("itemQuantity").setValue(quantity);


                    database.child("Items").child("-LmKL3ucfS0hf6g71W8u").
                            child(selectedItem.getKey()).child("itemDescription").setValue(desc);

                    transparentProgressDialog.dismiss();
                    Toast.makeText(EditItemActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(EditItemActivity.this, "No Network Available!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btncCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


}
