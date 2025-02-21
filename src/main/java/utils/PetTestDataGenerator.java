package utils;

import dto.PetDTO;
import java.util.List;

public class PetTestDataGenerator {

  public static PetDTO createPet(Long id, String name, String status, List<String> photoUrls, List<PetDTO.TagDTO> tags, PetDTO.CategoryDTO category) {
    return PetDTO.builder()
        .id(id)
        .name(name)
        .status(status)
        .category(category)
        .photoUrls(photoUrls)
        .tags(tags)
        .build();
  }
}