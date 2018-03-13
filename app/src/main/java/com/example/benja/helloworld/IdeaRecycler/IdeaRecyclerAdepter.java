package com.example.benja.helloworld.IdeaRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/13.
 */

public class IdeaRecyclerAdepter extends RecyclerView.Adapter<IdeaRecyclerAdepter.IdeaHolder>{

    private ArrayList<ideaItem> ideaItems;
    public class IdeaHolder extends RecyclerView.ViewHolder{

        public IdeaHolder(View itemView) {
            super(itemView);
        }
    }

    public IdeaRecyclerAdepter(ArrayList<ideaItem> ideaItems) {
        this.ideaItems = ideaItems;
    }

    @Override
    public IdeaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(IdeaHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ideaItems.size();
    }
}
