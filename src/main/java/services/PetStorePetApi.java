package services;
import static io.restassured.RestAssured.given;

import dto.PetDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.Specs;

public class PetStorePetApi {

  private final RequestSpecification spec;


  private static final String PET_PATH = "/pet";
  private static final String BASE_URL = System.getProperty("base.url");


  public PetStorePetApi() {
    spec = given()
        .spec(Specs.requestSpec())
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  public ValidatableResponse createPet(PetDTO pet) {
    return given(spec)
        .body(pet)
        .basePath(PET_PATH)
        .when()
        .post()
        .then()
        .log().all();
  }

  public ValidatableResponse findPetById(long petId) {
    return given(spec)
        .basePath(PET_PATH + "/{petId}")
        .pathParam("petId", petId)
        .when()
        .get()
        .then()
        .log().all();
  }

  public ValidatableResponse deletePet(long petId) {
    return given(spec)
        .basePath(PET_PATH + "/{petId}")
        .pathParam("petId", petId)
        .when()
        .delete()
        .then()
        .log().all();
  }
}