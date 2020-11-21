package com.project.foodinfo;

public class MemInfo {
    private String id;
    private String name;
    private String email;
    private String password;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private int check_owner;

    public MemInfo(){}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheck_owner(int check_owner) {
        this.check_owner = check_owner;
    }
}
