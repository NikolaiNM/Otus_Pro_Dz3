package services;
import static io.restassured.RestAssured.given;

import dto.UserDTO;
import io.restassured.response.ValidatableResponse;

public class PetStoreUserApi extends BaseApiClient {

  private static final String USER_PATH = "/user";

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
        .pathParam("username", user.getUsername())
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