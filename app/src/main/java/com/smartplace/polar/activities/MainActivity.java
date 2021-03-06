package com.smartplace.polar.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.smartplace.polar.R;
import com.smartplace.polar.fragments.FeatureFragment;
import com.smartplace.polar.fragments.RequirementFragment;
import com.smartplace.polar.fragments.TeamFragment;
import com.smartplace.polar.helpers.MemoryServices;
import com.smartplace.polar.helpers.WebServices;
import com.smartplace.polar.listeners.OnFeatureListener;
import com.smartplace.polar.listeners.OnRequirementListener;
import com.smartplace.polar.listeners.OnTeamListener;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Specification;
import com.smartplace.polar.models.Requirement;
import com.smartplace.polar.models.Team;
import com.smartplace.polar.models.User;

public class MainActivity extends AppCompatActivity implements OnTeamListener, OnFeatureListener, OnRequirementListener{

    public static final int REQUEST_LINK = 345;
    private TeamFragment mTeamFragment;
    private FeatureFragment mFeatureFragment;
    private RequirementFragment mRequirementFragment;
    private DrawerLayout mDrawerLayout;
    private User mUser;
    private int mCurrentTeam = 0;
    private String mRequirementInEdition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTeamFragment = (TeamFragment)getFragmentManager().findFragmentById(R.id.fragment_team);
        mFeatureFragment = (FeatureFragment)getFragmentManager().findFragmentById(R.id.fragment_feature);
        mRequirementFragment = (RequirementFragment)getFragmentManager().findFragmentById(R.id.fragment_requirement);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mTeamFragment.setOnTeamListener(this);
        mFeatureFragment.setOnFeatureListener(this);
        mRequirementFragment.setOnRequirementListener(this);

        mUser = new Gson().fromJson(MemoryServices.getUserData(getBaseContext()),User.class);

        if(mDrawerLayout!=null) {

            mTeamFragment.setUp(
                    R.id.fragment_team,mDrawerLayout,findViewById(R.id.main_container));

            mRequirementFragment.setUp(
                    R.id.fragment_requirement,mDrawerLayout,findViewById(R.id.main_container));

            mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    if (drawerView.getId() == R.id.fragment_requirement) {
                        mTeamFragment.disableDrawer();
                        findViewById(R.id.main_container).setTranslationX(-slideOffset * drawerView.getWidth());
                    } else if (drawerView.getId() == R.id.fragment_team) {
                        mRequirementFragment.disableDrawer();
                        findViewById(R.id.main_container).setTranslationX(slideOffset * drawerView.getWidth());
                    }
                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    if (drawerView.getId() == R.id.fragment_requirement) {
                        mTeamFragment.enableDrawer();
                    } else if (drawerView.getId() == R.id.fragment_team) {
                        mRequirementFragment.enableDrawer();
                    }
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        }

        WebServices.getUserTeam(MemoryServices.getPublicKey(getBaseContext()),
                mUser.getTeams().get(mCurrentTeam).getId(), new WebServices.OnUserTeamListener() {
            @Override
            public void onUserTeamReceived(Team team) {

                if(team!=null) {
                    mUser.getTeams().set(mCurrentTeam, team);
                    mTeamFragment.setTeam(team);
                }
            }
        });
    }


    @Override
    public void onRequirementSelected(final Requirement requirement) {

        if(mRequirementInEdition!=null){

            //it means order will change
            new MaterialDialog.Builder(this)
                    .title(R.string.move_to)
                    .items(R.array.move_to_array)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            /**
                             * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                             * returning false here won't allow the newly selected radio button to actually be selected.
                             **/
                            mRequirementInEdition = null;
                            //Here the order changed depending the option chosen
                            mFeatureFragment.changeRequirementOrder(mRequirementInEdition,requirement.getId(),which);
                            return true;
                        }
                    })
                    .show();

        }else {
            mRequirementFragment.setRequirement(requirement);
        }
    }

    @Override
    public void onLinkInButtonPressed(String requirementID) {

        setFileDialog(requirementID,LinkActivity.LINK_TYPE_IN);
    }

    @Override
    public void onLinkOutButtonPressed(String requirementID) {

        setFileDialog(requirementID, LinkActivity.LINK_TYPE_OUT);
    }

    @Override
    public void onLinkInDeleted(String linkID) {

    }

    @Override
    public void onLinkOutDeleted(String linkID) {

    }

    @Override
    public void onRequirementDeleted(String requirementID) {

        mFeatureFragment.removeSelectedRequirement();
    }

    @Override
    public void onRequirementEdited(Requirement requirement) {

        mFeatureFragment.getSpecificationFeature();
    }


    @Override
    public void onRequirementOrderWillChange(String requirementID) {

        mRequirementInEdition = requirementID;
        Snackbar.make(findViewById(android.R.id.content), "Elige el requerimiento de referencia", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onFeatureSelected(Feature feature) {

        mRequirementFragment.setRequirement(null);
        mFeatureFragment.setFeature(feature);
        if(mDrawerLayout!=null){

            mTeamFragment.closeDrawer();
        }
    }

    @Override
    public void onChangeTeamSelected() {
        setTeamDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_LINK) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                String sourceID = data.getStringExtra(LinkActivity.SOURCE_ID);
                String destinationID = data.getStringExtra(LinkActivity.DESTINATION_ID);

                Snackbar.make(findViewById(android.R.id.content),String.format("source:%s destination:%s",sourceID,destinationID),Snackbar.LENGTH_SHORT).show();
            }
        }else if (requestCode == CreateTeamActivity.REQUEST_CREATE_TEAM) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                mUser = new Gson().fromJson(MemoryServices.getUserData(getBaseContext()), User.class);

                mCurrentTeam = mUser.getTeams().size()-1;
                mTeamFragment.setTeam(mUser.getTeams().get(mCurrentTeam));
            }
        }
    }
    private void setFileDialog(final String requirementID, final String linkType){

        Team team = mTeamFragment.getTeam();
        final String files[] = new String[team.getSpecifications().size()];

        int i = 0;
        for(Specification specification : team.getSpecifications()){

            files[i] = specification.getName();
            i++;
        }

        new MaterialDialog.Builder(this)
                .title(R.string.choose_file)
                .items(files)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        Intent intent = new Intent(getBaseContext(),LinkActivity.class);
                        intent.putExtra(LinkActivity.ARG_REQUIREMENT_ID,requirementID);
                        intent.putExtra(LinkActivity.ARG_LINK_TYPE,linkType);
                        intent.putExtra(LinkActivity.ARG_FILE,new Gson().toJson(mTeamFragment.getTeam().getSpecifications().get(which)));
                        startActivityForResult(intent,REQUEST_LINK);

                        Toast.makeText(getBaseContext(),text,Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .show();
    }
    private void setTeamDialog(){

        final String files[] = new String[mUser.getTeams().size()+1];

        int i = 0;
        for(Team team : mUser.getTeams()){

            files[i] = team.getName();
            i++;
        }
        files[files.length-1]=getString(R.string.add_team);

        new MaterialDialog.Builder(this)
                .title(R.string.choose_file)
                .items(files)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text.equals(getString(R.string.add_team))) {

                            Intent intent = new Intent(getBaseContext(),CreateTeamActivity.class);
                            startActivityForResult(intent,CreateTeamActivity.REQUEST_CREATE_TEAM);

                        } else {
                            mCurrentTeam = which;
                            mTeamFragment.setTeam(mUser.getTeams().get(mCurrentTeam));
                        }
                        return true;
                    }
                })
                .show();
    }
}
