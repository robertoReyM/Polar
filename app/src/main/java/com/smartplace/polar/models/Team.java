package com.smartplace.polar.models;

import java.util.ArrayList;

/**
 * Created by robertoreym on 19/10/15.
 */
public class Team {

    private String id;
    private String name;
    private ArrayList<Specification> specifications;

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

    public ArrayList<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<Specification> specifications) {
        this.specifications = specifications;
    }
}
