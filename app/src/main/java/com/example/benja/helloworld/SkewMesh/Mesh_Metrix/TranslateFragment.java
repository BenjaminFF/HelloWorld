package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benja.helloworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {


    public TranslateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_translate, container, false);
        Button button=v.findViewById(R.id.testButton);
        final SnapView snapView=v.findViewById(R.id.snapview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snapView.startScroll();
            }
        });
        return v;
    }

}
