import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PetStorePetApi;
import dto.PetDTO;
import utils.Specs;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class PetTests {

  private PetStorePetApi petStoreApi;
  private PetDTO pet;

  @BeforeEach
  public void setUp() {
    petStoreApi = new PetStorePetApi();
    pet = PetDTO.builder()
        .id(12345L)
        .name("Buddy")
        .status("available")
        .category(PetDTO.CategoryDTO.builder().id(1L).name("Dogs").build())
        .photoUrls(List.of("url1", "url2"))
        .tags(List.of(PetDTO.TagDTO.builder().id(1L).name("tag1").build()))
        .build();
  }

  @Test
  public void createPetTest() {
    // Проверяем, что питомец успешно создается и возвращается корректный ответ
    petStoreApi.createPet(pet)
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body(matchesJsonSchemaInClasspath("Schema/CreatePet.json"))
        .body("id", equalTo(12345))
        .body("name", equalTo("Buddy"))
        .body("status", equalTo("available"));
  }

  @Test
  public void findPetByIdTest() {
    // Проверяем, что питомец успешно находится по ID
    petStoreApi.createPet(pet);
    petStoreApi.findPetById(pet.getId())
        .spec(Specs.responseSpec(HttpStatus.SC_OK))
        .body("id", equalTo(12345))
        .body("name", equalTo("Buddy"))
        .body("status", equalTo("available"));
  }

  @AfterEach
  public void tearDown() {
    // Удаляем питомца после каждого теста
    petStoreApi.deletePet(pet.getId());
  }
}