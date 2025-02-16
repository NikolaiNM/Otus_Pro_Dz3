import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

  @Test
  public void testGetUser() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
        .when()
        .get("/users/1")
        .then()
        .statusCode(200)
        .body("name", equalTo("Leanne Graham"));
  }
}