package com.smartplace.polar.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartplace.polar.R;
import com.smartplace.polar.fragments.FeatureFragment;
import com.smartplace.polar.fragments.SpecificationFragment;
import com.smartplace.polar.listeners.OnFeatureListener;
import com.smartplace.polar.listeners.OnFileListener;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Specification;
import com.smartplace.polar.models.Requirement;

public class LinkActivity extends AppCompatActivity implements OnFileListener, OnFeatureListener {

    public static final String ARG_REQUIREMENT_ID = "requirement_id";
    public static final String ARG_FILE = "file_object";
    public static final String ARG_LINK_TYPE = "link_type";
    public static final String LINK_TYPE_IN = "link_in";
    public static final String LINK_TYPE_OUT = "link_out";

    private String mSourceID;
    private String mDestinationID;
    private String mLinkType;
    private Specification mSpecification;


    private SpecificationFragment mSpecificationFragment;
    private FeatureFragment mFeatureFragment;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpecification = new Gson().fromJson(getIntent().getStringExtra(ARG_FILE), Specification.class);
        mLinkType = getIntent().getStringExtra(ARG_LINK_TYPE);

        if(mLinkType.equals(LINK_TYPE_IN)){

            mDestinationID = getIntent().getStringExtra(ARG_REQUIREMENT_ID);

        }else if(mLinkType.equals(LINK_TYPE_OUT)){

            mSourceID = getIntent().getStringExtra(ARG_REQUIREMENT_ID);
        }

        getSupportActionBar().setTitle(mSpecification.getName());


        mSpecificationFragment = (SpecificationFragment)getFragmentManager().findFragmentById(R.id.fragment_file);
        mFeatureFragment = (FeatureFragment)getFragmentManager().findFragmentById(R.id.fragment_feature);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mFeatureFragment.setEnabled(false);
        mSpecificationFragment.setOnFileListener(this);
        mFeatureFragment.setOnFeatureListener(this);

        if(mDrawerLayout!=null) {

            mSpecificationFragment.setUp(
                    R.id.fragment_team,mDrawerLayout,findViewById(R.id.main_container));


            mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    if (drawerView.getId() == R.id.fragment_file) {
                        findViewById(R.id.main_container).setTranslationX(slideOffset * drawerView.getWidth());
                    }
                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });

            mDrawerLayout.openDrawer(GravityCompat.START);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onRequirementSelected(Requirement requirement) {

        if(mLinkType.equals(LINK_TYPE_IN)){

            mSourceID = requirement.getId();

        }else if(mLinkType.equals(LINK_TYPE_OUT)){

            mDestinationID = requirement.getId();
        }

        //Toast.makeText(getBaseContext(),requirement.getValue(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFeatureSelected(Feature feature) {

        mFeatureFragment.setFeature(feature);
    }
}
