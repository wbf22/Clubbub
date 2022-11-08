package com.freedommuskrats.clubbub.domain;

import java.io.Serializable;

public class Post implements Serializable {

    private String personName;
    private String text;

    public Post(String personName, String text) {
        this.personName = personName;
        this.text = text;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
