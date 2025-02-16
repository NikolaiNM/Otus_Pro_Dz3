package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {

  public static RequestSpecification requestSpec() {
    return new RequestSpecBuilder()
        .setBaseUri("https://petstore.swagger.io/v2")
        .setContentType("application/json")
        .build();
  }

  public static ResponseSpecification responseSpec(int statusCode) {
    return new ResponseSpecBuilder()
        .expectStatusCode(statusCode)
        .build();
  }
}