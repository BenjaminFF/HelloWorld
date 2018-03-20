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


    private MetrixView matrix_origin,matrix_transform,matrix_result;

    private MetrixView metrixView_orgin,metrixView_transform,metrixView_result;

    private EditText coordx_text,coordy_text;

    private String coordx,coordy;

    private TextView metrix_orgin_text,metrix_transform_text;

    private int element_metrix[][]={{1,0,0},{0,1,0},{0,0,1}};

    SnapView prepostsnapView,transformsnapView;

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
        coordx=new String();
        coordy=new String();
        prepostsnapView=v.findViewById(R.id.prepost_snapView);
        prepostsnapView.setSnapViewListener(new SnapViewListener() {
            @Override
            public void OnSnapViewClickListener(int childindex) {
                if (childindex==1){
                    setAnimationforIndex1();
                }else if (childindex==0){
                    setAnimationforIndex0();
                }
            }
        });

        transformsnapView=v.findViewById(R.id.transform_snapview);
        matrix_result=v.findViewById(R.id.matrix_result);
        matrix_origin=v.findViewById(R.id.matrix_origin);
        matrix_transform=v.findViewById(R.id.matrix_transform);

        metrix_orgin_text=v.findViewById(R.id.matrix_origin_text);
        metrix_transform_text=v.findViewById(R.id.matrix_transform_text);

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
                translateXform();
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
                coordy = s.toString();
                translateYform();
            }
        });

        TextView transformclick=v.findViewById(R.id.transform_text_click);
        transformclick.setClickable(true);
        transformclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setAnimationforIndex1(){
        float curtranslationX_mo=matrix_origin.getTranslationX();
        float dx=matrix_transform.getRight()-matrix_origin.getRight();
        ObjectAnimator mo_transr = ObjectAnimator.ofFloat(matrix_origin, "translationX", curtranslationX_mo,dx);
        ObjectAnimator t_mo_transr=ObjectAnimator.ofFloat(metrix_orgin_text, "translationX", curtranslationX_mo,dx);

        float curtranslationX_mp=matrix_transform.getTranslationX();
        ObjectAnimator mp_transl = ObjectAnimator.ofFloat(matrix_transform, "translationX", curtranslationX_mp,matrix_origin.getLeft()-matrix_transform.getLeft());
        ObjectAnimator t_mp_transl = ObjectAnimator.ofFloat(metrix_transform_text, "translationX", curtranslationX_mp,matrix_origin.getLeft()-matrix_transform.getLeft());
        AnimatorSet animatorSet1=new AnimatorSet();
        animatorSet1.play(mo_transr).with(mp_transl).with(t_mo_transr).with(t_mp_transl);
        animatorSet1.setDuration(1000);
        animatorSet1.start();
    }

    private void setAnimationforIndex0(){
        float curtranslationX_mo=matrix_origin.getTranslationX();
        ObjectAnimator mo_transr = ObjectAnimator.ofFloat(matrix_origin, "translationX", curtranslationX_mo,0);
        ObjectAnimator t_mo_transr = ObjectAnimator.ofFloat(metrix_orgin_text, "translationX", curtranslationX_mo,0);

        float curtranslationX_mp=matrix_transform.getTranslationX();
        ObjectAnimator mp_transl = ObjectAnimator.ofFloat(matrix_transform, "translationX", curtranslationX_mp,0);
        ObjectAnimator t_mp_transl = ObjectAnimator.ofFloat(metrix_transform_text, "translationX", curtranslationX_mp,0);
        AnimatorSet animatorSet1=new AnimatorSet();
        animatorSet1.setDuration(1000);
        animatorSet1.play(mo_transr).with(mp_transl).with(t_mo_transr).with(t_mp_transl);
        animatorSet1.start();
    }

    private void translateXform(){
        if (!coordx.equals("")&&!coordy.equals("")){
            matrix_transform.setMatrixbyTranslate(Integer.valueOf(coordx),Integer.valueOf(coordy));
        }else if (coordx.equals("")&&!coordy.equals("")){
            matrix_transform.setMatrixbyTranslate(0,Integer.valueOf(coordy));
        }else if (coordy.equals("")&&coordy.equals("")){
            matrix_transform.setMatrixbyTranslate(0,0);
        }
    }

    private void translateYform(){
        if (!coordx.equals("")&&!coordy.equals("")){
            matrix_transform.setMatrixbyTranslate(Integer.valueOf(coordx),Integer.valueOf(coordy));
        }else if (coordy.equals("")&&!coordx.equals("")){
            matrix_transform.setMatrixbyTranslate(Integer.valueOf(coordx),0);
        }else if (coordy.equals("")&&coordy.equals("")){
            matrix_transform.setMatrixbyTranslate(0,0);
        }
    }
}
