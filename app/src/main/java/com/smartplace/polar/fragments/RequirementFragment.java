package com.smartplace.polar.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.smartplace.polar.R;
import com.smartplace.polar.helpers.Constants;
import com.smartplace.polar.helpers.MemoryServices;
import com.smartplace.polar.helpers.Utilities;
import com.smartplace.polar.helpers.WebServices;
import com.smartplace.polar.listeners.OnRequirementListener;
import com.smartplace.polar.models.Comment;
import com.smartplace.polar.models.Link;
import com.smartplace.polar.models.LinkReference;
import com.smartplace.polar.models.Requirement;
import com.smartplace.polar.models.Specification;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequirementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequirementFragment extends Fragment {

    private Requirement mRequirement;

    private TextView mTvType;
    private TextView mTvRequirement;
    private ImageView mIvRequirement;
    private TextView mTvInLinks;
    private TextView mTvOutLinks;
    private LinearLayout mLlInLinks;
    private LinearLayout mLlOutLinks;
    private FloatingActionButton mFabAddInLink;
    private FloatingActionButton mFabAddOutLink;
    private OnRequirementListener mListener;
    private View mView;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private View mContainerView;

    public static RequirementFragment newInstance() {
        RequirementFragment fragment = new RequirementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RequirementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_requirement, container, false);
        mView.setVisibility(View.INVISIBLE);

        mTvType = (TextView)mView.findViewById(R.id.tv_type);
        mTvRequirement = (TextView)mView.findViewById(R.id.tv_requirement);
        mIvRequirement = (ImageView)mView.findViewById(R.id.iv_requirement);
        mTvInLinks = (TextView)mView.findViewById(R.id.tv_links_in);
        mTvOutLinks = (TextView)mView.findViewById(R.id.tv_links_out);
        mLlInLinks = (LinearLayout) mView.findViewById(R.id.ll_in_links);
        mLlOutLinks = (LinearLayout) mView.findViewById(R.id.ll_out_links);
        mFabAddInLink = (FloatingActionButton)mView.findViewById(R.id.fab_add_link_in);
        mFabAddOutLink = (FloatingActionButton)mView.findViewById(R.id.fab_add_link_out);
        ImageView ivbBtnEdit = (ImageView)mView.findViewById(R.id.btn_edit);
        ImageView ivbBtnMove = (ImageView)mView.findViewById(R.id.btn_move);
        ImageView ivbBtnDelete = (ImageView)mView.findViewById(R.id.btn_delete);

        mRequirement = new Requirement();
        mRequirement.setId("1");
        mRequirement.setType(1);
        mRequirement.setValue("");
        mRequirement.setInLinks(new ArrayList<Link>());
        mRequirement.setOutLinks(new ArrayList<Link>());
        mRequirement.setComments(new ArrayList<Comment>());



        mFabAddInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onLinkInButtonPressed(mRequirement.getId());
            }
        });

        mFabAddOutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onLinkOutButtonPressed(mRequirement.getId());
            }
        });

        mTvInLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mLlInLinks.getVisibility() == View.VISIBLE){

                    Utilities.collapse(mLlInLinks);
                }else{

                    Utilities.expand(mLlInLinks);
                }
            }
        });

        mTvOutLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mLlOutLinks.getVisibility() == View.VISIBLE){

                    Utilities.collapse(mLlOutLinks);
                }else{

                    Utilities.expand(mLlOutLinks);
                }
            }
        });

        ivbBtnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onRequirementOrderWillChange(mRequirement.getId());
            }
        });
        ivbBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title(R.string.edit_item)
                        .customView(R.layout.dialog_edit_item, true)
                        .positiveText(R.string.save)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                final String value = ((EditText) dialog.getView().findViewById(R.id.et_value)).getText().toString();
                                WebServices.editItem(MemoryServices.getPublicKey(getActivity()),
                                        mRequirement.getId(), value, new WebServices.OnActionListener() {
                                    @Override
                                    public void onSuccess() {

                                        mRequirement.setValue(value);
                                        mListener.onRequirementEdited(mRequirement);
                                    }

                                    @Override
                                    public void onFailed(String failReason) {

                                        Snackbar.make(view,failReason,Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .show();

                EditText etValue = ((EditText) dialog.getView().findViewById(R.id.et_value));
                etValue.setText(mRequirement.getValue());
            }
        });
        ivbBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                WebServices.deleteItem(MemoryServices.getPublicKey(getActivity()),
                        mRequirement.getId(), new WebServices.OnActionListener() {
                    @Override
                    public void onSuccess() {

                        mListener.onRequirementDeleted(mRequirement.getId());
                        setRequirement(null);
                    }

                    @Override
                    public void onFailed(String failReason) {

                        Snackbar.make(view,failReason,Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Utilities.collapse(mLlInLinks);
        Utilities.collapse(mLlOutLinks);
        return mView;
    }

    public void setRequirement(Requirement requirement){

        if(isAdded()) {
            if (requirement != null) {
                mView.setVisibility(View.VISIBLE);
                setRequirementData(requirement);
            } else {

                mView.setVisibility(View.INVISIBLE);
            }
        }

    }



    public void setRequirementData(Requirement requirement){


        mRequirement.setId(requirement.getId());
        mRequirement.setValue(requirement.getValue());
        mRequirement.setType(requirement.getType());

        if(requirement.getComments()!=null) {
            mRequirement.setComments(requirement.getComments());
        }
        if(requirement.getInLinks()!=null) {
            mRequirement.setInLinks(requirement.getInLinks());
        }
        if(requirement.getOutLinks()!=null) {
            mRequirement.setOutLinks(requirement.getOutLinks());
        }

        //change view
        mTvRequirement.setText(mRequirement.getValue());
        mLlInLinks.removeAllViews();
        mLlOutLinks.removeAllViews();

        if(mRequirement.getType()== Constants.TYPE_IMAGE){

            mIvRequirement.setVisibility(View.VISIBLE);
            mIvRequirement.setImageBitmap(requirement.getImage());
        }else{
            mIvRequirement.setVisibility(View.GONE);
        }
        mTvType.setText(Utilities.getRequirementTypeByID(mRequirement.getType()));
        int inSize = mRequirement.getInLinks().size();
        int outSize = mRequirement.getOutLinks().size();


        mTvInLinks.setText(String.format("%s (%s)", getString(R.string.in_links), String.valueOf(inSize)));
        mTvOutLinks.setText(String.format("%s (%s)", getString(R.string.out_links), String.valueOf(outSize)));

        for(int i = 0; i<inSize;i++){

            View v  = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_link_reference, null);
            TextView tvRequirement = (TextView)v.findViewById(R.id.tv_requirement);
            TextView tvFile = (TextView)v.findViewById(R.id.tv_link_file);
            TextView tvFeature = (TextView)v.findViewById(R.id.tv_link_feature);

            tvRequirement.setText(mRequirement.getInLinks().get(i).getReference().getRequirementValue());
            tvFile.setText(mRequirement.getInLinks().get(i).getReference().getFileName());
            tvFeature.setText(mRequirement.getInLinks().get(i).getReference().getFeatureName());
            mLlInLinks.addView(v);

        }
        for(int i = 0; i<outSize;i++){

            View v  = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_link_reference, null);
            mLlOutLinks.addView(v);

        }

    }
    public void setOnRequirementListener(OnRequirementListener onRequirementListener){

        mListener = onRequirementListener;

    }
    public void setUp(int fragmentId, DrawerLayout drawerLayout,View containerView) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mContainerView = containerView;
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

    }
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
    public void disableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mFragmentContainerView);
    }

    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, mFragmentContainerView);
    }
}
