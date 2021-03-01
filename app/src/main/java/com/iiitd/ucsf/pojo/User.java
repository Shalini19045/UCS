package com.iiitd.ucsf.pojo;

public class User {
    private String id;
    private String name;
    private String uid;


    public User() {
    }

    public User(String id, String name, String uid ) {
        this.id = id;
        this.name = name;
        this.uid = uid;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
