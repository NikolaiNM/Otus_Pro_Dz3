package services;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.Specs;

import static io.restassured.RestAssured.given;

public class PetStoreUserApi {

  private final RequestSpecification SPEC;
  private static final String BASE_URL = "https://petstore.swagger.io/v2";
  private static final String USER_PATH = "/user";

  public PetStoreUserApi() {
    SPEC = given()
        .spec(Specs.requestSpec())
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  public ValidatableResponse createUser(UserDTO user) {
    return given(SPEC)
        .body(user)
        .basePath(USER_PATH)
        .when()
        .post()
        .then()
        .log().all();
  }

  public ValidatableResponse findUserByUsername(String username) {
    return given(SPEC)
        .basePath(USER_PATH + "/{username}")
        .pathParam("username", username)
        .when()
        .get()
        .then()
        .log().all();
  }

  public ValidatableResponse updateUser(UserDTO user) {
    return given(SPEC)
        .body(user)
        .basePath(USER_PATH + "/{username}")
        .pathParam("username", user.getUsername()) // Исправлено: передаем username
        .when()
        .put()
        .then()
        .log().all();
  }

  public ValidatableResponse deleteUser(String username) {
    return given(SPEC)
        .basePath(USER_PATH + "/{username}")
        .pathParam("username", username)
        .when()
        .delete()
        .then()
        .log().all();
  }
}