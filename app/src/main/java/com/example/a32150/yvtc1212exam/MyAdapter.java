package com.example.a32150.yvtc1212exam;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by auser on 2017/11/9.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    MyDataHandler dataHandler;
    String[] str;
    public boolean[] b;

    public MyAdapter(Context context, MyDataHandler dataHandler)
    {
        this.context = context;
        this.dataHandler = dataHandler;
        b = new boolean[dataHandler.titles.size()];
    }
    @Override
    public int getCount() {
        return dataHandler.titles.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("GEIVEW", "position:" + position);
        ViewHolder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.textView);
            holder.tv.setTextColor((Color.parseColor("#660077")));
            holder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //final String msg = str[position];
        Log.d("Title", dataHandler.titles.get(position));
        holder.tv.setText(dataHandler.titles.get(position));
        Log.d("Img", dataHandler.imgs.get(position));
        holder.tv2.setText(dataHandler.context.get(position));
        Log.d("Text Length", ""+dataHandler.context.get(position).length());

        if (!dataHandler.imgs.get(position).equals(""))
            Picasso.with(context).load(dataHandler.imgs.get(position)).into(holder.iv);
        else
            holder.iv.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
    static class ViewHolder
    {
        TextView tv;
        TextView tv2;
        ImageView iv;
    }
}