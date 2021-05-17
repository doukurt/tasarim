package com.kapici.kapici;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.kapici.kapici.Adapters.AdminRecyclerAdapter;

import java.util.ArrayList;
import java.util.Map;

public class ListProductForAdmin extends Fragment {
    ArrayList<String> productNameFB;
    ArrayList<String> productPriceFB;
    ArrayList<String> productImageFB;
    ArrayList<String> productIdFB;
    ArrayList<String> productCategoryFB;
    ArrayList<String> productDetailFB;
    ArrayList<String> productImageNameFB;

    AdminRecyclerAdapter adminRecyclerAdapter;
    CollectionReference collectionReference;
    private FirebaseFirestore firebaseFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin_product_list,container,false);
        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Products");
        productImageFB=new ArrayList<>();
        productNameFB=new ArrayList<>();
        productPriceFB=new ArrayList<>();
        productIdFB=new ArrayList<>();
        productCategoryFB= new ArrayList<>();
        productDetailFB = new ArrayList<>();
        productImageNameFB= new ArrayList<>();

        getDataFromFirestore();


        RecyclerView adminProductList = view.findViewById(R.id.admin_product_list);
        adminProductList.setLayoutManager(new GridLayoutManager(getContext(),3));
        adminRecyclerAdapter = new AdminRecyclerAdapter(productNameFB,productPriceFB,productImageFB,productIdFB,productDetailFB,productCategoryFB,productImageNameFB);
        adminProductList.setAdapter(adminRecyclerAdapter);

        return view;
    }

    public void getDataFromFirestore(){
        CollectionReference collectionReference= firebaseFirestore.collection("Products");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    //Toast.makeText(getActivity(),error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
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
                        adminRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}
