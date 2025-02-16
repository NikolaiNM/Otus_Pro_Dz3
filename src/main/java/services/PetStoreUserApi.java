package services;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.Specs;

import static io.restassured.RestAssured.given;

public class PetStoreUserApi {

  private RequestSpecification spec;
  private static final String BASE_URL = "https://petstore.swagger.io/v2";
  private static final String USER_PATH = "/user";
  private static final String DELETE_USER = "/{username}";

  public PetStoreUserApi() {
    spec = given()
        .spec(Specs.requestSpec()) // Используем RequestSpecification
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  public ValidatableResponse createUser(UserDTO user) {
    return given(spec)
        .body(user)
        .basePath(USER_PATH)
        .when()
        .post()
        .then()
        .log().all();
  }

  public ValidatableResponse findUserByUsername(String username) {
    return given(spec)
        .basePath(USER_PATH + "/{username}")
        .pathParam("username", username)
        .when()
        .get()
        .then()
        .log().all();
  }

  public ValidatableResponse deleteUser(String username) {
    return given(spec)
        .basePath(USER_PATH + DELETE_USER)
        .pathParam("username", username)
        .when()
        .delete()
        .then()
        .log().all();
  }
}