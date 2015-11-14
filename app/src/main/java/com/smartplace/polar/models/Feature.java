package com.smartplace.polar.models;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class Feature {

    private String id;
    private String name;
    private ArrayList<Requirement> requirements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<Requirement> requirements) {
        this.requirements = requirements;
    }
}
