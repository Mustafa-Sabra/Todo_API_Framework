package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("Todo Feature")
public class TodoTests {

    @Story("should Be Able To Add Todo")
    @Test(description = "should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo() {
        Todo todo = TodoSteps.generateTodo();

        String token = UserSteps.getUserToken();

        Response response = TodoApi.addTodo(todo,token);

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(todo.getIsCompleted()));
    }

    @Story("should Not Be Able To Add Todo If Is Completed Is Missing")
    @Test(description = "should Not Be Able To Add Todo If Is Completed Is Missing")
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {
        Todo todo = TodoSteps.generateTodo();

        Todo addData = new Todo(todo.getItem());

        String token = UserSteps.getUserToken();

        Response response = TodoApi.addTodo(addData,token);


        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }

    @Story("should Be Able To Get A Todo With Id")
    @Test(description = "should Be Able To Get A Todo With Id")
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

    @Story("should Be Able To Delete A Todo")
    @Test(description = "should Be Able To Delete A Todo")
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
