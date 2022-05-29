package com.example.bilabonnement.services;

import com.example.bilabonnement.models.users.User;
import com.example.bilabonnement.models.users.UserType;
import com.example.bilabonnement.repositories.UserRepo;

import java.util.ArrayList;

public class UserService {

    public User validateUserinfo(String userName, String password) {

        ArrayList<User> users = (ArrayList<User>) new UserRepo().getAllEntities();

        for (User user : users) {
            if(user.getName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public UserType determineBrugertype(String brugernavn, String password) {

        User user = validateUserinfo(brugernavn, password);

        if(user == null) {
            return UserType.NONUSER;
        }

        return user.getUserType();
    }

}
