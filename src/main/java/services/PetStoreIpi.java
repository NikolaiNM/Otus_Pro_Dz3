package services;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import dto.UserDTO;

import static io.restassured.RestAssured.given;


public class PetStoreIpi {

  private RequestSpecification spec;
  private static final String BASE_URL = "https://petstore.swagger.io/v2";
  private static final String USER_PATH = "/user";
  private static final String DELETE_USER = "/{username}";

  public PetStoreIpi() {
    spec = given()
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

  public ValidatableResponse deleteUser(String user) {
    return given(spec)
        .basePath(USER_PATH)
        .pathParam("username", user)
        .when()
        .delete(DELETE_USER)
        .then()
        .log().all();
  }

}
