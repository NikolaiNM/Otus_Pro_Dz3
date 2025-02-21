import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.PetStorePetApi;
import dto.PetDTO;
import utils.Specs;
import utils.PetTestDataGenerator;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Тесты проверяют:
 * 1. Создание питомца с различными параметрами.
 * 2. Обновление данных питомца через POST (name и status).
 * 3. Возвращение 404 если питомец не найден
 *  Для каждого теста выполняется очистка данных (удаление созданного питомца).
 */
public class PetTests {

  private PetStorePetApi petStoreApi;
  private PetDTO createdPet;

  @BeforeEach
  public void setUp() {
    petStoreApi = new PetStorePetApi();
  }

  @AfterEach
  public void tearDown() {
    if (createdPet != null) {
      petStoreApi.deletePet(createdPet.getId());
    }
  }

  private static Stream<Arguments> providePetData() {
    return Stream.of(
        Arguments.of(
            12345L, "Ralf", "available",
            List.of("url1", "url2"),
            List.of(PetDTO.TagDTO.builder().id(1L).name("tag1").build()),
            PetDTO.CategoryDTO.builder().id(1L).name("Dogs").build()
        ),
        Arguments.of(
            67890L, "Tihan", "pending",
            List.of("url3"),
            List.of(PetDTO.TagDTO.builder().id(2L).name("tag2").build(), PetDTO.TagDTO.builder().id(3L).name("tag3").build()),
            PetDTO.CategoryDTO.builder().id(2L).name("Cats").build()
        ),
        Arguments.of(
            11111L, "Kesha", "sold",
            Collections.emptyList(),
            Collections.emptyList(),
            PetDTO.CategoryDTO.builder().id(3L).name("Birds").build()
        )
    );
  }

  @ParameterizedTest
  @MethodSource("providePetData")
  public void createPetTest(Long id, String name, String status, List<String> photoUrls, List<PetDTO.TagDTO> tags, PetDTO.CategoryDTO category) {
    PetDTO pet = PetTestDataGenerator.createPet(id, name, status, photoUrls, tags, category);
    createdPet = pet;

    petStoreApi.createPet(pet)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body(matchesJsonSchemaInClasspath("Schema/CreatePet.json"))
        .body("id", equalTo(id.intValue()))
        .body("name", equalTo(name))
        .body("status", equalTo(status))
        .body("photoUrls", equalTo(photoUrls))
        .body("tags", hasSize(tags.size()))
        .body("category.id", equalTo(category.getId().intValue()))
        .body("category.name", equalTo(category.getName()));

    petStoreApi.findPetById(pet.getId())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("id", equalTo(id.intValue()))
        .body("name", equalTo(name))
        .body("status", equalTo(status))
        .body("photoUrls", equalTo(photoUrls))
        .body("tags", hasSize(tags.size()))
        .body("category.id", equalTo(category.getId().intValue()))
        .body("category.name", equalTo(category.getName()));
  }

  @Test
  public void updatePetWithFormDataTest() {
    Long petId = 12345L;
    String petName = "Chip";
    String petStatus = "available";
    List<String> photoUrls = List.of("url1", "url2");
    List<PetDTO.TagDTO> tags = List.of(PetDTO.TagDTO.builder().id(1L).name("tag1").build());
    PetDTO.CategoryDTO category = PetDTO.CategoryDTO.builder().id(1L).name("Dogs").build();

    PetDTO pet = PetTestDataGenerator.createPet(petId, petName, petStatus, photoUrls, tags, category);
    createdPet = pet;
    petStoreApi.createPet(pet);

    petStoreApi.findPetById(pet.getId())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("id", equalTo(petId.intValue()))
        .body("name", equalTo(petName))
        .body("status", equalTo(petStatus))
        .body("photoUrls", equalTo(photoUrls))
        .body("tags", hasSize(tags.size()))
        .body("category.id", equalTo(category.getId().intValue()))
        .body("category.name", equalTo(category.getName()));

    String updatedName = "Dale";
    String updatedStatus = "pending";

    petStoreApi.updatePetWithFormData(pet.getId(), updatedName, updatedStatus)
        .spec(Specs.responseSpec(HttpStatus.SC_OK));

    petStoreApi.findPetById(pet.getId())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("id", equalTo(petId.intValue()))
        .body("name", equalTo(updatedName))
        .body("status", equalTo(updatedStatus))
        .body("photoUrls", equalTo(photoUrls))
        .body("tags", hasSize(tags.size()))
        .body("category.id", equalTo(category.getId().intValue()))
        .body("category.name", equalTo(category.getName()));
  }

  @Test
  public void findNonExistentPetByIdTest() {
    long nonExistentPetId = 9999999933339L;

    petStoreApi.findPetById(nonExistentPetId)
        .spec(Specs.responseSpec(HttpStatus.SC_NOT_FOUND));
  }
}