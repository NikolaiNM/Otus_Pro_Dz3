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
    private String mEmail;
    private String mFirstName;
    private String mId;
    private String mLastName;
    private String mPassword;
    private String mPhone;
    private Long mUserStatus;
    private String mUsername;
}
