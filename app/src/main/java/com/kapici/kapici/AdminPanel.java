    package com.kapici.kapici;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class AdminPanel extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListProductForAdmin listProduct = new ListProductForAdmin();
        fragmentTransaction.replace(R.id.admin_frame_layout,listProduct).commit();
    }

    public void addProduct(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddProduct addProduct = new AddProduct();
        fragmentTransaction.replace(R.id.admin_frame_layout,addProduct).commit();
    }
    public void listProduct(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListProductForAdmin listProduct = new ListProductForAdmin();
        fragmentTransaction.replace(R.id.admin_frame_layout,listProduct).commit();
    }


}