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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.R;
import com.kapici.kapici.ShoppingCartFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {
    private ArrayList<String> cartNameList;
    private ArrayList<String> cartPriceList;
    private ArrayList<String> cartImageList;
    private ArrayList<String> cartQuantityList;
    private ArrayList<String> cartIdList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    int a;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public CartRecyclerAdapter(ArrayList<String> cartNameList, ArrayList<String> cartPriceList, ArrayList<String> cartImageList, ArrayList<String> cartQuantityList,ArrayList<String> cartIdList) {
        this.cartNameList = cartNameList;
        this.cartPriceList = cartPriceList;
        this.cartImageList = cartImageList;
        this.cartQuantityList = cartQuantityList;
        this.cartIdList = cartIdList;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart,parent,false);
        CartViewHolder cartViewHolder = new CartViewHolder(view,mListener);
        return cartViewHolder;
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
            }
        });
        holder.cartDecrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a= Integer.parseInt(holder.cartPcount.getText().toString());
                if (a>1){
                    a--;
                    holder.cartPcount.setText(String.valueOf(a));
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


        public CartViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });


        }
    }
}
