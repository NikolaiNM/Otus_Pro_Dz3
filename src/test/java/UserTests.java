import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PetStoreUserApi;
import dto.UserDTO;
import utils.Specs;
import utils.UserTestDataGenerator;

/**
 * Тесты проверяют:
 * 1. Создание пользователя и получение его данных по username.
 * 2. Обновление данных пользователя и проверка изменений.
 * Для каждого теста выполняется очистка данных (удаление созданного пользователя).
 */
public class UserTests {

  private PetStoreUserApi petStoreApi;
  private UserDTO user;

  @BeforeEach
  public void setUp() {
    petStoreApi = new PetStoreUserApi();
    user = UserTestDataGenerator.createDefaultUser();
    petStoreApi.createUser(user);
  }

  @AfterEach
  public void tearDown() {
    petStoreApi.deleteUser(user.getUsername());
  }

  @Test
  public void createAndFindUserTest() {
    UserDTO user = UserTestDataGenerator.createDefaultUser();

    petStoreApi.createUser(user)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body(matchesJsonSchemaInClasspath("Schema/CreateUser.json"))
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("1122335566"));

    petStoreApi.findUserByUsername(user.getUsername())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("username", equalTo("mytestuser"))
        .body("firstName", equalTo("Nick"))
        .body("lastName", equalTo("Mal"));
  }

  @Test
  public void updateUserTest() {
    UserDTO updatedUser = UserTestDataGenerator.createUpdatedUser();

    petStoreApi.updateUser(updatedUser)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("1122335566"));

    petStoreApi.findUserByUsername(updatedUser.getUsername())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("firstName", equalTo("Donald"))
        .body("lastName", equalTo("Tramp"))
        .body("email", equalTo("Trampampam@mail.ru"))
        .body("password", equalTo("newpassword123"))
        .body("phone", equalTo("0987654321"))
        .body("userStatus", equalTo(2));
  }
}