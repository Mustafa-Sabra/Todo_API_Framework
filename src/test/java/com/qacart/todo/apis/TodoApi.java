package com.qacart.todo.apis;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {

    public static Response addTodo(Todo todo, String token)
    {
        return given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getTodo(String token, String taskId)
    {
        return given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get("/api/v1/tasks/" + taskId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodo(String token, String taskId)
    {
        return given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("/api/v1/tasks/" + taskId)
                .then()
                .log().all()
                .extract().response();
    }


}
