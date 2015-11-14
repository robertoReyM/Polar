package com.smartplace.polar.models;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class Specification {

    private String id;
    private String name;
    private ArrayList<Feature> features;

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

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }
}
