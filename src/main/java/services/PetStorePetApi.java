package services;
import static io.restassured.RestAssured.given;

import dto.PetDTO;
import io.restassured.response.ValidatableResponse;

public class PetStorePetApi extends BaseApiClient {

  private static final String PET_PATH = "/pet";

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