package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static com.qacart.todo.steps.UserSteps.getRegisteredUser;
import static io.restassured.RestAssured.given;
import static javax.management.Query.not;
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
        assertThat(returnedError.getMessage(), equalTo("Email is already exists in the Database"));
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
        assertThat(returnedError.getMessage(), equalTo("The email and password combination is not correct, please fill a correct email and password"));

    }
}
