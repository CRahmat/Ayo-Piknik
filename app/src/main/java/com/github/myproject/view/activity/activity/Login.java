package com.github.myproject.view.activity.activity;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.myproject.R;
import com.github.myproject.view.fragment.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import java.util.Arrays;

public class Login extends AppCompatActivity {
    private static final String TAG = "FacebookAuthentication";
    private static final String TAG_GOOGLE = "GoogelAuthentication";
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private Button registration;
    private Button signInButtonGoogle;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private int RC_SIGN_IN = 1;
    private TextView signUp;
    private Long backPressTime;
    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent mainActivity = new Intent(Login.this, MainActivity.class);
                    startActivity(mainActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        };

        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (emails.isEmpty()) {
                    loginEmail.setError(getString(R.string.input_email));
                    loginEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
                    loginEmail.setError(getString(R.string.email_valid));
                    loginEmail.requestFocus();
                    return;
                }
                if (password.length() < 8) {
                    loginPassword.setError(getString(R.string.min_password));
                    loginPassword.requestFocus();
                    return;
                }
                if (!(emails.isEmpty() && password.length() < 8 && !Patterns.EMAIL_ADDRESS.matcher(emails).matches())) {
                    setContentView(R.layout.progress_dialog);
                    firebaseAuth.signInWithEmailAndPassword(emails, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                                Intent mainActivity = new Intent(Login.this, MainActivity.class);
                                Profile.aBoolean = true;
                                startActivity(mainActivity);
                            } else {
                                Profile.aBoolean = false;
                                Log.e("Login Failed", task.getException().toString());
                                Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        signUp = findViewById(R.id.sign_up_bttn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivity = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signupActivity);
                finish();
                finishAffinity();
            }
        });


        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        registration = findViewById(R.id.facebook_registration);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "onSuccess" + loginResult);
                        handleFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "onCancle");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "onError" + error);
                    }
                });

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    updateUI(user);
                } else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    firebaseAuth.signOut();
                }
            }
        };

        //Google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        signInButtonGoogle = findViewById(R.id.google_registration);
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void handleFacebookToken(AccessToken accessToken) {
        try {
            Log.d(TAG, "handleFaceboookToken" + accessToken);

            setContentView(R.layout.progress_dialog);

            final AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Sign In with Credential Successfull");
                        Toast.makeText(getApplicationContext(), R.string.success_facebook, Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Profile.aBoolean = true;
                        Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentMainActivity);
                        updateUI(user);
                        Toast.makeText(getApplicationContext(), R.string.username_login + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    } else {
                        Profile.aBoolean = false;
                        Log.d(TAG, "Sign In with Credential Failed");
                        Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Facebook Login Error", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        //GOOGLE
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(signInAccountTask);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> signInAccountTask) {
        try {
            GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
            FirebaseGoogleAuth(googleSignInAccount);

        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount googleSignInAccount) {
        try {
            setContentView(R.layout.progress_dialog);
            AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), R.string.sign_in_google, Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUIGoogle(user);
                        Profile.aBoolean = true;
                        Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentMainActivity);
                        finish();
                        finishAffinity();
                    } else {
                        Profile.aBoolean = false;
                        Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
                        updateUIGoogle(null);
                    }
                }
            });
        } catch (Exception e) {

        }

    }

    private void updateUIGoogle(FirebaseUser user) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        Toast.makeText(getApplicationContext(), getString(R.string.username_login) + googleSignInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener == null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (backPressTime + 2000 > System.currentTimeMillis()) {
                finishAffinity();
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(getApplicationContext(), "Press Back Again To Exite", Toast.LENGTH_SHORT).show();
            }
            backPressTime = System.currentTimeMillis();
        } catch (NullPointerException e) {
            final AlertDialog.Builder alertExit = new AlertDialog.Builder(this);
            alertExit.setIcon(R.drawable.logo_ayo_piknik);
            alertExit.setMessage(R.string.conf_exit);
            alertExit.setCancelable(false);
            alertExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finishAffinity();
                }
            });
            alertExit.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertExit.create();
            alertDialog.show();
        }

    }
}
