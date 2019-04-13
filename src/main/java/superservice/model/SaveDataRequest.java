package superservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@JsonInclude
@Data
@Builder
@AllArgsConstructor
public class SaveDataRequest {
  String payload;
}
