package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.benja.helloworld.R;

import java.util.ArrayList;

public class Mesh_Metrix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesh__metrix);
        RotateFragment rotateFragment=new RotateFragment();
        SkewFragment skewFragment=new SkewFragment();
        TranslateFragment translateFragment=new TranslateFragment();
        ViewPager viewPager=findViewById(R.id.mesh_metrix_pager);

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(rotateFragment);
        fragments.add(skewFragment);
        fragments.add(translateFragment);
        MeshMetrixPagerAdapter adapter=new MeshMetrixPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
