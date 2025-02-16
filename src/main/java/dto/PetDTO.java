package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PetDTO {
  private Long id;
  private CategoryDTO category;
  private String name;
  private List<String> photoUrls;
  private List<TagDTO> tags;
  private String status;

  @Data
  @Builder
  @Accessors(chain = true)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public static class CategoryDTO {
    private Long id;
    private String name;
  }

  @Data
  @Builder
  @Accessors(chain = true)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public static class TagDTO {
    private Long id;
    private String name;
  }
}