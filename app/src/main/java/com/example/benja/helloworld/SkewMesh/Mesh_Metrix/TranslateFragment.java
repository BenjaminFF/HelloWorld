package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        final MetrixView matrix_result=v.findViewById(R.id.matrix_result);
        matrix_result.setMatrix(new int[][]{{1,2,3},{4,5,6},{7,8,9},{6,3,9}});
        final MetrixView matrix_origin=v.findViewById(R.id.matrix_origin);
        final MetrixView matrix_process=v.findViewById(R.id.matrix_process);
        TextView enter=v.findViewById(R.id.enter_text);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float curtranslationX=matrix_origin.getTranslationX();
                float dx=matrix_process.getRight()-matrix_origin.getRight();
                ObjectAnimator animator = ObjectAnimator.ofFloat(matrix_origin, "translationX", curtranslationX,dx);
                animator.setDuration(2000);
                animator.start();

                float x=matrix_process.getTranslationX();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(matrix_process, "translationX", x,matrix_origin.getLeft()-matrix_process.getLeft());
                animator2.setDuration(2000);
                animator2.start();
                Log.i("Test",curtranslationX+"");
            }
        });
    }
}
