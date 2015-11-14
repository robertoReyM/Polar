package com.smartplace.polar.models;

/**
 * Created by robertoreym on 17/10/15.
 */
public class LinkReference {

    private String fileID;
    private String fileName;
    private String featureID;
    private String featureName;
    private String requirementID;
    private int requirementType;
    private String requirementValue;

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFeatureID() {
        return featureID;
    }

    public void setFeatureID(String featureID) {
        this.featureID = featureID;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getRequirementID() {
        return requirementID;
    }

    public void setRequirementID(String requirementID) {
        this.requirementID = requirementID;
    }

    public int getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(int requirementType) {
        this.requirementType = requirementType;
    }

    public String getRequirementValue() {
        return requirementValue;
    }

    public void setRequirementValue(String requirementValue) {
        this.requirementValue = requirementValue;
    }
}
