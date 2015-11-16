package com.smartplace.polar.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by robertoreym on 17/10/15.
 */
public class Requirement {

    private String id;
    private int type;
    private String value;
    private Bitmap image;
    private ArrayList<Link> inLinks;
    private ArrayList<Link> outLinks;
    private ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Link> getInLinks() {
        return inLinks;
    }

    public void setInLinks(ArrayList<Link> inLinks) {
        this.inLinks = inLinks;
    }

    public ArrayList<Link> getOutLinks() {
        return outLinks;
    }

    public void setOutLinks(ArrayList<Link> outLinks) {
        this.outLinks = outLinks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
