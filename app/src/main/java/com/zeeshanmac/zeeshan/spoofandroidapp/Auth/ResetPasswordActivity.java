package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.annotation.NonNull;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private Button ResetPasswordSendEmailBtn;
    private EditText ResetEmailInput;
    private FirebaseAuth mAuth;
    ImageView ivBackarrow;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        ResetPasswordSendEmailBtn = (Button) findViewById(R.id.reset_password_email_button);
        ResetEmailInput = (EditText) findViewById(R.id.reset_password_email);
        ivBackarrow = findViewById(R.id.iv_back_arrow);

        ivBackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ResetPasswordSendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = ResetEmailInput.getText().toString();

                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ResetPasswordActivity.this, "S'il vous plaît entrer un email", Toast.LENGTH_SHORT).show();
                }

                else if (!(userEmail.matches(emailPattern))){
                    Toast.makeText(ResetPasswordActivity.this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show();

                }
                else {

                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, "Vous allez bientôt recevoir un email avec un lien pour réinitialiser votre mot de passe", Toast.LENGTH_SHORT).show();
                                Intent passwordIntent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(passwordIntent);
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this, "Impossible de réinitialiser le mot de passe Essayez encore ...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
