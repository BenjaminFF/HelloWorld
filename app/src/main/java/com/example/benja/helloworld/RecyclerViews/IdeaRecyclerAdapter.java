package com.example.benja.helloworld.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benja.helloworld.R;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/13.
 */

public class IdeaRecyclerAdapter extends RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaHolder>
{

    private ArrayList<IdeaItem> ideaItems;

    private OnRecyclerViewItemClickListener recyclerViewItemClickListener;
    public class IdeaHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title;
        public IdeaHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.ideaitem_image);
            title=itemView.findViewById(R.id.ideaitem_title);
        }
    }

    public IdeaRecyclerAdapter(ArrayList<IdeaItem> ideaItems) {
        this.ideaItems = ideaItems;
    }

    @Override
    public IdeaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ideaitem, parent, false);
        final IdeaHolder ideaHolder = new IdeaHolder(v);
        return ideaHolder;
    }

    @Override
    public void onBindViewHolder(final IdeaHolder holder, int position) {
        holder.image.setImageResource(ideaItems.get(position).getImageId());
        final String title=ideaItems.get(position).getTitle();
        holder.title.setText(title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClickListener.onItemClickListener(holder.itemView,title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ideaItems.size();
    }

    public void addRecyclerViewItemClickListener(OnRecyclerViewItemClickListener recyclerViewItemClickListener){
        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
    }
}
