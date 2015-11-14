package com.smartplace.polar.models;

/**
 * Created by robertoreym on 17/10/15.
 */
public class Link {

    private String id;
    private LinkReference reference;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkReference getReference() {
        return reference;
    }

    public void setReference(LinkReference reference) {
        this.reference = reference;
    }
}
