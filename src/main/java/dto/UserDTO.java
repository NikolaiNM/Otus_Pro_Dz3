package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDTO {
  private String email;
  private String firstName;
  private String id;
  private String lastName;
  private String password;
  private String phone;
  private Long userStatus;
  private String username;
}