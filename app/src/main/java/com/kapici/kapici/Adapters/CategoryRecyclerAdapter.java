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

import com.kapici.kapici.CategoryProductList;
import com.kapici.kapici.R;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryHolder> {
    private String[] categoryList={};
    int[] categoryPhoto={R.drawable.snack,R.drawable.fruits,R.drawable.drinks,
                         R.drawable.gida,R.drawable.hazir,R.drawable.kahvalti,
                         R.drawable.kisisel,R.drawable.evbakim};
    public CategoryRecyclerAdapter(String[] categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_category,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.categoryName.setText(categoryList[position]);
        holder.categoryImage.setImageResource(categoryPhoto[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CategoryProductList.class);
                intent.putExtra("Name",categoryList[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return categoryList.length;
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;
        CardView cardView;


        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage=itemView.findViewById(R.id.categoryImage);
            categoryName=itemView.findViewById(R.id.categoryName);
            cardView= itemView.findViewById(R.id.single_category_id);
        }
    }
}
