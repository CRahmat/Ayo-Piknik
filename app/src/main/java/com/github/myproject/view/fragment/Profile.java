package com.github.myproject.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.myproject.R;
import com.github.myproject.favorite_database.destination.FavoriteDAODestination;
import com.github.myproject.favorite_database.destination.FavoriteDataDestination;
import com.github.myproject.favorite_database.destination.FavoriteDatabaseDestination;
import com.github.myproject.favorite_database.destination.FavoriteOperationDestination;
import com.github.myproject.view.activity.activity.Login;
import com.github.myproject.view.activity.activity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    public static boolean aBoolean = false;
    private TextView name;
    private TextView userName;
    private TextView bio;
    private TextView website;
    private TextView activeLanguage;
    private FirebaseAuth firebaseAuth;
    private CircleImageView photoProfile;
    private Button btn_sign_out;
    private FavoriteOperationDestination favoriteOperationDestination;
    private FavoriteDatabaseDestination favoriteDatabaseDestination;
    private List<FavoriteDataDestination> favoriteDatumDestinations = new ArrayList<>();
    private FavoriteDAODestination favoriteDAODestination;
    private ImageButton bttn_english, bttn_indonesia;

    public Profile() {
        // Required empty public constructor
        firebaseAuth = FirebaseAuth.getInstance();
        try {
            if (firebaseAuth.getCurrentUser() != null) {
                aBoolean = true;
            }
        } catch (NullPointerException e) {
            aBoolean = false;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.profile_name);
        userName = view.findViewById(R.id.profile_username);
        bio = view.findViewById(R.id.profiles_bio);
        website = view.findViewById(R.id.profile_website);
        photoProfile = view.findViewById(R.id.profile_image);
        btn_sign_out = view.findViewById(R.id.profile_logout_bottom);
        bttn_indonesia = view.findViewById(R.id.indonesia_language);
        bttn_english = view.findViewById(R.id.english_language);
        activeLanguage = view.findViewById(R.id.language_active);

        loadLocale();
        if (aBoolean == false) {
            btn_sign_out.setText("LOG IN");
        } else {
            btn_sign_out.setText("SIGN OUT");
        }

        if (activeLanguage.getText().toString().equals("Setting")) {
            bttn_indonesia.setBackgroundResource(R.drawable.custom_language_background);
            bttn_english.setBackgroundResource(R.drawable.custom_language_background_active);
        } else {

            bttn_english.setBackgroundResource(R.drawable.custom_language_background);
            bttn_indonesia.setBackgroundResource(R.drawable.custom_language_background_active);
        }
        bttn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeLanguage.getText().toString().equals("Setting")){
                    Toast.makeText(getContext(), "Language Already Used", Toast.LENGTH_SHORT).show();
                }else {
                    setLocale("en");
                    getActivity().recreate();
                    bttn_indonesia.setBackgroundResource(R.drawable.custom_language_background);
                    bttn_english.setBackgroundResource(R.drawable.custom_language_background_active);
                    Intent myActivity = new Intent(getContext(), MainActivity.class);
                    startActivity(myActivity);
                    getActivity().finish();
                    getActivity().finishAffinity();
                    Toast.makeText(getContext(), "Success to change the language", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bttn_indonesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeLanguage.getText().toString().equals("Pengaturan")){
                    Toast.makeText(getContext(), "Bahasa Telah Digunakan", Toast.LENGTH_SHORT).show();
                }else {
                    setLocale("in");
                    getActivity().recreate();
                    bttn_indonesia.setBackgroundResource(R.drawable.custom_language_background_active);
                    bttn_english.setBackgroundResource(R.drawable.custom_language_background);
                    Intent myActivity = new Intent(getContext(), MainActivity.class);
                    startActivity(myActivity);
                    getActivity().finish();
                    getActivity().finishAffinity();
                    Toast.makeText(getContext(), "Bahasa berhasil diganti", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            try {
                if (user.getDisplayName().equals("")) {
                    name.setText("Profile Name");
                } else {
                    name.setText(user.getDisplayName());
                }
                if (user.getDisplayName().equals("")) {
                    userName.setText("Username");
                } else {
                    userName.setText(user.getDisplayName());
                }

                String photoURL = user.getPhotoUrl().toString();
                photoURL = photoURL + "?type=large";
                if (photoURL == null) {

                } else {
                    Glide.with(getContext()).load(photoURL).into(photoProfile);
                }
                if (user.getDisplayName().equals("")) {
                    bio.setText("NOT FOUND");
                } else {
                    bio.setText(user.getDisplayName());
                }
                if (user.getEmail().equals("")) {
                    website.setText("NOT FOUND");
                } else {
                    website.setText(user.getEmail());
                }
            } catch (Exception e) {

            }

        }
        if (btn_sign_out.getText().toString().equals("LOG IN")) {
            btn_sign_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(getContext(), Login.class);
                    startActivity(loginIntent);
                }
            });
        } else {
            btn_sign_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertExit = new AlertDialog.Builder(getContext());
                    alertExit.setIcon(R.drawable.logo_ayo_piknik);
                    alertExit.setMessage("If you Sign Out your favorite data can be deleted\nAre you sure you want to exit?");
                    alertExit.setCancelable(false);
                    alertExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);
                            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    aBoolean = false;
                                    firebaseAuth.signOut();
                                    Intent loginIntent = new Intent(getContext(), Login.class);
                                    startActivity(loginIntent);
                                    getActivity().finishAffinity();
                                    getActivity().finish();
                                }
                            });
                            FavoriteOperationDestination favoriteOperationDestination = new FavoriteOperationDestination();
                            favoriteDatabaseDestination = FavoriteDatabaseDestination.database(getContext());
                            favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
                            favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
                            int favSize = favoriteDatumDestinations.size();
                            for (int i = 0; i < favSize; i++) {
                                favoriteOperationDestination.deleteFavoriteData(favoriteDatumDestinations.get(i), favoriteDatabaseDestination);
                            }

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
            });
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getContext().getSharedPreferences("Setting", getActivity().MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    private void loadLocale() {
        SharedPreferences preferences = getContext().getSharedPreferences("Setting", getActivity().MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);

    }
}