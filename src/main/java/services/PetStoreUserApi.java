package services;
import static io.restassured.RestAssured.given;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.Specs;

public class PetStoreUserApi {

  private final RequestSpecification spec;
  private static final String BASE_URL = System.getProperty("base.url");
  private static final String USER_PATH = "/user";

  public PetStoreUserApi() {
    spec = given()
        .spec(Specs.requestSpec())
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

  public ValidatableResponse updateUser(UserDTO user) {
    return given(spec)
        .body(user)
        .basePath(USER_PATH + "/{username}")
        .pathParam("username", user.getUsername()) // Исправлено: передаем username
        .when()
        .put()
        .then()
        .log().all();
  }

  public ValidatableResponse deleteUser(String username) {
    return given(spec)
        .basePath(USER_PATH + "/{username}")
        .pathParam("username", username)
        .when()
        .delete()
        .then()
        .log().all();
  }
}