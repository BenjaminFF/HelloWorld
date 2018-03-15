package com.example.benja.helloworld.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.benja.helloworld.RecyclerViews.*;

import com.example.benja.helloworld.R;
import com.example.benja.helloworld.SkewMesh.SkewMeshWork;

import java.util.ArrayList;


public class IdeaFragment extends Fragment {



    public IdeaFragment() {
        // Required empty public constructor
    }

    ArrayList<IdeaItem> ideaItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_idea, container, false);
        initIdeaView();
        initIdeaRecycler(v);
        return v;
    }

    private void initIdeaView(){
        ideaItems=new ArrayList<>();
        ideaItems.add(new IdeaItem(R.drawable.bitmapmesh,"模拟扭曲"));
        ideaItems.add(new IdeaItem(R.drawable.bitmapmesh,"弹性卡片"));
    }

    private void initIdeaRecycler(final View v){
        RecyclerView recyclerView=v.findViewById(R.id.idea_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(v.getContext());
        IdeaRecyclerAdapter adepter=new IdeaRecyclerAdapter(ideaItems);
        adepter.addRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View itemView, String item_Title) {
                switch (item_Title){
                    case "模拟扭曲":
                        Intent intent=new Intent(v.getContext(),SkewMeshWork.class);
                        startActivity(intent);
                        break;
                    case "弹性卡片":
                        break;
                }
            }
        });
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        PagerSnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adepter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
