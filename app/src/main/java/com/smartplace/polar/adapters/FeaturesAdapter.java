package com.smartplace.polar.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartplace.polar.R;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Requirement;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class FeaturesAdapter extends BaseAdapter {

    private ArrayList<Feature> mFeatures;
    private Context mContext;

    private int mSelectedItem = 0;

    public int getSelectedItem() {
        return mSelectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.mSelectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public FeaturesAdapter(Context context, ArrayList<Feature> features){

        mContext = context;
        mFeatures = features;

    }
    @Override
    public int getCount() {
        return mFeatures.size();
    }

    @Override
    public Object getItem(int i) {
        return mFeatures.get(i);
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

            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_feature, null);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.mLlBackground = (LinearLayout)view.findViewById(R.id.ll_background);
            view.setTag(holder);

        }else{

            holder = (Holder)view.getTag();
        }


        Feature feature = mFeatures.get(i);

        holder.tvName.setText(feature.getName());

        if(i == mSelectedItem){

            holder.mLlBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorFeatureSelected));
        }else{

            holder.mLlBackground.setBackgroundColor(ContextCompat.getColor(mContext,android.R.color.transparent));
        }


        return view;
    }

    static class Holder{

        LinearLayout mLlBackground;
        TextView tvName;

    }
}
