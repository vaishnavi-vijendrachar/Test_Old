package com.example.vish.test.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vish.test.R;
import com.example.vish.test.service.model.DataModel;

import java.util.List;

/**
 * Created by admin on 25/7/2018.
 */


public class ListDetailsAdapter extends BaseAdapter {

    List<DataModel> mList;
    LayoutInflater mInflater;
    Context c;

    public ListDetailsAdapter(Context context,List<DataModel> list){
        mList = list;
        c = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null) {
            view = mInflater.inflate(R.layout.list_row, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.title_tv = (TextView)view.findViewById(R.id.title);
            viewHolder.subtitle_tv2 = (TextView)view.findViewById(R.id.subtitle);
            viewHolder.image_iv = (ImageView)view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        if(mList.get(i).getTitle() != null ) {
            viewHolder.title_tv.setText(mList.get(i).getTitle());
            viewHolder.subtitle_tv2.setText(mList.get(i).getDescription());
            //Glide to load the images
            Glide.with(c.getApplicationContext()).load(mList.get(i).getImageHref()).into(viewHolder.image_iv);
        }

        return  view;
    }

    /**
     * View Holder to make the list scrolling smooth
     */
    private static class ViewHolder{
        private TextView title_tv;
        private  TextView subtitle_tv2;
        private ImageView image_iv;
    }

}
