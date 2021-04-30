package com.kapici.kapici;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.kapici.kapici.Adapters.AdminRecyclerAdapter;
import com.kapici.kapici.Adapters.CategoryProductRecyclerAdapter;

import java.util.ArrayList;
import java.util.Map;

public class CategoryProductList extends AppCompatActivity {
    String name;
    ArrayList<String> productNameFB;
    ArrayList<String> productPriceFB;
    ArrayList<String> productImageFB;
    ArrayList<String> productIdFB;
    ArrayList<String> productCategoryFB;
    ArrayList<String> productDetailFB;
    ArrayList<String> productImageNameFB;
    CategoryProductRecyclerAdapter categoryProductRecyclerAdapter;

    CollectionReference collectionReference;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product_list);
        productImageFB=new ArrayList<>();
        productNameFB=new ArrayList<>();
        productPriceFB=new ArrayList<>();
        productIdFB=new ArrayList<>();
        productCategoryFB= new ArrayList<>();
        productDetailFB = new ArrayList<>();
        productImageNameFB= new ArrayList<>();

        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Products");
        Intent intent = getIntent();
        name = intent.getExtras().getString("Name");


        getProductsFromFirestore();
        RecyclerView categoryProductList = findViewById(R.id.categoryProductView);
        categoryProductList.setLayoutManager(new GridLayoutManager(this,3));
        categoryProductRecyclerAdapter = new CategoryProductRecyclerAdapter(productNameFB,productPriceFB,productImageFB,productIdFB,productDetailFB,productCategoryFB,productImageNameFB);
        categoryProductList.setAdapter(categoryProductRecyclerAdapter);
    }
    public void getProductsFromFirestore(){
     collectionReference.whereEqualTo("productCategory",name).addSnapshotListener(new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
             if(value!=null){
                 for (DocumentSnapshot snapshot:value.getDocuments()){
                     Map<String,Object> data = snapshot.getData();
                     String name = (String) data.get("productName");
                     String image = (String) data.get("productImage");
                     String price = (String) data.get("productPrice");
                     String  detail= (String) data.get("productDetail");
                     String category = (String) data.get("productCategory");
                     String  imageName= (String) data.get("productImageName");



                     productImageFB.add(image);
                     productNameFB.add(name);
                     productPriceFB.add(price);
                     productDetailFB.add(detail);
                     productCategoryFB.add(category);
                     productImageNameFB.add(imageName);
                     productIdFB.add(snapshot.getId());
                     categoryProductRecyclerAdapter.notifyDataSetChanged();
                 }
             }
         }
     });
    }



}