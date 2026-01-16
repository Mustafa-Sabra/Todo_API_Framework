package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static javax.management.Query.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTests {

    @Test
    public void shouldBeAbletoResigter()
    {
        User user = new User("mustafa","sabra" ,"ali5@gmail.com" ,"123456789");


        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();


        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));

    }

    @Test
    public void shouldNotBeAbletoResigterWithTheEmail()
    {
        User user = new User("mustafa","sabra" ,"ali2@gmail.com" ,"123456789");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .extract().response();

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldBeAbleToLogin()
    {
        User user = new User( "ali2@gmail.com" ,"123456789");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/login")
                .then()
                .log().all()
                .extract().response();

        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUser.getFirstName(), equalTo("mustafa"));
        assertThat(returnedUser.getAccessToken(), notNullValue());

    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPassword() {
        User user = new User("ali2@gmail.com", "12345678");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/login")
                .then()
                .log().all()
                .extract().response();


        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo("The email and password combination is not correct, please fill a correct email and password"));


    }
}
