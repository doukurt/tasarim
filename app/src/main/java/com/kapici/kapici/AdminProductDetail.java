package com.kapici.kapici;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kapici.kapici.Models.Products;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class AdminProductDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText updateName,updateDetail,updateCategory,updatePrice;
    ImageView updateImage;
    String id, imageName,category;
    Uri imageData;
    Bitmap selectedImage;
    int spinnerPosition=0;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_detail);
        Spinner spinner = (Spinner) findViewById(R.id.updateCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("Name");
        String image = intent.getExtras().getString("Image");
        String price = intent.getExtras().getString("Price");
        String detail = intent.getExtras().getString("Detail");
        String categorySpinner = intent.getExtras().getString("Category");
        imageName = intent.getExtras().getString("ImageName");
        id = intent.getExtras().getString("Id");

        spinnerPosition=adapter.getPosition(categorySpinner);
        spinner.setSelection(spinnerPosition);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        updateName=findViewById(R.id.updateName);
        updateDetail= findViewById(R.id.updateDetail);
        updatePrice= findViewById(R.id.updatePrice);
        updateImage = findViewById(R.id.updateImage);



        updateName.setText(name);
        updatePrice.setText(price);
        updateDetail.setText(detail);

        Picasso.get().load(image).into(updateImage);

    }

    public void updateProduct(View view){
        String updatedName = updateName.getText().toString();
        String updatedDetail = updateDetail.getText().toString();
        String updatedPrice = updatePrice.getText().toString();

        HashMap<String,Object> updatedData= new HashMap<>();
        updatedData.put("productName",updatedName);
        updatedData.put("productDetail",updatedDetail);
        updatedData.put("productCategory",category);
        updatedData.put("productPrice",updatedPrice);

        if(imageData!=null){
        firebaseStorage.getReference().child(imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                firebaseStorage.getReference().child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl= uri.toString();
                                updatedData.put("productImage",downloadUrl);
                                firebaseFirestore.collection("Products").document(id).update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AdminProductDetail.this,"Başarıyla Güncellendi",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(AdminProductDetail.this,AdminPanel.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AdminProductDetail.this,""+e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminProductDetail.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminProductDetail.this,"deletefoto"+e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
        }else{
            firebaseFirestore.collection("Products").document(id).update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AdminProductDetail.this,"Başarıyla Güncellendi",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AdminProductDetail.this,AdminPanel.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminProductDetail.this,""+e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void deleteProduct(View view){

        firebaseStorage.getReference().child(imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseFirestore.collection("Products").document(id)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminProductDetail.this,"Başarıyla Silindi",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminProductDetail.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent = new Intent(AdminProductDetail.this,AdminPanel.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminProductDetail.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void updateImageData(View view){

        if(ContextCompat.checkSelfPermission(AdminProductDetail.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==2 && resultCode == Activity.RESULT_OK && data != null){
            imageData = data.getData();

            try {
                if (Build.VERSION.SDK_INT>=28){
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    updateImage.setImageBitmap(selectedImage);
                }else{
                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),imageData);
                    updateImage.setImageBitmap(selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}