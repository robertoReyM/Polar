package com.smartplace.polar.listeners;

import com.smartplace.polar.models.Requirement;

/**
 * Created by robertoreym on 19/10/15.
 */
public interface OnRequirementListener {

    void onLinkInButtonPressed(String requirementID);
    void onLinkOutButtonPressed(String requirementID);
    void onLinkInDeleted(String linkID);
    void onLinkOutDeleted(String linkID);
    void onRequirementDeleted(String requirementID);
    void onRequirementEdited(Requirement requirement);
    void onRequirementOrderWillChange(String requirementID);
}
