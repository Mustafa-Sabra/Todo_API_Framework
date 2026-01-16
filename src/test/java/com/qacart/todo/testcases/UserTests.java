package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTests {

    @Test
    public void shouldBeAbletoResigter()
    {
        User user = UserSteps.generateUser();


        Response response = UserApi.register(user);


        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));

    }

    @Test
    public void shouldNotBeAbletoResigterWithTheEmail()
    {
        User user = UserSteps.getRegisteredUser();

        Response response = UserApi.register(user);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_ALREADY_REGISTERED));
    }

    @Test
    public void shouldBeAbleToLogin()
    {
        User user = UserSteps.getRegisteredUser();

        User loginData = new User(user.getEmail(),user.getPassword());

        Response response = UserApi.login(loginData);

        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccessToken(), notNullValue());

    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPassword() {
        User user = UserSteps.getRegisteredUser();

        User loginData = new User(user.getEmail(), "wrong password");

        Response response = UserApi.login(loginData);


        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));

    }
}
