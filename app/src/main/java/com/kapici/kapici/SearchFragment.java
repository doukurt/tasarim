package com.kapici.kapici;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kapici.kapici.Adapters.SearchRecyclerAdapter;

import java.util.ArrayList;
import java.util.Map;

public class SearchFragment extends Fragment {
    EditText searchEditText;
    TextView textView;
    ArrayList<String> productNameFB;
    ArrayList<String> productPriceFB;
    ArrayList<String> productImageFB;
    ArrayList<String> productIdFB;
    ArrayList<String> productCategoryFB;
    ArrayList<String> productDetailFB;
    ArrayList<String> productImageNameFB;
    ArrayList<String> shoppingCart ;
    ArrayList<String> cartQuantities ;
    CollectionReference collectionReference;
    SearchRecyclerAdapter searchRecyclerAdapter;
    private FirebaseFirestore firebaseFirestore;
    RecyclerView searchList;
    int i;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        searchEditText=view.findViewById(R.id.searchEditText);
        textView=view.findViewById(R.id.searchTV);
        productImageFB=new ArrayList<>();
        productNameFB=new ArrayList<>();
        productPriceFB=new ArrayList<>();
        productIdFB=new ArrayList<>();
        productCategoryFB= new ArrayList<>();
        productDetailFB = new ArrayList<>();
        productImageNameFB= new ArrayList<>();
        shoppingCart =new ArrayList<>();
        cartQuantities =new ArrayList<>();


        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Products");

        searchList = view.findViewById(R.id.searchRecyclerView);
        searchList.setLayoutManager(new GridLayoutManager(getContext(),3));
        searchRecyclerAdapter = new SearchRecyclerAdapter(productNameFB,productPriceFB,productImageFB,productIdFB,productDetailFB,productCategoryFB,productImageNameFB);
        searchList.setAdapter(searchRecyclerAdapter);
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
                    productNameFB.clear();
                    productImageFB.clear();
                    productPriceFB.clear();
                    productDetailFB.clear();
                    productCategoryFB.clear();
                    productImageNameFB.clear();
                    productIdFB.clear();
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
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        String name = (String) data.get("productName");
                        String image = (String) data.get("productImage");
                        String price = (String) data.get("productPrice");
                        String detail = (String) data.get("productDetail");
                        String category = (String) data.get("productCategory");
                        String imageName = (String) data.get("productImageName");


                        productImageFB.add(image);
                        productNameFB.add(name);
                        productPriceFB.add(price);
                        productDetailFB.add(detail);
                        productCategoryFB.add(category);
                        productImageNameFB.add(imageName);
                        productIdFB.add(snapshot.getId());
                        searchRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
