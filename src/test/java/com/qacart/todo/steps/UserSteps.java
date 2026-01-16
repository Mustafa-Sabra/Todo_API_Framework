package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.User;

public class UserSteps {

    public static User generateUser()
    {

        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "123456789";


        User user = new User(firstName,lastName,email,password);

        return user;
    }

    public static User getRegisteredUser() {
        User user = generateUser();

        UserApi.register(user);

        return  user;

    }

}
