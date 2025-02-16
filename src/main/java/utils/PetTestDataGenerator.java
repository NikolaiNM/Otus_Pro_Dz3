package utils;

import dto.PetDTO;

import java.util.List;

public class PetTestDataGenerator {

  public static PetDTO createPet(Long id, String name, String status) {
    return PetDTO.builder()
        .id(id)
        .name(name)
        .status(status)
        .category(PetDTO.CategoryDTO.builder().id(1L).name("Dogs").build())
        .photoUrls(List.of("url1", "url2"))
        .tags(List.of(PetDTO.TagDTO.builder().id(1L).name("tag1").build()))
        .build();
  }
}