package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;


public class EmailVerifyActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private Button ResetPasswordSendEmailBtn;
    private EditText ResetEmailInput;
    private FirebaseAuth mAuth;
    ImageView ivBackarrow;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

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
                    Toast.makeText(EmailVerifyActivity.this, "S'il vous plaît entrer un email", Toast.LENGTH_SHORT).show();
                }

                else if (!(userEmail.matches(emailPattern))){
                    Toast.makeText(EmailVerifyActivity.this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show();
                }
                else {

                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(EmailVerifyActivity.this, "Vous allez bientôt recevoir un email avec un lien pour réinitialiser votre mot de passe", Toast.LENGTH_SHORT).show();
                                Intent passwordIntent = new Intent(EmailVerifyActivity.this, LoginActivity.class);
                                startActivity(passwordIntent);
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(EmailVerifyActivity.this, "Impossible de réinitialiser le mot de passe Essayez encore ...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
