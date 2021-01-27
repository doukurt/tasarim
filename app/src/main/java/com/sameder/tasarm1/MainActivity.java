package com.sameder.tasarm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void girisyap (View view){

        Intent intent = new Intent(getApplicationContext(),homepage.class);
        startActivity(intent);

    }
    public void kayitol (View view){

        Intent intent = new Intent(MainActivity.this,signup.class);
        startActivity(intent);

    }
    public void sifreunut (View view){

        Intent intent = new Intent(MainActivity.this,forgetpassword.class);
        startActivity(intent);

    }
    public void signOut (View view){
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }
}