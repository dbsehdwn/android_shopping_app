package com.dinosaurfactory.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectOrderAdapter extends BaseAdapter {

    ArrayList<String> order_option = new ArrayList<String>(
            Arrays.asList("","","","","","","")
    );

    Spinner pick;

    String option;
    TextView list;


    private ArrayList<order> orders = new ArrayList<>();

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_list, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        list = (TextView) convertView.findViewById(R.id.list) ;
        pick = (Spinner)convertView.findViewById(R.id.pick);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        order orders = getItem(position);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( context,android.R.layout.simple_spinner_dropdown_item,orders.getPick());
        pick.setAdapter(arrayAdapter);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        list.setText(orders.getList());
        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        pick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int sp_position, long id) {
                option = getList(position) + " : " + getPick(position).get(sp_position);
                order_option.set(position,option);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String list ,ArrayList pick) {

        order review = new order();

        /* MyItem에 아이템을 setting한다. */
        review.setList(list);
        review.setPick(pick);


        /* mItems에 MyItem을 추가한다. */
        orders.add(review);

    }

    public String getList(int position){
        order review = getItem(position);
        return review.getList();
    }
    public ArrayList getPick(int position){
        order review = getItem(position);
        return review.getPick();
    }

    public ArrayList<String> getOrder_option(){return  order_option;}

}
