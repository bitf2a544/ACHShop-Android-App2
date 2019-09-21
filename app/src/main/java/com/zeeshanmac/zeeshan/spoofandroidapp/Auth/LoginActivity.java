package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;

public class LoginActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private Boolean emailVerifier;
    private ProgressDialog loadingBar;

    private Button LoginButton;
    private EditText LoginEmail;
    private EditText LoginPassword;
    private Button RegisterButton;
    private TextView tvRegistered;
    private TextView ForgetPasswordLink;
    private DatabaseReference usersReference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");

        LoginButton = (Button) findViewById(R.id.login_button);
        ForgetPasswordLink = (TextView) findViewById(R.id.forget_password_link);
        LoginEmail = (EditText) findViewById(R.id.login_email);
        LoginPassword = (EditText) findViewById(R.id.login_password);
        tvRegistered = findViewById(R.id.tv_registered);
        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginEmail.getText().toString();
                String password = LoginPassword.getText().toString();

                LoginUserAccount(email, password);
            }
        });

        tvRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
              //  registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(registerIntent);
              //  finish();
            }
        });

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resetIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(resetIntent);
            }
        });
    }

    private void VerifyUserEmailAddress(){
        FirebaseUser user = mAuth.getCurrentUser();
        emailVerifier = user.isEmailVerified();

        if(emailVerifier){
            SendUserToMainActivity();
        }
        else {
            SendUserToVerifyEmailActivity();
        }
    }

    private void LoginUserAccount(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Veuillez entrer l'email!",Toast.LENGTH_SHORT).show();
        }

        if (!(email.matches(emailPattern))){
            Toast.makeText(LoginActivity.this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show();

        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Veuillez entrer le mot de passe",Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Compte de connexion");
            loadingBar.setMessage("S'il vous plaît attendez. Pendant que nous vérifions vos informations d'identification");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                VerifyUserEmailAddress();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Compte introuvable pour l'utilisateur spécifié. Veuillez vérifier et réessayer",Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this, "Votre mot de passe est incorrect. Veuillez réessayer ou utilisez Mot de passe oublié pour réinitialiser votre mot de passe.", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof FirebaseAuthInvalidUserException) {

                                String errorCode =
                                        ((FirebaseAuthInvalidUserException) e).getErrorCode();
                                if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                                    Toast.makeText(LoginActivity.this, "Compte non trouvé pour l'utilisateur spécifié.Veuillez vérifier et essayer à nouveau", Toast.LENGTH_SHORT).show();
                                } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                                    Toast.makeText(LoginActivity.this, "Votre compte a été désactivé.Veuillez contacter le support contact.trialcorner@gmail.com.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
        });

       }
    }

    private void SendUserToMainActivity() {
       // Intent mainIntent = new Intent(LoginActivity.this,MatchTabbedActivity.class);
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToVerifyEmailActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, EmailVerifyActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
