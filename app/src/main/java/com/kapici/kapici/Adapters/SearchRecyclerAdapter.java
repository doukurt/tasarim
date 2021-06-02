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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.Models.Users;
import com.kapici.kapici.R;
import com.squareup.picasso.Picasso;



public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {
    ArrayList<String> shoppingCart =new ArrayList<>();
    ArrayList<String> cartQuantities =new ArrayList<>();
    private long totalPrice;
    private ArrayList<String> productNameList;
    private ArrayList<String> productPriceList;
    private ArrayList<String> productImageList;
    private ArrayList<String> productIdList;
    private ArrayList<String> productDetailList;
    private ArrayList<String> productCategoryList;
    private ArrayList<String> productImageNameList;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public SearchRecyclerAdapter(ArrayList<String> productNameList, ArrayList<String> productPriceList, ArrayList<String> productImageList, ArrayList<String> productIdList, ArrayList<String> productDetailList, ArrayList<String> productCategoryList, ArrayList<String> productImageNameList) {
        this.productNameList = productNameList;
        this.productPriceList = productPriceList;
        this.productImageList = productImageList;
        this.productIdList = productIdList;
        this.productDetailList = productDetailList;
        this.productCategoryList = productCategoryList;
        this.productImageNameList = productImageNameList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View  view = inflater.inflate(R.layout.single_category_product,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.productName.setText(productNameList.get(position));
        Picasso.get().load(productImageList.get(position)).into(holder.productImage);
        holder.productPrice.setText(String.format("%s₺", productPriceList.get(position)));
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= Integer.parseInt(holder.productCount.getText().toString());
                i++;
                holder.productCount.setText(String.valueOf(i));
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= Integer.parseInt(holder.productCount.getText().toString());
                if (i>1){
                    i--;
                    holder.productCount.setText(String.valueOf(i));
                }
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=currentUser.getUid();
                firebaseFirestore.collection("UserDetails").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Users user = documentSnapshot.toObject(Users.class);
                        assert user != null;
                        shoppingCart= (ArrayList<String>) user.getShoppingCart();
                        cartQuantities= (ArrayList<String>) user.getCartQuantities();
                        totalPrice = user.getCartTotal();
                        if (!shoppingCart.contains(productIdList.get(position))){
                            totalPrice+= Long.parseLong(String.valueOf(holder.productCount.getText()))*Long.parseLong(productPriceList.get(position));
                            cartQuantities.add(holder.productCount.getText().toString());
                            shoppingCart.add(productIdList.get(position));
                            firebaseFirestore.collection("UserDetails").document(id).update("shoppingCart",shoppingCart,"cartQuantities",cartQuantities,"cartTotal",totalPrice)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(holder.itemView.getContext(),"Sepete Başarıyla Eklendi",Toast.LENGTH_LONG).show();

                                        }
                                    });
                        }else if (shoppingCart.contains(productIdList.get(position))){
                            int index = shoppingCart.indexOf(productIdList.get(position));
                            int cartQuantitForUpdate= Integer.parseInt(cartQuantities.get(position));
                            int productCountForUpdate = Integer.parseInt(holder.productCount.getText().toString());
                            int newQuantity = cartQuantitForUpdate+productCountForUpdate;
                            cartQuantities.set(index, String.valueOf(newQuantity));
                            totalPrice+= Long.parseLong(holder.productCount.getText().toString())*Long.parseLong(productPriceList.get(position));
                            firebaseFirestore.collection("UserDetails").document(id).update("cartQuantities",cartQuantities,"cartTotal",totalPrice).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(holder.itemView.getContext(),"Sepete Başarıyla Eklendi",Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return productNameList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName,productPrice;
        EditText productCount;
        CardView cardView;
        Button increase,decrease,addCart;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.listProductImage);
            productName=itemView.findViewById(R.id.listProductName);
            productCount=itemView.findViewById(R.id.productCount);
            increase=itemView.findViewById(R.id.increase);
            decrease=itemView.findViewById(R.id.decrease);
            addCart=itemView.findViewById(R.id.addCart);
            productPrice=itemView.findViewById(R.id.listProductPrice);
            cardView=itemView.findViewById(R.id.single_category_product_id);
        }
    }
}
