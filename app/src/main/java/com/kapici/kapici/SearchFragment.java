package com.kapici.kapici;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kapici.kapici.Models.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    EditText searchEditText;
    TextView textView;
    ArrayList<String> productNameFB;
    ArrayList<String> productImageFB;
    CollectionReference collectionReference;
    private FirebaseFirestore firebaseFirestore;
    RecyclerView searchList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        searchEditText=view.findViewById(R.id.searchEditText);
        textView=view.findViewById(R.id.searchTV);
        productImageFB=new ArrayList<>();
        productNameFB=new ArrayList<>();


        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Products");

        searchList = view.findViewById(R.id.searchRecyclerView);
        searchList.setLayoutManager(new GridLayoutManager(getContext(),3));
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null) {
                    textView.setVisibility(View.INVISIBLE);
                    searchList.setVisibility(View.VISIBLE);
                    getSearchDataFromDB(s.toString());
                }
                if(s.length()<1){
                    textView.setVisibility(View.VISIBLE);
                    searchList.setVisibility(View.INVISIBLE);
                }
            }
        });
        return view;
    }
    public void getSearchDataFromDB(String data){

        Query query = collectionReference.orderBy("productName").startAt(data).endAt(data +"\uf8ff");
        FirestoreRecyclerOptions<Products> options = new FirestoreRecyclerOptions.Builder<Products>()
                .setQuery(query, Products.class)
                .build();
        FirestoreRecyclerAdapter<Products,SearchViewHolder> adapter = new FirestoreRecyclerAdapter<Products, SearchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull Products model) {

                    holder.productName.setText(model.getProductName());
                    Picasso.get().load(model.getProductImage()).into(holder.productImage);

            }

            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.single_category_product,parent,false);
                return new SearchViewHolder(view);
            }
        };
        adapter.startListening();
        searchList.setAdapter(adapter);


    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
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
            cardView=itemView.findViewById(R.id.single_category_product_id);
        }
    }
}
