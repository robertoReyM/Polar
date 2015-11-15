package com.smartplace.polar.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.smartplace.polar.R;
import com.smartplace.polar.helpers.MemoryServices;
import com.smartplace.polar.helpers.WebServices;
import com.smartplace.polar.models.Team;
import com.smartplace.polar.models.User;

public class CreateTeamActivity extends AppCompatActivity {

    public static final  int REQUEST_CREATE_TEAM = 678;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText etTeamName = (EditText)findViewById(R.id.et_team_name);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String teamName = etTeamName.getText().toString();

                if(TextUtils.isEmpty(teamName)){
                    etTeamName.setError(getString(R.string.error_field_required));

                }else{

                    WebServices.userCreateTeam(MemoryServices.getPublicKey(getBaseContext()), teamName, new WebServices.OnCreateTeamListener() {
                        @Override
                        public void onTeamCreated(Team team) {

                            if(team!=null) {

                                User user = new Gson().fromJson(MemoryServices.getUserData(getBaseContext()), User.class);
                                user.getTeams().add(team);
                                MemoryServices.setUserData(getBaseContext(),new Gson().toJson(user));
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

}
