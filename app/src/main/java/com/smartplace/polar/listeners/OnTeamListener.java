package com.smartplace.polar.listeners;

import com.smartplace.polar.models.Feature;

/**
 * Created by robertoreym on 19/10/15.
 */
public interface OnTeamListener {

    void onFeatureSelected(Feature feature);
    void onChangeTeamSelected();
}
