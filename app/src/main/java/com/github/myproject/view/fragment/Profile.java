package com.github.myproject.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.myproject.R;
import com.github.myproject.favorite_database.FavoriteDAO;
import com.github.myproject.favorite_database.FavoriteData;
import com.github.myproject.favorite_database.FavoriteDatabase;
import com.github.myproject.favorite_database.FavoriteOperation;
import com.github.myproject.view.activity.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    private TextView name;
    private TextView userName;
    private TextView bio;
    private TextView website;
    private FirebaseAuth firebaseAuth;
    private CircleImageView photoProfile;
    private Button btn_sign_out;
    private FavoriteOperation favoriteOperation;
    private FavoriteDatabase favoriteDatabase;
    private List<FavoriteData> favoriteData = new ArrayList<>();
    private FavoriteDAO favoriteDAO;
    public boolean aBoolean = false;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public boolean signInStatus(Boolean aBoolean){
        this.aBoolean = aBoolean;
        return aBoolean;
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.profile_name);
        userName = view.findViewById(R.id.profile_username);
        bio = view.findViewById(R.id.profiles_bio);
        website = view.findViewById(R.id.profile_website);
        photoProfile = view.findViewById(R.id.profile_image);
        btn_sign_out = view.findViewById(R.id.profile_logout_bottom);
        signInStatus(true);
        final GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            userName.setText(signInAccount.getFamilyName());
            Glide.with(getContext()).load(signInAccount.getPhotoUrl()).into(photoProfile);
            bio.setText(signInAccount.getDisplayName());
            website.setText(signInAccount.getEmail());

        }
        if(name.getText().toString().equals("Nama Profil") || name.getText().toString().equals("Profile Name")){
            btn_sign_out.setText("LOG IN");
            signInStatus(false);
        }else {
            btn_sign_out.setText("SIGN OUT");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            name.setText(user.getDisplayName());
            userName.setText(user.getDisplayName());
            String photoURL = user.getPhotoUrl().toString();
            photoURL = photoURL + "?type=large";
            Glide.with(getContext()).load(photoURL).into(photoProfile);
            bio.setText(user.getDisplayName());
            website.setText(user.getEmail());
        }
        if(btn_sign_out.getText().toString().equals("LOG IN")){
            btn_sign_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(getContext(), Login.class);
                    startActivity(loginIntent);
                }
            });
        }else {
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
                            FavoriteOperation favoriteOperation = new FavoriteOperation();
                            favoriteDatabase = FavoriteDatabase.database(getContext());
                            favoriteDAO = favoriteDatabase.favoriteDAO();
                            favoriteData = favoriteDatabase.favoriteDAO().getFavorite();
                            int favSize = favoriteData.size();
                            for (int i = 0; i < favSize; i++) {
                                favoriteOperation.deleteFavoriteData(favoriteData.get(i), favoriteDatabase);
                            }
                            firebaseAuth.signOut();
                            Intent loginIntent = new Intent(getContext(), Login.class) ;
                            startActivity(loginIntent);
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
}