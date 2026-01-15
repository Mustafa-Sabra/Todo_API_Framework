package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static javax.management.Query.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserTests {

    @Test
    public void shouldBeAbletoResigter()
    {
        User user = new User("mustafa","sabra" ,"ali2@gmail.com" ,"123456789");


        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName", equalTo("mustafa"));

    }

    @Test
    public void shouldNotBeAbletoResigterWithTheEmail()
    {
        User user = new User("mustafa","sabra" ,"ali2@gmail.com" ,"123456789");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldBeAbleToLogin()
    {
        User user = new User( "ali2@gmail.com" ,"123456789");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("firstName", equalTo("mustafa") )
                .assertThat().body("access_token" , notNullValue());
    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPassword()
    {
        User user = new User( "ali2@gmail.com" ,"12345678");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password") );
    }



}
