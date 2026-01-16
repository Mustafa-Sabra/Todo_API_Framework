package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
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
        Todo todo = TodoSteps.generateTodo();

        String token = UserSteps.getUserToken();

        Response response = TodoApi.addTodo(todo,token);

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(todo.getIsCompleted()));
    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {
        Todo todo = TodoSteps.generateTodo();

        Todo addData = new Todo(todo.getItem());

        String token = UserSteps.getUserToken();

        Response response = TodoApi.addTodo(addData,token);


        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(), equalTo("\"isCompleted\" is required"));
    }

    @Test
    public void shouldBeAbleToGetATodoWithId() {

        String token = UserSteps.getUserToken();

        Todo todo = TodoSteps.generateTodo();

        String taskId = TodoSteps.getTaskId(token, todo);

        Response response = TodoApi.getTodo(token, taskId);


        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getIsCompleted() ,equalTo( todo.getIsCompleted()));
        assertThat(returnedTodo.getItem() ,equalTo( todo.getItem()));
    }

    @Test
    public void shouldBeAbleToDeleteATodo() {

        String token = UserSteps.getUserToken();

        Todo todo = TodoSteps.generateTodo();

        String taskId = TodoSteps.getTaskId(token,todo);



        Response response = TodoApi.deleteTodo(token, taskId);

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getIsCompleted(),equalTo( todo.getIsCompleted()));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
    }


}
