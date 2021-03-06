package com.example.bilabonnement.models.userElements;

public class User {

    private int id;
    private String name;
    private String password;
    private UserType userType;

    public User(int id, String name, String password, UserType userType) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userType = userType;
    }


    public int getId(){
        return this.id;
    }

    public UserType getUserType(){
        return this.userType;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "\nUser:" +
                "\n\tid = " + id +
                "\n\tname = " + name +
                "\n\tpassword = " + password +
                "\n\tuser type = " + userType +
                "\n";
    }
}
