package com.example.benja.helloworld.MainFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.benja.helloworld.IdeaRecycler.*;

import com.example.benja.helloworld.R;

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
        ideaItems=new ArrayList<>();
        IdeaItem testitem=new IdeaItem();
        testitem.setImageId(R.drawable.bitmapmesh);
        testitem.setTitle("模拟扭曲");
        testitem.setSubtitle("drawbitmapmesh");
        ideaItems.add(testitem);
        RecyclerView recyclerView=v.findViewById(R.id.idea_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(v.getContext());
        IdeaRecyclerAdepter adepter=new IdeaRecyclerAdepter(ideaItems);
        recyclerView.setAdapter(adepter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return v;
    }






}
