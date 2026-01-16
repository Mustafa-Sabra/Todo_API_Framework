package com.qacart.todo.apis;

import com.qacart.todo.models.Todo;
import com.qacart.todo.data.Route;
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
                .post(Route.TODOS_ROUTE)
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
                .get(Route.TODOS_ROUTE + "/" + taskId)
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
                .delete(Route.TODOS_ROUTE + "/" + taskId)
                .then()
                .log().all()
                .extract().response();
    }


}
