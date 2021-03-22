package com.kapici.kapici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
    }
    public void sifreunutgonder (View view){

        Intent intent = new Intent(ForgetPassword.this,SignIn.class);
        startActivity(intent);

    }
}