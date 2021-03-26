package com.kapici.kapici;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.Models.Products;

public class AddProduct extends Fragment {


    private FirebaseFirestore firebaseFirestore;
    EditText editProductName,editProductDetail,editProductCategory,editProductPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseFirestore= FirebaseFirestore.getInstance();
        return inflater.inflate(R.layout.fragment_add_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button save = view.findViewById(R.id.save);
        editProductName= view.findViewById(R.id.editProductName);
        editProductDetail= view.findViewById(R.id.editProductDetail);
        editProductCategory= view.findViewById(R.id.editProductCategory);
        editProductPrice=view.findViewById(R.id.editProductPrice);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editProductName.getText().toString();
                String detail=editProductDetail.getText().toString();
                String category = editProductCategory.getText().toString();
                String price= editProductPrice.getText().toString();

                Products product = new Products(name,detail,category,price);
                firebaseFirestore.collection("Products").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Başarıyla Kaydoldu",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Kayıt işlemi başarısız",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
