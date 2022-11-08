package com.freedommuskrats.clubbub.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Person implements Serializable {

    private UUID id;
    private String firstName;
    private String lastName;
    private List<Club> clubsOwned;
    private List<Club> clubsMemberOf;
    private int longitude;
    private int latitude;


    public Person(UUID id, String firstName, String lastName, int longitude, int latitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Club> getClubsOwned() {
        return clubsOwned;
    }

    public void setClubsOwned(List<Club> clubsOwned) {
        this.clubsOwned = clubsOwned;
    }

    public List<Club> getClubsMemberOf() {
        return clubsMemberOf;
    }

    public void setClubsMemberOf(List<Club> clubsMemberOf) {
        this.clubsMemberOf = clubsMemberOf;
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
