package com.example.benja.helloworld.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benja.helloworld.R;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/15.
 */

public class MeshRecyclerAdapter extends  RecyclerView.Adapter<MeshRecyclerAdapter.MeshHolder>{

    private ArrayList<String> meshitems;

    private OnRecyclerViewItemClickListener recyclerViewItemClickListener;

    public MeshRecyclerAdapter(ArrayList<String> meshitems) {
        this.meshitems = meshitems;
    }

    public class MeshHolder extends RecyclerView.ViewHolder{

        TextView text;
        public MeshHolder(View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.meshitem_text);
        }
    }

    @Override
    public MeshHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meshitem, parent, false);
        final MeshHolder ideaHolder = new MeshHolder(v);
        return ideaHolder;
    }

    @Override
    public void onBindViewHolder(final MeshHolder holder, int position) {
        final String text=meshitems.get(position).toString();
        holder.text.setText(text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClickListener.onItemClickListener(holder.itemView,text);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meshitems.size();
    }

    public void addRecyclerViewItemClickListener(OnRecyclerViewItemClickListener recyclerViewItemClickListener){
        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
    }
}
