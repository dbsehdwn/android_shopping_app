package com.dinosaurfactory.android;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private int[] namecode;
    private int[] imageids;
    private Listener listener;

    interface Listener{
        void onClick(int position);
    }

    public CategoryAdapter(int[] namecode, int[] imageIds){
        this.namecode = namecode;
        this.imageids = imageIds;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public int getItemCount(){
        return namecode.length;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewTpye){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent,false);
        return new CategoryAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder,final int position){
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), imageids[position]);
        imageView.setImageDrawable(drawable);
        //imageView.setContentDescription(namecode[position]);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(namecode[position]);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
}
