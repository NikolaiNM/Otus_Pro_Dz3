package services;

import dto.PetDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.Specs;

import static io.restassured.RestAssured.given;

public class PetStorePetApi {

  private final RequestSpecification SPEC;
  private static final String BASE_URL = "https://petstore.swagger.io/v2";
  private static final String PET_PATH = "/pet";

  public PetStorePetApi() {
    SPEC = given()
        .spec(Specs.requestSpec())
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  public ValidatableResponse createPet(PetDTO pet) {
    return given(SPEC)
        .body(pet)
        .basePath(PET_PATH)
        .when()
        .post()
        .then()
        .log().all();
  }

  public ValidatableResponse findPetById(long petId) {
    return given(SPEC)
        .basePath(PET_PATH + "/{petId}")
        .pathParam("petId", petId)
        .when()
        .get()
        .then()
        .log().all();
  }

  public ValidatableResponse deletePet(long petId) {
    return given(SPEC)
        .basePath(PET_PATH + "/{petId}")
        .pathParam("petId", petId)
        .when()
        .delete()
        .then()
        .log().all();
  }
}