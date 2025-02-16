package utils;

import dto.UserDTO;

public class UserTestDataGenerator {

  public static UserDTO createDefaultUser() {
    return UserDTO.builder()
        .username("mytestuser")
        .firstName("Nick")
        .lastName("Mal")
        .email("test@mail.ru")
        .password("password123")
        .phone("79998886655")
        .id("1122335566")
        .userStatus(1L)
        .build();
  }

  public static UserDTO createUpdatedUser() {
    return UserDTO.builder()
        .username("mytestuser")
        .firstName("Donald")
        .lastName("Tramp")
        .email("Trampampam@mail.ru")
        .password("newpassword123")
        .phone("0987654321")
        .id("1122335566")
        .userStatus(2L)
        .build();
  }
}