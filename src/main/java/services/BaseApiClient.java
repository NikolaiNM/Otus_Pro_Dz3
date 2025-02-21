package services;
import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.Specs;

public abstract class BaseApiClient {

  protected final RequestSpecification spec;

  protected BaseApiClient() {
    spec = given()
        .spec(Specs.requestSpec())
        .baseUri(getBaseUrl())
        .contentType(ContentType.JSON)
        .log().all();
  }

  protected String getBaseUrl() {
    return System.getProperty("base.url");
  }
}