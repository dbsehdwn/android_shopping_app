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

public class ReviewAdapter extends BaseAdapter {

    private ArrayList<Review> reviews = new ArrayList<>();

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Review getItem(int position) {
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
            convertView = inflater.inflate(R.layout.review_layout, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView review_img = (ImageView) convertView.findViewById(R.id.reivew_img) ;
        TextView reviewr_id = (TextView) convertView.findViewById(R.id.reviewer_id) ;
        TextView review_date = (TextView) convertView.findViewById(R.id.reivew_date) ;
        TextView review_text=(TextView)convertView.findViewById(R.id.reivew_text);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        Review reviews = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */


        String url = "https://dinosaurfactory.000webhostapp.com/img"+reviews.getPhoto();
        if(url.equals("https://dinosaurfactory.000webhostapp.com/img")){
            review_img.setVisibility(View.GONE);
        }else{
            Glide.with(context).load(url).into(review_img);
            review_img.setVisibility(View.VISIBLE);
        }
        reviewr_id.setText(reviews.getId());
        review_date.setText(reviews.getDate());
        review_text.setText(reviews.getText());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem( String id, String date, String text,String photo) {

        Review review = new Review();

        /* MyItem에 아이템을 setting한다. */
        review.setId(id);
        review.setDate(date);
        review.setText(text);
        review.setPhoto(photo);

        /* mItems에 MyItem을 추가한다. */
        reviews.add(review);

    }

    public String getid(int position){
        Review reviews = getItem(position);
        return reviews.getId();
    }
    public String getdate(int position){
        Review reviews = getItem(position);
        return reviews.getDate();
    }
    public String gettext(int position){
        Review reviews = getItem(position);
        return reviews.getText();
    }
    public String getphoto(int position){
        Review reviews = getItem(position);
        return reviews.getPhoto();
    }
}

