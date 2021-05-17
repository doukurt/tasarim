package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.Models.Users;

public class Profile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String uName,uSurname,uBirthday,uPhone,uAdres;
    TextView userName,userSurname,userBirthday;
    EditText userPhone,userAdres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userName= findViewById(R.id.profileName);
        userSurname = findViewById(R.id.profileSurname);
        userBirthday = findViewById(R.id.profilebirthday);
        userPhone = findViewById(R.id.profilePhone);
        userAdres = findViewById(R.id.profileAdress);

        getProfileData();

    }

    public void getProfileData(){
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {

            String userId = currentUser.getUid();
            firebaseFirestore.collection("UserDetails").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Users user = documentSnapshot.toObject(Users.class);
                    uName = user.getName();
                    uSurname = user.getSurname();
                    uBirthday = user.getBirthday();
                    uPhone = user.getPhoneNumber();
                    uAdres = user.getAddress();
                    setUserDetails();
                }
            });
        }
    }
    public void setUserDetails(){
        userName.setText(uName);
        userSurname.setText(uSurname);
        userBirthday.setText(uBirthday);
        userPhone.setText(uPhone);
        userAdres.setText(uAdres);
    }
}