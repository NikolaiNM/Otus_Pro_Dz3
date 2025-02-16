import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PetStoreUserApi;
import dto.UserDTO;
import utils.Specs;
import utils.UserTestDataGenerator;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * Тесты проверяют:
 * 1. Пользователь успешно создается и возвращается корректный ответ.
 * 2. Созданного пользователя можно найти по его username и возвращаются корректные данные.
 * 3. Данные пользователя успешно изменяются и возвращается корректный ответ.
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
  public void createUserTest() {
    petStoreApi.createUser(user)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body(matchesJsonSchemaInClasspath("Schema/CreateUser.json"))
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo("1122335566"));
  }

  @Test
  public void findUserByUsernameTest() {
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