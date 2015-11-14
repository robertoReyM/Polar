package com.smartplace.polar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.smartplace.polar.R;
import com.smartplace.polar.models.Specification;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class SpecificationsAdapter extends ArrayAdapter implements SpinnerAdapter {

    private ArrayList<Specification> mSpecifications;
    private Context mContext;

    public SpecificationsAdapter(Context context, int textViewResourceId, ArrayList<Specification> specifications){
        super(context, textViewResourceId, specifications);
        mContext = context;
        mSpecifications = specifications;

    }
    @Override
    public int getCount() {
        return mSpecifications.size();
    }

    @Override
    public Object getItem(int i) {
        return mSpecifications.get(i);
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

            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_file, null);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);

        }else{

            holder = (Holder)view.getTag();
        }

        Specification specification = mSpecifications.get(i);

        holder.tvName.setText(specification.getName());

        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
            convertView = vi.inflate(R.layout.list_item_file_dropdown, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
        textView.setText(mSpecifications.get(position).getName());//after changing from ArrayList<String> to ArrayList<Object>


        return convertView;
    }



    static class Holder{

        TextView tvName;

    }
}
