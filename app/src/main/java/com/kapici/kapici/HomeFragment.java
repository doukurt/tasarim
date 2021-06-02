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
import androidx.viewpager.widget.ViewPager;

import com.kapici.kapici.Adapters.CategoryRecyclerAdapter;
import com.kapici.kapici.Adapters.ImageAdapter;

public class HomeFragment extends Fragment {

    String[] category = {"Atıştırmalıklar","Meyve Sebze","İçecekler","Temel Gıda","Hazır Gıda","Kahvaltılıklar","Kişisel Bakım","Ev Bakım"};
    CategoryRecyclerAdapter categoryRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);

        RecyclerView categoryList = view.findViewById(R.id.categoryList);
        categoryList.setLayoutManager(new GridLayoutManager(getContext(),3));
        categoryRecyclerAdapter=new CategoryRecyclerAdapter(category);
        categoryList.setAdapter(categoryRecyclerAdapter);

        ViewPager viewPager = view.findViewById(R.id.imageSlider);
        ImageAdapter imageAdapter = new ImageAdapter(getContext());
        viewPager.setAdapter(imageAdapter);
        return view;
    }

}
