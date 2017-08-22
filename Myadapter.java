package com.giulia.myapplication3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.giulia.myapplication3.R;

import java.util.ArrayList;

/**
 * Created by giulia on 26/06/17.
 */

public class Myadapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> listid;
    private ArrayList<String> nameList;

    public Myadapter(Context context, ArrayList<Integer> listid, ArrayList<String> nameList) {
        this.context = context;
        this.listid = listid;
        this.nameList = nameList;
    }


    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            convertView = View.inflate(context, R.layout.list_item, null);
        }
        ImageView images=(ImageView) convertView.findViewById(R.id.image);
        TextView text= (TextView) convertView.findViewById(R.id.text);
        images.setImageResource(listid.get(position));
        text.setText(nameList.get(position));
        return convertView;
    }
  /*  public void remove(String m,ArrayList<String> list_it){
        int position;
        position=list_it.indexOf(m);
        list_it.remove(position);

    }*/
}
