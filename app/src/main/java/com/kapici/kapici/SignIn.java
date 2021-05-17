package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.kapici.kapici.Models.Users;

import java.util.Map;

public class SignIn extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText signInEmail,signInPassword;

    private FirebaseFirestore firebaseFirestore;
    boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        signInEmail = findViewById(R.id.signInEmail);
        signInPassword = findViewById(R.id.signInPassword);
        signInEmail.onEndBatchEdit();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {

            String userId = currentUser.getUid();

            firebaseFirestore.collection("UserDetails").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Users user = documentSnapshot.toObject(Users.class);
                    isAdmin = user.isAdmin();
                    Intent intentToAdminPage = new Intent();
                    if (isAdmin) {
                        intentToAdminPage.setClass(getApplicationContext(), AdminPanel.class);
                    } else {
                        intentToAdminPage.setClass(getApplicationContext(), NavHost.class);
                    }
                    startActivity(intentToAdminPage);
                    finish();
                }
            });
        }
    }



    public void signIn (View view){
        String u_email=signInEmail.getText().toString();
        String u_password=signInPassword.getText().toString();


        if(TextUtils.isEmpty(u_email)) {
            signInEmail.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(u_password)) {
            signInPassword.setError("Lütfen Boş Bırakmayın");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(u_email,u_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                String userId=currentUser.getUid();

                firebaseFirestore.collection("UserDetails").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Map<String, Object> data=document.getData();
                            isAdmin=(boolean) data.get("admin");
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(isAdmin){
                            Intent intentToAdminPage = new Intent(getApplicationContext(),AdminPanel.class);//gidecegi yer öylesine tasarlanınca degisacek
                            startActivity(intentToAdminPage);
                            finish();
                            Toast.makeText(SignIn.this,"Admin girişi başarılı",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(),NavHost.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(SignIn.this,"Başarıyla Giriş Yaptınız",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }



    public void kayitol (View view){

        Intent intent = new Intent(SignIn.this,SignUp.class);
        startActivity(intent);

    }
    public void sifreunut (View view){

        Intent intent = new Intent(SignIn.this,ForgetPassword.class);
        startActivity(intent);

    }
}
