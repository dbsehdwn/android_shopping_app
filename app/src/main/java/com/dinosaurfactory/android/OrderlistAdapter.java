package com.dinosaurfactory.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderlistAdapter extends BaseAdapter {

    private ArrayList<order> reviews = new ArrayList<order>();


    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public order getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.orderlist, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView orderimg = (ImageView) convertView.findViewById(R.id.imageView) ;
        TextView ordername = (TextView) convertView.findViewById(R.id.textView) ;
        TextView orderoption = (TextView) convertView.findViewById(R.id.textView2) ;
        TextView orderstate=(TextView)convertView.findViewById(R.id.textView3);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        order reviews = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */

        switch(reviews.getOrdername()){
            case "dino":
                orderimg.setImageResource(R.drawable.dinosaur);
                ordername.setText(R.string.dino);
                break;
            case "stone":
                orderimg.setImageResource(R.drawable.stone);
                ordername.setText(R.string.stone);
                break;
            case "biz":
                orderimg.setImageResource(R.drawable.biz_ring);
                ordername.setText(R.string.biz);
                break;
        }
        orderoption.setText(reviews.getOrderoption());
        if(reviews.getPrice()==0){
            orderstate.setText(reviews.getOrderstate());
        }else{
            orderstate.setText("금액 : "+String.valueOf(reviews.getPrice())+"원");
        }


        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
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
