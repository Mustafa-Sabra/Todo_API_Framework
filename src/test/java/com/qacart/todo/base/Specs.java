package com.qacart.todo.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

        public static RequestSpecification getRequestSpecs()
        {
            return given()
                    .baseUri("https://qacart-todo.herokuapp.com")
                    .contentType(ContentType.JSON)
                    .log().all();
        }

}
