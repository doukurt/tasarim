package com.kapici.kapici;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    LinearLayout emptyCart;


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
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getCartDataFromDB();

        cartList = view.findViewById(R.id.cartRecyclerView);
        cartList.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerAdapter = new CartRecyclerAdapter(cartDataFromDB,cartPricesFromDb,cartImagesFromDb,cartQuantitiesFromDb,cartIdsFromDb);
        cartList.setAdapter(cartRecyclerAdapter);

        cartRecyclerAdapter.setOnItemClickListener(new CartRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

        return view;
    }

    public void removeItem(int position){
        firebaseFirestore.collection("UserDetails").document(currentUser.getUid())
                .update("shoppingCart", FieldValue.arrayRemove(cartIdsFromDb.get(position))
                        ,"cartQuantities",FieldValue.arrayRemove(cartQuantitiesFromDb.get(position))).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Sepetten bir ürün kaldırıldı",Toast.LENGTH_LONG).show();
               //Fragmentı yenileme
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                fragmentTransaction.replace(R.id.fragment_container,shoppingCartFragment).commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        });

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
                    for (int i = 0; i < shoppingCart.size(); i++) {
                        cartQuantitiesFromDb.add(cartQuantities.get(i));
                        cartIdsFromDb.add(shoppingCart.get(i));
                        firebaseFirestore.collection("Products").document(shoppingCart.get(i)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                Products product = value.toObject(Products.class);
                                pName = product.getProductName();
                                pImage= product.getProductImage();
                                pPrice=product.getProductPrice();
                                cartImagesFromDb.add(pImage);
                                cartDataFromDB.add(pName);
                                cartPricesFromDb.add(pPrice);
                                cartRecyclerAdapter.notifyDataSetChanged();
                                if (cartIdsFromDb.size()>=1){
                                    emptyCart.setVisibility(View.INVISIBLE);
                                    cartList.setVisibility(View.VISIBLE);
                                }else if (cartIdsFromDb.size()==0){
                                    emptyCart.setVisibility(View.VISIBLE);
                                    cartList.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                }

            }
        });
    }

}
