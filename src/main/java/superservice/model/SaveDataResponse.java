package superservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude
public class SaveDataResponse {
  String status;
  String error;
  Integer id;

  public static SaveDataResponse create(String status, String error) {
    return builder()
        .status(status)
        .error(error)
        .build();
  }
}
