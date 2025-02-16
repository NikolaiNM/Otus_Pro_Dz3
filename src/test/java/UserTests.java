import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PetStoreUserApi;
import dto.UserDTO;
import utils.Specs;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class UserTests {

  private PetStoreUserApi petStoreApi;
  private UserDTO user;

  @BeforeEach
  public void setUp() {
    petStoreApi = new PetStoreUserApi();
    user = UserDTO.builder()
        .username("testuser")
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .password("password123")
        .phone("1234567890")
        .userStatus(1L)
        .build();
  }

  @Test
  public void createUserTest() {
    petStoreApi.createUser(user)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body(matchesJsonSchemaInClasspath("Schema/CreateUser.json")) // Убедитесь, что путь правильный
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"));
        //.body("message", equalTo("9223372036854755594")); // Уточните ожидаемое значение
  }

  @Test
  public void findUserByUsernameTest() {
    // Проверяем, что пользователь успешно находится по username
    petStoreApi.createUser(user);
    petStoreApi.findUserByUsername(user.getUsername())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("username", equalTo("testuser"))
        .body("firstName", equalTo("John"))
        .body("lastName", equalTo("Doe"));
  }

  @AfterEach
  public void tearDown() {
    // Удаляем пользователя после каждого теста
    petStoreApi.deleteUser(user.getUsername());
  }
}