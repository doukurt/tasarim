package com.kapici.kapici;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kapici.kapici.Models.Products;

public class OtherFragment extends Fragment {
    View view;
    FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_other,container,false);


        ListView listView = view.findViewById(R.id.otherList);
        String[] values = new String[] { "Profil", "Uygulama Hakkında"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (values[position]){
                    case "Profil":
                        intent.setClass(getContext(), Profile.class);
                        startActivity(intent);
                        break;
                    case "Uygulama Hakkında":
                        //intent.setClass(getContext(), About.class);
                        break;
                }
            }
        });

        view.findViewById(R.id.cikis);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.cikis);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(),SignIn.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

}
