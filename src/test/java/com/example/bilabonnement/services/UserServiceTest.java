package com.example.bilabonnement.services;

import com.example.bilabonnement.models.userElements.User;
import com.example.bilabonnement.repositories.UserRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void isUserInfoValid(){
        //Arrange
        var uService = new UserService();
        var ur = new UserRepo();
        User expectedUserWithIdOne = ur.getSingleEntityById(1);
        User expectedUserWithIdTwo = ur.getSingleEntityById(2);
        User expectedUserWithIdThree = ur.getSingleEntityById(3);
        User expectedUserWithIdFour = ur.getSingleEntityById(4);

        //Act
        User returnedUserAlfred = uService.validateUserinfo("Alfred", "Password1234");
        User returnedUserJames = uService.validateUserinfo("James", "Password1234");
        User returnedUserLars = uService.validateUserinfo("Lars", "Password1234");
        User returnedUserMorten = uService.validateUserinfo("Morten", "Password1234");

        //Assert
        assertEquals(expectedUserWithIdOne.getId(),returnedUserAlfred.getId());
        assertEquals(expectedUserWithIdTwo.getId(),returnedUserJames.getId());
        assertEquals(expectedUserWithIdThree.getId(),returnedUserLars.getId());
        assertEquals(expectedUserWithIdFour.getId(),returnedUserMorten.getId());

    }


}