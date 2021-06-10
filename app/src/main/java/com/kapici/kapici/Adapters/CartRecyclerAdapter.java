package com.kapici.kapici.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.Models.Users;
import com.kapici.kapici.R;
import com.kapici.kapici.ShoppingCartFragment;
import com.squareup.picasso.Picasso;

import java.security.CryptoPrimitive;
import java.util.ArrayList;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {
    private ArrayList<String> cartNameList;
    private ArrayList<String> cartPriceList;
    private ArrayList<String> cartImageList;
    private ArrayList<String> cartQuantityList;
    private ArrayList<String> cartIdList;
    public int a,quantity,price;
    long totalPrice;
    Users user = new Users();
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public CartRecyclerAdapter(ArrayList<String> cartNameList, ArrayList<String> cartPriceList, ArrayList<String> cartImageList, ArrayList<String> cartQuantityList, ArrayList<String> cartIdList) {
        this.cartNameList = cartNameList;
        this.cartPriceList = cartPriceList;
        this.cartImageList = cartImageList;
        this.cartQuantityList = cartQuantityList;
        this.cartIdList = cartIdList;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.cartPname.setText(cartNameList.get(position));
        holder.cartPprice.setText(String.format("%s₺", cartPriceList.get(position)));
        holder.cartPcount.setText(cartQuantityList.get(position));
        Picasso.get().load(cartImageList.get(position)).into(holder.cartImage);
        holder.cartIncrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a= Integer.parseInt(holder.cartPcount.getText().toString());
                a++;
                holder.cartPcount.setText(String.valueOf(a));
                cartQuantityList.set(position,String.valueOf(a));
                firebaseFirestore.collection("UserDetails").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Users user = documentSnapshot.toObject(Users.class);
                        long total = user.getCartTotal();
                        total+= Long.parseLong(cartPriceList.get(position));
                        firebaseFirestore.collection("UserDetails").document(currentUser.getUid()).update("cartQuantities",cartQuantityList,"cartTotal",total).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FragmentManager fragmentManager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                                fragmentTransaction.replace(R.id.fragment_container,shoppingCartFragment).commit();
                            }
                        });
                    }
                });
            }
        });
        holder.cartDecrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a= Integer.parseInt(holder.cartPcount.getText().toString());
                if (a>1){
                    a--;
                    holder.cartPcount.setText(String.valueOf(a));
                    cartQuantityList.set(position,String.valueOf(a));
                    firebaseFirestore.collection("UserDetails").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Users user = documentSnapshot.toObject(Users.class);
                            long total = user.getCartTotal();
                            total-= Long.parseLong(cartPriceList.get(position));
                            firebaseFirestore.collection("UserDetails").document(currentUser.getUid()).update("cartQuantities",cartQuantityList,"cartTotal",total).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    FragmentManager fragmentManager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                                    fragmentTransaction.replace(R.id.fragment_container,shoppingCartFragment).commit();
                                }
                            });
                        }
                    });




                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartIdList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        TextView cartPname,cartPprice,cartPcount;
        ImageView cartImage;
        Button cartDecrase,cartIncrase,deleteCartItem;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartPcount=itemView.findViewById(R.id.cartPcount);
            cartPname=itemView.findViewById(R.id.cartPname);
            cartPprice=itemView.findViewById(R.id.cartPprice);
            cartImage=itemView.findViewById(R.id.cartImage);
            cartIncrase=itemView.findViewById(R.id.cartIncrase);
            cartDecrase=itemView.findViewById(R.id.cartDecrase);
            deleteCartItem=itemView.findViewById(R.id.deleteCartItem);



            deleteCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            firebaseFirestore.collection("UserDetails").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    user = documentSnapshot.toObject(Users.class);
                                    totalPrice =user.getCartTotal();
                                    totalPrice-=(Long.parseLong(cartPriceList.get(position))*Long.parseLong(cartQuantityList.get(position)));
                                    cartIdList.remove(position);
                                    cartQuantityList.remove(position);
                                    firebaseFirestore.collection("UserDetails").document(currentUser.getUid())
                                            .update("shoppingCart", cartIdList
                                                    ,"cartQuantities", cartQuantityList,"cartTotal",totalPrice).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(v.getContext(),"Sepetten bir ürün kaldırıldı",Toast.LENGTH_SHORT).show();
                                            FragmentManager fragmentManager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
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
                            });

                        }
                    }
            });


        }
    }
}
