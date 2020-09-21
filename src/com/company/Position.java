package com.company;

import java.util.List;

public class Position {
    private String title;
    private String location;
    private List<String> description;
    private List<String> qualification;
    private String posted_by;

    public Position(String title, String location, List<String> description, List<String> qualification, String posted_by) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.qualification = qualification;
        this.posted_by = posted_by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getQualification() {
        return qualification;
    }

    public void setQualification(List<String> qualification) {
        this.qualification = qualification;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }
}
