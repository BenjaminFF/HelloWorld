package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/15.
 */

public class MeshMetrixPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public MeshMetrixPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
