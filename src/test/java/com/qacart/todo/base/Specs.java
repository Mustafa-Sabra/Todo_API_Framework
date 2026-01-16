package com.qacart.todo.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    public static String getEnv()
    {
        String env = System.getProperty("env", "PRODUCTION");
        String baseURI;

        switch (env)
        {
            case "PRODUCTION":
                baseURI = "https://qacart-todo.herokuapp.com";
            break;
            case "LOCAL":
                baseURI = "https://localhost:8080";
            break;
            default:
                throw new RuntimeException("Environment is not supported");
        }

        return baseURI;
    }

        public static RequestSpecification getRequestSpecs()
        {
            return given()
                    .baseUri(getEnv())
                    .contentType(ContentType.JSON)
                    .log().all();
        }

}
