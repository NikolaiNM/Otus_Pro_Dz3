import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.PetStorePetApi;
import dto.PetDTO;
import utils.Specs;
import utils.PetTestDataGenerator;
import java.util.stream.Stream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * Тесты проверяют:
 * 1. Создание питомца с различными параметрами.
 * 2. Получение данных питомца по его ID.
 * Для каждого теста выполняется очистка данных (удаление созданного питомца).
 */

public class PetTests {

  private PetStorePetApi petStoreApi;

  @BeforeEach
  public void setUp() {
    petStoreApi = new PetStorePetApi();
  }

  private static Stream<Arguments> providePetData() {
    return Stream.of(
        Arguments.of(12345L, "Buddy", "available"),
        Arguments.of(67890L, "Max", "pending"),
        Arguments.of(11111L, "Charlie", "sold")
    );
  }

  @ParameterizedTest
  @MethodSource("providePetData")
  public void createPetTest(Long id, String name, String status) {
    PetDTO pet = PetTestDataGenerator.createPet(id, name, status);

    petStoreApi.createPet(pet)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body(matchesJsonSchemaInClasspath("Schema/CreatePet.json"))
        .body("id", equalTo(id.intValue()))
        .body("name", equalTo(name))
        .body("status", equalTo(status));

    petStoreApi.deletePet(pet.getId());
  }

  @Test
  public void findPetByIdTest() {
    Long petId = 12345L;
    String petName = "Buddy";
    String petStatus = "available";

    PetDTO pet = PetTestDataGenerator.createPet(petId, petName, petStatus);

    petStoreApi.createPet(pet);

    petStoreApi.findPetById(pet.getId())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("id", equalTo(petId.intValue()))
        .body("name", equalTo(petName))
        .body("status", equalTo(petStatus));

    petStoreApi.deletePet(pet.getId());
  }
}