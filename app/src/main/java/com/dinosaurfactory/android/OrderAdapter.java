package com.dinosaurfactory.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<order> reviews = new ArrayList<order>();
    private OrderAdapter.Listener listener;

    interface Listener{
        void onClick(int position);
    }

    public int getCount() {
        return reviews.size();
    }

    @Override
    public int getItemCount(){
        return reviews.size();
    }
    public order getItem(int position) {
        return reviews.get(position);
    }

    public void setListener(OrderAdapter.Listener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView orderimg;
        TextView ordername;
        TextView orderoption;
        TextView orderstate;

        public ViewHolder(View v){
            super(v);
             orderimg = (ImageView)v.findViewById(R.id.imageView);
             ordername = (TextView)v.findViewById(R.id.textView);
             orderoption = (TextView)v.findViewById(R.id.textView2);
             orderstate = (TextView)v.findViewById(R.id.textView3);
        }
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewTpye){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.orderlist, parent, false) ;
        OrderAdapter.ViewHolder vh = new OrderAdapter.ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder,final int position){

        order reviews = getItem(position);

        switch(reviews.getOrdername()){
            case "dino":
                holder.orderimg.setImageResource(R.drawable.dinosaur);
                holder.ordername.setText(R.string.dino);
                break;
            case "stone":
                holder.orderimg.setImageResource(R.drawable.stone);
                holder.ordername.setText(R.string.stone);
                break;
            case "biz":
                holder.orderimg.setImageResource(R.drawable.biz_ring);
                holder.ordername.setText(R.string.biz);
                break;
        }
        holder.orderoption.setText(reviews.getOrderoption());
        if(reviews.getPrice()==0){
            holder.orderstate.setText(reviews.getOrderstate());
        }else{
            holder.orderstate.setText("금액 : "+String.valueOf(reviews.getPrice())+"원");
        }

    }

    public void addItem(String name, String option, String state, int price) {

        order review = new order();

        /* MyItem에 아이템을 setting한다. */
        review.setOrdername(name);
        review.setOrderoption(option);
        review.setOrderstate(state);
        review.setPrice(price);

        /* mItems에 MyItem을 추가한다. */
        reviews.add(review);

    }
}
