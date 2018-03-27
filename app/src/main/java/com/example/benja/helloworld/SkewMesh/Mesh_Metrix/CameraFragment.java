package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benja.helloworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {

    ImageView imageView;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_camera, container, false);
        imageView=v.findViewById(R.id.myImageView);
        TextView textView=v.findViewById(R.id.start_rotate_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotate3dAnimation rotate3dAnimation=new Rotate3dAnimation(getContext(),0,360,imageView.getWidth()/2,imageView.getHeight()/2,0,false);
                rotate3dAnimation.setInterpolator(new LinearInterpolator());
                rotate3dAnimation.setDuration(10000);
                imageView.startAnimation(rotate3dAnimation);
            }
        });
        return v;
    }

}
