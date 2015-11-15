package com.smartplace.polar.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.smartplace.polar.R;
import com.smartplace.polar.adapters.FeaturesAdapter;
import com.smartplace.polar.adapters.SpecificationsAdapter;
import com.smartplace.polar.helpers.MemoryServices;
import com.smartplace.polar.helpers.WebServices;
import com.smartplace.polar.listeners.OnTeamListener;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Specification;
import com.smartplace.polar.models.Team;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {

    private Team mTeam;
    private int mCurrentSpecification = 0;
    private FeaturesAdapter mFeaturesAdapter;
    private SpecificationsAdapter mSpecificationsAdapter;
    private ListView mListFeatures;
    private Spinner mSpSpecifications;
    private Button mBtnSettings;
    private OnTeamListener mListener;


    private View mFragmentContainerView;
    private View mContainerView;
    private DrawerLayout mDrawerLayout;
    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;

    public static TeamFragment newInstance() {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TeamFragment() {
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
        View v = inflater.inflate(R.layout.fragment_team, container, false);
        View headerFeature = inflater.inflate(R.layout.list_header_feature, container, false);
        mListFeatures = (ListView)v.findViewById(R.id.list_features);
        mSpSpecifications = (Spinner)v.findViewById(R.id.sp_file);
        mBtnSettings = (Button) v.findViewById(R.id.btn_settings);
        mListFeatures.addHeaderView(headerFeature);

        ImageView ivBtnAddFile = (ImageView) v.findViewById(R.id.iv_btn_add_file);
        ImageView ivBtnAddFeature = (ImageView) headerFeature.findViewById(R.id.iv_btn_add_feature);

        mTeam = new Team();
        mTeam.setId("");
        mTeam.setName("");
        mTeam.setSpecifications(new ArrayList<Specification>());

        mSpecificationsAdapter = new SpecificationsAdapter(getActivity(),R.layout.list_item_file,mTeam.getSpecifications());
        mSpSpecifications.setAdapter(mSpecificationsAdapter);


        ivBtnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialDialog.Builder(getActivity())
                        .title(R.string.new_file)
                        .customView(R.layout.dialog_add_file, true)
                        .positiveText(R.string.add)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                String name = ((EditText) dialog.getView().findViewById(R.id.et_name)).getText().toString();
                                WebServices.addSpecification(MemoryServices.getPublicKey(getActivity()), mTeam.getId(), name, new WebServices.OnSpecificationListener() {
                                    @Override
                                    public void onSpecificationReceived(Specification specification) {

                                        mTeam.getSpecifications().add(specification);
                                        mSpecificationsAdapter.notifyDataSetChanged();
                                        mCurrentSpecification = mTeam.getSpecifications().size()-1;
                                        mSpSpecifications.setSelection(mCurrentSpecification);
                                        getTeamSpecification(mTeam,mCurrentSpecification);

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
            }
        });
        ivBtnAddFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialDialog.Builder(getActivity())
                        .title(R.string.new_section)
                        .customView(R.layout.dialog_add_feature, true)
                        .positiveText(R.string.add)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                String name = ((EditText) dialog.getView().findViewById(R.id.et_name)).getText().toString();
                                WebServices.addFeature(MemoryServices.getPublicKey(getActivity()),
                                        mTeam.getSpecifications().get(mCurrentSpecification).getId(),
                                        name, new WebServices.OnFeatureListener() {
                                            @Override
                                            public void onFeatureReceived(Feature feature) {

                                                mTeam.getSpecifications().get(mCurrentSpecification).getFeatures().add(feature);
                                                mFeaturesAdapter.notifyDataSetChanged();
                                                int size = mTeam.getSpecifications().get(mCurrentSpecification).getFeatures().size();
                                                mListener.onFeatureSelected(mTeam.getSpecifications().get(mCurrentSpecification).getFeatures().get(size-1));
                                                mFeaturesAdapter.setSelectedItem(size-1);
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
            }
        });

        mBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), mBtnSettings);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_settings, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        mListFeatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(mListener!=null && i!=0){

                    mListener.onFeatureSelected(mTeam.getSpecifications().get(mCurrentSpecification).getFeatures().get(i-1));
                    mFeaturesAdapter.setSelectedItem(i-1);
                }
            }
        });
        mSpSpecifications.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mCurrentSpecification = i;
                getTeamSpecification(mTeam, mCurrentSpecification);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

    public void setOnTeamListener(OnTeamListener onTeamListener){

        mListener = onTeamListener;

    }
    public void setTeam(Team team){

        if(isAdded()) {
            setTeamData(team);

        }

    }

    public Team getTeam(){

        return mTeam;
    }

    private void setTeamData(Team team){

        mTeam.setId(team.getId());
        mTeam.setName(team.getName());
        mTeam.getSpecifications().clear();
        mTeam.getSpecifications().addAll(team.getSpecifications());
        mSpecificationsAdapter.notifyDataSetChanged();

        mCurrentSpecification = 0;

        getTeamSpecification(mTeam, mCurrentSpecification);

    }

    private void getTeamSpecification(final Team team, final int currentFile){

        if(team.getSpecifications().size()>0) {

            WebServices.getSpecification(MemoryServices.getPublicKey(getActivity()), mTeam.getSpecifications().get(currentFile).getId(), new WebServices.OnSpecificationListener() {
                @Override
                public void onSpecificationReceived(Specification specification) {

                    if (isAdded()) {
                        team.getSpecifications().set(currentFile, specification);
                        mFeaturesAdapter = new FeaturesAdapter(getActivity(), mTeam.getSpecifications().get(currentFile).getFeatures());
                        mListFeatures.setAdapter(mFeaturesAdapter);

                        if (mTeam.getSpecifications().get(currentFile).getFeatures().size() > 0) {
                            mListFeatures.performItemClick(mListFeatures.getAdapter().getView(1, null, null), 1, mListFeatures.getItemIdAtPosition(1));
                        }
                    }
                }
            });

        }

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
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,mFragmentContainerView);
    }

    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, mFragmentContainerView);
    }

}
