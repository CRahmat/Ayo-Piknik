package com.github.myproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.myproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private TextView login;
    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText mPassword;
    private Button signUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = findViewById(R.id.registration_name);
        email = findViewById(R.id.registration_emial);
        mPassword = findViewById(R.id.registration_password);
        signUp = findViewById(R.id.sign_up_button);
        login = findViewById(R.id.log_in_bttn);

        registrationUser();

        firebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(getApplicationContext(), com.github.myproject.view.fragment.Login.class);
                startActivity(Login);
                finish();
            }
        });
    }

    private void registrationUser() {
        String name = username.getText().toString();
        String emails = email.getText().toString();
        String password = mPassword.getText().toString();

        if(name.isEmpty()){
            username.setError("Nama Harus Diisi");
            username.requestFocus();
            return;
        }
        if(emails.isEmpty()){
            username.setError("Nama Harus Diisi");
            username.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
            username.setError("Massukan Email Yang Valid");
            username.requestFocus();
            return;
        }
        if(password.length() < 8){
            username.setError("Minimal 8 karakter");
            username.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(emails, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // progresBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                        }else{

                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){

        }
    }
}
