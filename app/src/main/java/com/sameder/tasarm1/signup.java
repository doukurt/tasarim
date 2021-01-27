package com.sameder.tasarm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void kayittamamla (View view){

        Intent intent = new Intent(signup.this,homepage.class);
        startActivity(intent);

    }
}