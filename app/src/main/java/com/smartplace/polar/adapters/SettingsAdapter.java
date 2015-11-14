package com.smartplace.polar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartplace.polar.R;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class SettingsAdapter extends BaseAdapter {

    private ArrayList<String> mSettings;
    private Context mContext;

    public SettingsAdapter(Context context, ArrayList<String> settings){

        mContext = context;
        mSettings = settings;

    }
    @Override
    public int getCount() {
        return mSettings.size();
    }

    @Override
    public Object getItem(int i) {
        return mSettings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;

        if(view == null){

            holder = new Holder();

            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_setting, null);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);

        }else{

            holder = (Holder)view.getTag();
        }

        String setting = mSettings.get(i);

        holder.tvName.setText(setting);

        return view;
    }

    static class Holder{

        TextView tvName;

    }
}
