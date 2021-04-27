package com.example.meteodairy.models;

public class City {
    private int id;
    private String name;

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    private String urlName;

    public City(int id, String urlName, String name) {
        this.id = id;
        this.urlName = urlName;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
