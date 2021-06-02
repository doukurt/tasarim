package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgetPassword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String userEmail;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.forgetPasswordEmail);
    }
    public void sifreunutgonder (View view){

        if( email.getText().toString().length()!=0){
            userEmail= email.getText().toString();
        firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this,"Sıfırlama Emaili gönderildi. Lütfen mailinizi kontrol ediniz!",Toast.LENGTH_LONG).show();
                }
            }
        });

        }


    }
}