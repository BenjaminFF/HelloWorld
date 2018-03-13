package com.example.benja.helloworld.IdeaRecycler;

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

public class IdeaRecyclerAdepter extends RecyclerView.Adapter<IdeaRecyclerAdepter.IdeaHolder>{

    private ArrayList<IdeaItem> ideaItems;
    public class IdeaHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title,subtitle;
        public IdeaHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.ideaitem_image);
            title=itemView.findViewById(R.id.ideaitem_title);
            subtitle=itemView.findViewById(R.id.ideaitem_subtitle);
        }
    }

    public IdeaRecyclerAdepter(ArrayList<IdeaItem> ideaItems) {
        this.ideaItems = ideaItems;
    }

    @Override
    public IdeaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ideaitem, parent, false);

        IdeaHolder ideaHolder = new IdeaHolder(v);
        return ideaHolder;
    }

    @Override
    public void onBindViewHolder(IdeaHolder holder, int position) {
        holder.image.setImageResource(ideaItems.get(position).getImageId());
        holder.title.setText(ideaItems.get(position).getTitle());
        holder.subtitle.setText(ideaItems.get(position).getSubtitle());
    }

    @Override
    public int getItemCount() {
        return ideaItems.size();
    }
}
