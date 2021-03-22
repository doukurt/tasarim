package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText signInEmail,signInPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        firebaseAuth= FirebaseAuth.getInstance();

        signInEmail= findViewById(R.id.signInEmail);
        signInPassword=findViewById(R.id.signInPassword);

        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(getApplicationContext(),NavHost.class);
            startActivity(intent);
            finish();
        }

    }

    public void signIn (View view){
        String u_email=signInEmail.getText().toString();
        String u_password=signInPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(u_email,u_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignIn.this,"Başarıyla Giriş Yaptınız",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),NavHost.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
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
