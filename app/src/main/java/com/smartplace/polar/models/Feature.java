package com.smartplace.polar.models;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class Feature {

    private String id;
    private String name;
    private ArrayList<Requirement> items;

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

    public ArrayList<Requirement> getItems() {
        return items;
    }

    public void setItems(ArrayList<Requirement> items) {
        this.items = items;
    }

    public Requirement getItemByID(String id){

        for(Requirement requirement: items){

            if(id.equals(requirement.getId())){

                return requirement;
            }
        }

        return null;
    }
}
