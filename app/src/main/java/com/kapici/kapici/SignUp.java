package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.Models.Users;


import java.util.Date;
import java.util.HashMap;


public class SignUp extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    EditText userEmailEditText,userPasswordEditText,userPasswordRepeat,userNameEditText,
             userSurnameEditText,userBirthday,userPhoneNumber,userAdressEditText;
   // DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userEmailEditText=findViewById(R.id.userEmailEditText);
        userPasswordEditText=findViewById(R.id.userPasswordEditText);
        userPasswordRepeat=findViewById(R.id.userPasswordRepeat);
        userNameEditText=findViewById(R.id.userNameEditText);
        userSurnameEditText=findViewById(R.id.userSurnameEditText);
        userBirthday=findViewById(R.id.userBirthday);
        userPhoneNumber=findViewById(R.id.userPhoneNumber);
        userAdressEditText=findViewById(R.id.userAdressEditText);
    }

    public void signUp (View view){
        String email=userEmailEditText.getText().toString();
        String password=userPasswordEditText.getText().toString();
        String passwordRepeat = userPasswordRepeat.getText().toString();
        String name= userNameEditText.getText().toString();
        String surname= userSurnameEditText.getText().toString();
        String birthday= userBirthday.getText().toString();
        String phoneNumber=userPhoneNumber.getText().toString();
        String address= userAdressEditText.getText().toString();



        if (!password.matches(passwordRepeat)){
            Toast.makeText(SignUp.this,"Parolalar uyuşmuyor, lütfen kontol ediniz!",Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(email)) {
            userEmailEditText.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(passwordRepeat)) {
            userPasswordRepeat.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(name)) {
            userNameEditText.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(surname)) {
            userSurnameEditText.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(birthday)) {
            userBirthday.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(phoneNumber)) {
            userPhoneNumber.setError("Lütfen Boş Bırakmayın");
            return;
        }if(TextUtils.isEmpty(address)) {
            userAdressEditText.setError("Lütfen Boş Bırakmayın");
            return;
        }else{

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUp.this,"Başarıyla Kayıt Olundu",Toast.LENGTH_LONG).show();
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    String userId = user.getUid();

                    Users newUser=new Users(name,surname,birthday,phoneNumber,address);

                    firebaseFirestore.collection("UserDetails").document(userId).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(SignUp.this,NavHost.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           Toast.makeText(SignUp.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                      Toast.makeText(SignUp.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}