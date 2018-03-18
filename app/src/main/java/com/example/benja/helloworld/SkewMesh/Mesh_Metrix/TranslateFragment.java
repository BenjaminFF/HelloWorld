package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
        initComponent(v);
        return v;
    }

    private void initComponent(View v){
        final SnapView snapView=v.findViewById(R.id.translate_snapView);
        MetrixView metrixView=v.findViewById(R.id.metrixview1);
        metrixView.setMatrix(new int[][]{{1,2,3},{4,5,6},{7,8,9},{6,3,9}});
    }
}
