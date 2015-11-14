package com.smartplace.polar.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartplace.polar.R;
import com.smartplace.polar.custom.ProportionalImageView;
import com.smartplace.polar.helpers.Constants;
import com.smartplace.polar.models.Requirement;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class RequirementsAdapter extends BaseAdapter {

    private ArrayList<Requirement> mRequirements;
    private Context mContext;
    private int mSelectedItem = 0;

    public int getSelectedItem() {
        return mSelectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.mSelectedItem = selectedItem;
        notifyDataSetChanged();
    }


    public RequirementsAdapter (Context context,ArrayList<Requirement> requirements){

        mContext = context;
        mRequirements = requirements;

    }
    @Override
    public int getCount() {
        return mRequirements.size();
    }

    @Override
    public Object getItem(int i) {
        return mRequirements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Holder holder;

        if(view == null){

            holder = new Holder();

            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_requirement, null);
            holder.tvRequirement = (TextView) view.findViewById(R.id.tv_requirement);
            holder.ivRequirement = (ProportionalImageView) view.findViewById(R.id.iv_requirement);
            holder.llLinksIn = (LinearLayout) view.findViewById(R.id.ll_links_in);
            holder.llLinksOut = (LinearLayout) view.findViewById(R.id.ll_links_out);
            holder.llBackground = (LinearLayout) view.findViewById(R.id.ll_background);
            holder.tvLinksIn = (TextView) view.findViewById(R.id.tv_links_in);
            holder.tvLinksOut = (TextView) view.findViewById(R.id.tv_links_out);
            view.setTag(holder);

        }else{

            holder = (Holder)view.getTag();
        }

        Requirement requirement = mRequirements.get(position);

        holder.tvRequirement.setText(requirement.getValue());

        int inSize = requirement.getInLinks().size();
        int outSize = requirement.getOutLinks().size();

        if(inSize>0){

            holder.llLinksIn.setVisibility(View.VISIBLE);
            holder.tvLinksIn.setText(String.valueOf(inSize));

        }else{

            holder.llLinksIn.setVisibility(View.GONE);
        }

        if(outSize>0){

            holder.llLinksOut.setVisibility(View.VISIBLE);
            holder.tvLinksOut.setText(String.valueOf(outSize));
        }else{

            holder.llLinksOut.setVisibility(View.GONE);
        }


        if(requirement.getType() == Constants.TYPE_REQUIREMENT){

            holder.tvRequirement.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tvRequirement.setTypeface(Typeface.DEFAULT);
            holder.tvRequirement.setGravity(Gravity.START);
            holder.ivRequirement.setVisibility(View.GONE);

        }else if(requirement.getType() == Constants.TYPE_HEADER){

            holder.tvRequirement.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            holder.tvRequirement.setTypeface(Typeface.DEFAULT_BOLD);
            holder.tvRequirement.setGravity(Gravity.CENTER);
            holder.ivRequirement.setVisibility(View.GONE);


        }else if(requirement.getType() == Constants.TYPE_SUBHEADER){

            holder.tvRequirement.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tvRequirement.setTypeface(Typeface.DEFAULT_BOLD);
            holder.tvRequirement.setGravity(Gravity.START);
            holder.ivRequirement.setVisibility(View.GONE);


        }else if(requirement.getType() == Constants.TYPE_LINK){

        }else if(requirement.getType() == Constants.TYPE_IMAGE){


            holder.tvRequirement.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tvRequirement.setTypeface(Typeface.DEFAULT);
            holder.tvRequirement.setGravity(Gravity.START);
            holder.ivRequirement.setVisibility(View.VISIBLE);
            holder.ivRequirement.setImageBitmap(requirement.getImage());

        }else{

        }

        if(position == mSelectedItem){

            holder.llBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorRequirementSelected));
        }else{

            holder.llBackground.setBackgroundColor(ContextCompat.getColor(mContext,android.R.color.transparent));
        }
        return view;
    }

    static class Holder{

        TextView tvRequirement;
        ProportionalImageView ivRequirement;
        LinearLayout llLinksIn;
        LinearLayout llLinksOut;
        LinearLayout llBackground;
        TextView tvLinksIn;
        TextView tvLinksOut;

    }
}
