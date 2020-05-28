package com.github.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Registration extends AppCompatActivity {
    private static final String TAG = "FacebookAuthentication";
    private static final String TAG_GOOGLE = "FGoogelAuthentication";
    TextView Login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private LoginButton registration;
    private SignInButton signInButtonGoogle;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        registration = findViewById(R.id.facebook_registration);
        registration.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();
        registration.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Login = findViewById(R.id.log_in_bttn);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(getApplicationContext(), Login.class);
                startActivity(Login);
                finish();
            }
        });

        //Google
        signInButtonGoogle = findViewById(R.id.google_registration);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
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
        Log.d(TAG, "handleFaceboookToken" + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Sign In with Credential Successfull");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.d(TAG, "Sign In with Credential Failed");
                    Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        //GOOGLE
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(signInAccountTask);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> signInAccountTask) {
        try {
            GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(googleSignInAccount);

        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount googleSignInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUIGoogle(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                    updateUIGoogle(null);
                }
            }
        });

    }

    private void updateUIGoogle(FirebaseUser user) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Username" + googleSignInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (user != null) {
                //textView.setTextUser.getDisplayName();
                if (user.getPhotoUrl() != null) {
                    String photoURL = user.getPhotoUrl().toString();
                    photoURL = photoURL + "?type=large";

                }
            }
        } else {
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
    //Facebook-End

    //Google

}
