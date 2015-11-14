package com.smartplace.polar.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.smartplace.polar.R;
import com.smartplace.polar.adapters.FeaturesAdapter;
import com.smartplace.polar.listeners.OnFileListener;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Specification;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecificationFragment extends Fragment {

    private Specification mSpecification;
    private FeaturesAdapter mFeaturesAdapter;
    private ListView mListFeatures;
    private OnFileListener mListener;


    private View mFragmentContainerView;
    private View mContainerView;
    private DrawerLayout mDrawerLayout;
    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;

    public static SpecificationFragment newInstance() {
        SpecificationFragment fragment = new SpecificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SpecificationFragment() {
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
        View v = inflater.inflate(R.layout.fragment_file, container, false);
        mListFeatures = (ListView)v.findViewById(R.id.list_features);


        mSpecification = new Specification();
        mSpecification.setId("1");
        mSpecification.setName("");
        if(mSpecification.getFeatures()==null){

            mSpecification.setFeatures(new ArrayList<Feature>());
        }

        mFeaturesAdapter = new FeaturesAdapter(getActivity(), mSpecification.getFeatures());
        mListFeatures.setAdapter(mFeaturesAdapter);


        mListFeatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mListener != null) {

                    mListener.onFeatureSelected(mSpecification.getFeatures().get(i));
                }
            }
        });

        setFileData(getDummyFileData());
        return v;
    }

    public void setOnFileListener(OnFileListener onFileListener){

        mListener = onFileListener;

    }
    public void setFile(Specification specification){

        setFileData(specification);

    }

    public Specification getFile(){

        return mSpecification;
    }

    private void setFileData(Specification specification){

        mSpecification.setId(specification.getId());
        mSpecification.setName(specification.getName());
        mSpecification.getFeatures().clear();
        mSpecification.getFeatures().addAll(specification.getFeatures());
        mFeaturesAdapter.notifyDataSetChanged();

    }


    private Specification getDummyFileData(){

        Specification specification = new Specification();
        specification.setId("1");
        specification.setName("File");
        specification.setFeatures(new ArrayList<Feature>());
        //set
        for(int i2 = 0 ; i2<10;i2++){

            Feature feature = new Feature();
            feature.setId(String.valueOf(i2 + 1));
            feature.setName("#Feature " + String.valueOf(i2 + 1));
            specification.getFeatures().add(feature);
        }

        return specification;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout,View containerView) {

        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mContainerView = containerView;
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

    }
    public void disableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,mFragmentContainerView);
    }

    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, mFragmentContainerView);
    }

}
