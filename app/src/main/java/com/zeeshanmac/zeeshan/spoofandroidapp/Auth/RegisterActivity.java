package com.zeeshanmac.zeeshan.spoofandroidapp.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import android.widget.Toolbar;
import android.support.annotation.NonNull;
import com.zeeshanmac.zeeshan.spoofandroidapp.*;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;

    private Toolbar mToolbar;
    private ProgressDialog loadingBar;
    private EditText RegisterUserName;
    private EditText RegisterUserEmail;
    private EditText RegisterUserPassword;
    private EditText RegisterUserConfirmPassword;
    private Button CreateAccountbutton;
    ImageView ivBckrrow;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

   ArrayAdapter <String> adapter;
    String record= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        RegisterUserName = (EditText) findViewById(R.id.register_name);
        RegisterUserEmail = (EditText) findViewById(R.id.register_email);
        RegisterUserConfirmPassword = findViewById(R.id.register_confirmpassword);
        RegisterUserPassword = (EditText) findViewById(R.id.register_password);
        CreateAccountbutton = (Button) findViewById(R.id.create_account_button);
        ivBckrrow = findViewById(R.id.iv_back_arrow);

        ivBckrrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CreateAccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = RegisterUserName.getText().toString().toLowerCase();
                String email = RegisterUserEmail.getText().toString();
                String password = RegisterUserPassword.getText().toString();
                String confirmpassword = RegisterUserConfirmPassword.getText().toString();
                RegisterAccount(name, email, password, confirmpassword);
            }
        });
    }


private void RegisterAccount(final String name, final String email, final String password, final String confirmpassword) {

    if(TextUtils.isEmpty(name)){
        Toast.makeText(RegisterActivity.this,"Veuillez entrer le pseudo",Toast.LENGTH_LONG).show();
    }

    if(TextUtils.isEmpty(email)){
        Toast.makeText(RegisterActivity.this,"Veuillez entrer l'email",Toast.LENGTH_LONG).show();
    }

    if (!(email.matches(emailPattern))){
        Toast.makeText(RegisterActivity.this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show();
    }

    if(TextUtils.isEmpty(password)){
        Toast.makeText(RegisterActivity.this,"Veuillez entrer le mot de passe",Toast.LENGTH_LONG).show();
    }

    if(TextUtils.isEmpty(confirmpassword)){
        Toast.makeText(RegisterActivity.this,"Veuillez entrer le mot de passe",Toast.LENGTH_LONG).show();
    }

    if(confirmpassword != password){
        Toast.makeText(RegisterActivity.this,"Veuillez saisir votre mot de passe",Toast.LENGTH_LONG).show();
    }

    else {
        loadingBar.setTitle("Créer un nouveau compte");
        loadingBar.setMessage("S'il vous plaît attendez, pendant que nous créons un compte pour vous ..");
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    String current_user_Id = mAuth.getCurrentUser().getUid();
                   storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_Id);

                    storeUserDefaultDataReference.child("createdTimestamp").setValue(ServerValue.TIMESTAMP);
                    storeUserDefaultDataReference.child("email").setValue(email);
                    storeUserDefaultDataReference.child("id").setValue(current_user_Id);
                    storeUserDefaultDataReference.child("username").setValue(name);
                    storeUserDefaultDataReference.child("password").setValue(password);
                    storeUserDefaultDataReference.child("isRequestSend").setValue(false)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        SendUserToLoginActivity();
                                    }
                                    else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(RegisterActivity.this,"Error: " + error,Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }
                else
                {
                    String error = task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this,"Error Occured " + error,Toast.LENGTH_LONG).show();
                }
                loadingBar.dismiss();

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(RegisterActivity.this, "Votre mot de passe est incorrect. Veuillez réessayer ou utilisez Mot de passe oublié pour réinitialiser votre mot de passe.", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseAuthInvalidUserException) {

                    String errorCode =
                            ((FirebaseAuthInvalidUserException) e).getErrorCode();
                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        Toast.makeText(RegisterActivity.this, "Compte non trouvé pour l'utilisateur spécifié.Veuillez vérifier et essayer à nouveau", Toast.LENGTH_SHORT).show();
                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                        Toast.makeText(RegisterActivity.this, "Votre compte a été désactivé.Veuillez contacter le support contact.trialcorner@gmail.com.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                     }
                  }
              }
          });
       }
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToVerifyEmailActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, EmailVerifyActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}