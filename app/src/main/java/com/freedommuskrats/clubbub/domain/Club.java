package com.freedommuskrats.clubbub.domain;

public class Club {

    private String name;
    private String imageUrl = "data:image/png;base64,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";
    private String description; //"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"
    private int longitude;
    private int latitude;

    public Club(String name, String description, int longitude, int latitude) {
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
