package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benja.helloworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {


    private MetrixView matrix_origin,matrix_process,matrix_result;

    private EditText coordx_text,coordy_text;

    private String coordx,coordy;

    private int element_metrix[][]={{1,0,0},{0,1,0},{0,0,1}};

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
        snapView.setSnapViewListener(new SnapViewListener() {
            @Override
            public void OnSnapViewClickListener(int childindex) {
                if (childindex==1){
                    setAnimationforIndex1();
                }else if (childindex==0){
                    setAnimationforIndex0();
                }
            }
        });

        matrix_result=v.findViewById(R.id.matrix_result);
        matrix_result.setMatrix(new int[][]{{300},{300},{1}});
        matrix_origin=v.findViewById(R.id.matrix_origin);
        matrix_origin.setMatrix(new int[][]{{300},{300},{1}});
        matrix_process=v.findViewById(R.id.matrix_process);
        matrix_process.setMatrix(element_metrix);
        TextView enter=v.findViewById(R.id.enter_text);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix=new Matrix();
                matrix.postScale(0.5f,0.5f);
                Log.i("Test",matrix.toString());
            }
        });
        coordx_text=v.findViewById(R.id.coordx_text);
        coordx_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    coordx_text.setHint("x");
                }else {
                    coordx_text.setHint(null);
                }
            }
        });
        coordx_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                coordx=s.toString();
            }
        });

        coordy_text=v.findViewById(R.id.coordy_text);
        coordy_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    coordy_text.setHint("y");
                }else {
                    coordy_text.setHint(null);
                }
            }
        });
        coordy_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                coordy=s.toString();
            }
        });
    }

    private void setAnimationforIndex1(){
        float curtranslationX_mo=matrix_origin.getTranslationX();
        float dx=matrix_process.getRight()-matrix_origin.getRight();
        ObjectAnimator mo_transr = ObjectAnimator.ofFloat(matrix_origin, "translationX", curtranslationX_mo,dx);

        float curtranslationX_mp=matrix_process.getTranslationX();
        ObjectAnimator mp_transl = ObjectAnimator.ofFloat(matrix_process, "translationX", curtranslationX_mp,matrix_origin.getLeft()-matrix_process.getLeft());
        AnimatorSet animatorSet1=new AnimatorSet();
        animatorSet1.play(mo_transr).with(mp_transl);
        animatorSet1.setDuration(1000);
        animatorSet1.start();
    }

    private void setAnimationforIndex0(){
        float curtranslationX_mo=matrix_origin.getTranslationX();
        ObjectAnimator mo_transr = ObjectAnimator.ofFloat(matrix_origin, "translationX", curtranslationX_mo,0);

        float curtranslationX_mp=matrix_process.getTranslationX();
        ObjectAnimator mp_transl = ObjectAnimator.ofFloat(matrix_process, "translationX", curtranslationX_mp,0);
        AnimatorSet animatorSet1=new AnimatorSet();
        animatorSet1.setDuration(1000);
        animatorSet1.play(mo_transr).with(mp_transl);
        animatorSet1.start();
    }
}
