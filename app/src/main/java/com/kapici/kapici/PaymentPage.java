package com.kapici.kapici;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.StructuredQuery;
import com.kapici.kapici.Models.Orders;
import com.kapici.kapici.Models.Users;

import java.util.ArrayList;
import java.util.List;


public class PaymentPage extends Fragment {
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    long total;
    String adress,payment;
    List<String> items;
    List<String> quantity;
    TextView totalHolder,adresHolder;
    Button orderButton;
    RadioGroup radioGroup;
    RadioButton radioButton;
    View view;
    ArrayList<String> deletedcart = new ArrayList<>();
    ArrayList<String> deletedQua = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment_page, container, false);
        totalHolder= view.findViewById(R.id.totalHolder);
        adresHolder= view.findViewById(R.id.adresHolder);
        orderButton = view.findViewById(R.id.orderButton);
        radioGroup = view.findViewById(R.id.radioGroup);
        items = new ArrayList<>();
        quantity = new ArrayList<>();
        getOrderData();
        return view;
    }


    public void getOrderData(){

        firebaseFirestore.collection("UserDetails").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users user = documentSnapshot.toObject(Users.class);
                total=user.getCartTotal();
                adress=user.getAddress();
                items = user.getShoppingCart();
                quantity = user.getCartQuantities();

                orderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioId = radioGroup.getCheckedRadioButtonId();
                        radioButton = view.findViewById(radioId);
                        payment = (String) radioButton.getText();
                        Orders order = new Orders(items,quantity,total,payment,currentUser.getUid());
                        firebaseFirestore.collection("Orders").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                    firebaseFirestore.collection("UserDetails").document(currentUser.getUid())
                                            .update("orderList", FieldValue.arrayUnion(documentReference.getId()),"shoppingCart",deletedcart,"cartQuantities",deletedQua,"cartTotal",0)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(),"Siparişiniz Alınmıştır",Toast.LENGTH_LONG).show();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                                            fragmentTransaction.replace(R.id.fragment_container,shoppingCartFragment).commit();
                                        }
                                    });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                });
                adresHolder.setText(adress);
                totalHolder.setText(String.valueOf(total)+"₺");
            }
        });

    }
}