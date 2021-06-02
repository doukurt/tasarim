package com.kapici.kapici;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kapici.kapici.Adapters.CartRecyclerAdapter;
import com.kapici.kapici.Models.Products;
import com.kapici.kapici.Models.Users;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Map;

public class ShoppingCartFragment extends Fragment {

    RecyclerView cartList;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> shoppingCart;
    ArrayList<String> cartQuantities;
    ArrayList<String> cartDataFromDB;
    ArrayList<String> cartPricesFromDb;
    ArrayList<String> cartImagesFromDb;
    ArrayList<String> cartQuantitiesFromDb;
    ArrayList<String> cartIdsFromDb;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    String pName,pPrice,pImage;
    CartRecyclerAdapter cartRecyclerAdapter;
    LinearLayout emptyCart,cartConfirm;
    Button confirmCart;
    TextView totalPriceHolder;
    int i;
    long totalPrice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        shoppingCart = new ArrayList<>();
        cartQuantities = new ArrayList<>();
        cartDataFromDB = new ArrayList<>();
        cartPricesFromDb = new ArrayList<>();
        cartImagesFromDb = new ArrayList<>();
        cartQuantitiesFromDb=new ArrayList<>();
        cartIdsFromDb=new ArrayList<>();
        emptyCart= view.findViewById(R.id.emptyCart);
        cartConfirm = view.findViewById(R.id.cartConfirm);
        confirmCart = view.findViewById(R.id.confirmButton);
        totalPriceHolder= view.findViewById(R.id.totalPriceHolder);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        cartConfirm.setVisibility(View.INVISIBLE);

        getCartDataFromDB();
        cartList = view.findViewById(R.id.cartRecyclerView);
        cartList.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerAdapter = new CartRecyclerAdapter(cartDataFromDB,cartPricesFromDb,cartImagesFromDb,cartQuantitiesFromDb,cartIdsFromDb);
        cartList.setAdapter(cartRecyclerAdapter);

        confirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PaymentPage paymentPage = new PaymentPage();
                fragmentTransaction.replace(R.id.fragment_container,paymentPage).commit();
            }
        });

        return view;
    }

    public void getCartDataFromDB() {
        String uid = currentUser.getUid();

        firebaseFirestore.collection("UserDetails").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    Map<String, Object> data = value.getData();
                    shoppingCart = (ArrayList<String>) data.get("shoppingCart");
                    cartQuantities = (ArrayList<String>) data.get("cartQuantities");
                    totalPrice = (long) data.get("cartTotal");
                    for (i= 0; i < shoppingCart.size(); i++) {
                        cartQuantitiesFromDb.add(cartQuantities.get(i));
                        cartIdsFromDb.add(shoppingCart.get(i));
                        firebaseFirestore.collection("Products").document(shoppingCart.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Products product = documentSnapshot.toObject(Products.class);
                                pName = product.getProductName();
                                pImage= product.getProductImage();
                                pPrice=product.getProductPrice();
                                cartImagesFromDb.add(pImage);
                                cartDataFromDB.add(pName);
                                cartPricesFromDb.add(pPrice);
                                totalPriceHolder.setText(String.valueOf(totalPrice)+"₺");
                                cartRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                        if (cartIdsFromDb.size()>0){
                            emptyCart.setVisibility(View.INVISIBLE);
                            cartConfirm.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }




}
