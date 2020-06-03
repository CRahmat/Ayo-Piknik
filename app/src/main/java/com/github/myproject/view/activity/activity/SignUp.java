package com.github.myproject.view.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.myproject.R;
import com.github.myproject.view.fragment.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(getApplicationContext(), Login.class);
                startActivity(Login);
                finish();
            }
        });
    }

    private void registrationUser() {
        String name = username.getText().toString();
        String emails = email.getText().toString();
        String password = mPassword.getText().toString();

        if (name.isEmpty()) {
            username.setError(getString(R.string.name_signup));
            username.requestFocus();
            return;
        }
        if (emails.isEmpty()) {
            email.setError(getString(R.string.input_email));
            email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError(getString(R.string.email_valid));
            email.requestFocus();
            return;
        }
        if (password.length() < 8) {
            mPassword.setError(getString(R.string.min_password));
            mPassword.requestFocus();
            return;
        }
        if (!(name.isEmpty() && emails.isEmpty() && password.length() < 8 && !Patterns.EMAIL_ADDRESS.matcher(emails).matches())) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(emails, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // progresBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                                Profile.aBoolean = true;
                                finishAffinity();
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.sign_up_failed, Toast.LENGTH_SHORT).show();
                                Log.e("Sign Up Failed", task.getException().toString());
                                if (task.getException().toString().equals("The email address is already in use by another account")) {
                                    Toast.makeText(getApplicationContext(), R.string.email_already, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
