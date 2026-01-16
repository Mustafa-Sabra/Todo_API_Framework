package com.qacart.todo.testcases;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTests {

    @Test
    public void shouldBeAbleToAddTodo() {
        Todo todo = new Todo(false, "new item");

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5NjhiN2M0MTliYjRjMDAxNTc5MjNmMCIsImZpcnN0TmFtZSI6Im11c3RhZmEiLCJsYXN0TmFtZSI6InNhYnJhIiwiaWF0IjoxNzY4NDg2NDYzfQ.gUevV0KpEv9gAeVGFYcKFcWNIkMfcH9vsLsIGpMb1FU";


        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(todo.getIsCompleted()));
    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {
        Todo todo = new Todo("new item");

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5NjhiN2M0MTliYjRjMDAxNTc5MjNmMCIsImZpcnN0TmFtZSI6Im11c3RhZmEiLCJsYXN0TmFtZSI6InNhYnJhIiwiaWF0IjoxNzY4NDg2NDYzfQ.gUevV0KpEv9gAeVGFYcKFcWNIkMfcH9vsLsIGpMb1FU";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();


                Error returnedError = response.body().as(Error.class);

                assertThat(response.statusCode(),equalTo(400));
                assertThat(returnedError.getMessage(), equalTo("\"isCompleted\" is required"));
    }

    @Test
    public void shouldBeAbleToGetATodoWithId() {
        String taskId = "69698cc119bb4c001579303d";

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5NjhiN2M0MTliYjRjMDAxNTc5MjNmMCIsImZpcnN0TmFtZSI6Im11c3RhZmEiLCJsYXN0TmFtZSI6InNhYnJhIiwiaWF0IjoxNzY4NDg2NDYzfQ.gUevV0KpEv9gAeVGFYcKFcWNIkMfcH9vsLsIGpMb1FU";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get("/api/v1/tasks/" + taskId)
                .then()
                .log().all()
                .extract().response();


        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getIsCompleted() ,equalTo( false));
        assertThat(returnedTodo.getItem() ,equalTo( "new item"));
    }

    @Test
    public void shouldBeAbleToDeleteATodo() {
        String taskId = "6969fdb729993a0015e5dda5";

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5NjhiN2M0MTliYjRjMDAxNTc5MjNmMCIsImZpcnN0TmFtZSI6Im11c3RhZmEiLCJsYXN0TmFtZSI6InNhYnJhIiwiaWF0IjoxNzY4NDg2NDYzfQ.gUevV0KpEv9gAeVGFYcKFcWNIkMfcH9vsLsIGpMb1FU";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("/api/v1/tasks/" + taskId)
                .then()
                .log().all()
                .extract().response();

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getIsCompleted(),equalTo( false));
        assertThat(returnedTodo.getItem(), equalTo("new item"));
    }


}
