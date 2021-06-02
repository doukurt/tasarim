    package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

    public class AdminPanel extends AppCompatActivity {

        FirebaseAuth firebaseAuth;
        TabLayout tabLayout ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_panel);
            firebaseAuth = FirebaseAuth.getInstance();
            tabLayout = findViewById(R.id.tabLayout);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ListProductForAdmin listProduct = new ListProductForAdmin();
            fragmentTransaction.replace(R.id.admin_frame_layout,listProduct).commit();
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            ListProductForAdmin listProduct = new ListProductForAdmin();
                            fragmentTransaction.replace(R.id.admin_frame_layout,listProduct).commit();
                            break;
                        case 1:
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            AddProduct addProduct = new AddProduct();
                            fragmentTransaction.replace(R.id.admin_frame_layout,addProduct).commit();
                            break;


                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.admin_menu,menu);

            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId()==R.id.admin_sign_out){
                firebaseAuth.signOut();
                Intent intent = new Intent(AdminPanel.this,SignIn.class);
                startActivity(intent);
                finish();
            }
            return super.onOptionsItemSelected(item);
        }



    /*public void addProduct(View view){

    }
    public void listProduct(View view){

    }*/


}