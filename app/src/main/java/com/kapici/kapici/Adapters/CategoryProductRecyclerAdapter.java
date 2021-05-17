package com.kapici.kapici.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kapici.kapici.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryProductRecyclerAdapter extends RecyclerView.Adapter<CategoryProductRecyclerAdapter.CategoryProductHolder> {
    private ArrayList<String> productNameList;
    private ArrayList<String> productPriceList;
    private ArrayList<String> productImageList;
    private ArrayList<String> productIdList;
    private ArrayList<String> productDetailList;
    private ArrayList<String> productCategoryList;
    private ArrayList<String> productImageNameList;
    ArrayList<String> shoppingCart =new ArrayList<>();
    //ArrayList<String> cartQuantities =new ArrayList<>();
    int i;

    public CategoryProductRecyclerAdapter(ArrayList<String> productNameList, ArrayList<String> productPriceList, ArrayList<String> productImageList, ArrayList<String> productIdList, ArrayList<String> productDetailList, ArrayList<String> productCategoryList, ArrayList<String> productImageNameList) {
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
    public CategoryProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_category_product,parent,false);
        return new CategoryProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductHolder holder, int position) {

        holder.productName.setText(productNameList.get(position));
        Picasso.get().load(productImageList.get(position)).into(holder.productImage);

        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               i++;
               holder.productCount.setText(String.valueOf(i));
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i>1){
                    i--;
                    holder.productCount.setText(String.valueOf(i));
                }
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shoppingCart.add(productIdList.get(position));
                System.out.println(shoppingCart);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productNameList.size();
    }

    class CategoryProductHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName;
        EditText productCount;
        CardView cardView;
        Button increase,decrease,addCart;


        public CategoryProductHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.listProductImage);
            productName=itemView.findViewById(R.id.listProductName);
            productCount=itemView.findViewById(R.id.productCount);
            increase=itemView.findViewById(R.id.increase);
            decrease=itemView.findViewById(R.id.decrease);
            addCart=itemView.findViewById(R.id.addCart);
            cardView=itemView.findViewById(R.id.single_category_product_id);

        }
    }
}
