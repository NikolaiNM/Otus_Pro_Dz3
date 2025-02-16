import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import dto.CreateUserResponse;
import dto.UserDTO;
import groovyjarjarpicocli.CommandLine;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.PetStoreIpi;

import java.util.stream.Stream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserName {

  private PetStoreIpi petStoreIpi = new PetStoreIpi();

  @Test
  public void createUser() {
    UserDTO userDTO = UserDTO.builder()
        .mLastName("LastName")
        .mPhone("79888")
        .mEmail("email@mail.ru")
        .mUsername("alex")
        .mUserStatus(400l)
        .build();



//    petStoreIpi.createUser(userDTO)
//        .statusCode(HttpStatus.SC_OK)
//        .body(matchesJsonSchemaInClasspath("Schema/CreateUser.json"))
//        .body("code",equalTo(200))
//        .body("type",equalTo("unknown"))
//        .body("message",equalTo("0"));

//    String actualType = petStoreIpi.createUser(userDTO).extract().body().jsonPath().get("type");
//    String actualMessage = petStoreIpi.createUser(userDTO).extract().body().jsonPath().get("message");
//
//    Assertions.assertAll(
//        () -> Assertions.assertEquals("unknown", actualType, "Incorrect type value"),
//        () -> Assertions.assertEquals("0", actualMessage, "Incorrect message value")
//        );

//    CreateUserResponse actualUser = petStoreIpi.createUser(userDTO).extract().body().as(CreateUserResponse.class);
//
//    Assertions.assertAll(
//        () -> Assertions.assertEquals("unknown", actualUser.getType(), "Incorrect type value"),
//        () -> Assertions.assertEquals("0", actualUser.getMessage(), "Incorrect message value")
//    );

    petStoreIpi.deleteUser("Sasha12321")
        .statusCode(HttpStatus.SC_NOT_FOUND);

  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  public void createUserInvalidData(UserDTO userDTO) {
    petStoreIpi.createUser(userDTO)
        .statusCode(HttpStatus.SC_OK);

  }

  private static Stream<Arguments> dataProvider() {

    UserDTO userNoId = UserDTO.builder()
        .mLastName("LastName")
        .mPhone("79888")
        .mEmail("email@mail.ru")
        .mUsername("alex")
        .mUserStatus(400l)
        .build();

    UserDTO userNoType = UserDTO.builder()
        .mLastName("LastName")
        .mPhone("79888")
        .mEmail("email@mail.ru")
        .mUsername("alex")
        .mUserStatus(400l)
        .build();

    return Stream.of(
        Arguments.of(userNoId),
        Arguments.of(userNoType)
    );
  }

}
