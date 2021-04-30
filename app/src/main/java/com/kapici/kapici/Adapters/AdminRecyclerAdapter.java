package com.kapici.kapici.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kapici.kapici.AdminProductDetail;
import com.kapici.kapici.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.ProductHolder> {

    private ArrayList<String> productNameList;
    private ArrayList<String> productPriceList;
    private ArrayList<String> productImageList;
    private ArrayList<String> productIdList;
    private ArrayList<String> productDetailList;
    private ArrayList<String> productCategoryList;
    private ArrayList<String> productImageNameList;

    public AdminRecyclerAdapter(ArrayList<String> productNameList, ArrayList<String> productPriceList, ArrayList<String> productImageList, ArrayList<String> productIdList, ArrayList<String> productDetailList, ArrayList<String> productCategoryList, ArrayList<String> productImageNameList) {
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
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_product_admin,parent,false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
         holder.productName.setText(productNameList.get(position));
         holder.productPrice.setText(productPriceList.get(position)+"₺");
         Picasso.get().load(productImageList.get(position)).into(holder.productImage);

         holder.cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Context context = v.getContext();
                 Intent intent = new Intent(context, AdminProductDetail.class);
                 intent.putExtra("Name",productNameList.get(position));
                 intent.putExtra("Image",productImageList.get(position));
                 intent.putExtra("Price",productPriceList.get(position));
                 intent.putExtra("Id",productIdList.get(position));
                 intent.putExtra("Detail",productDetailList.get(position));
                 intent.putExtra("Category",productCategoryList.get(position));
                 intent.putExtra("ImageName",productImageNameList.get(position));
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return productNameList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        CardView cardView;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productName);
            productPrice= itemView.findViewById(R.id.productPrice);
            cardView= itemView.findViewById(R.id.admin_single_card_id);


        }

    }
}
