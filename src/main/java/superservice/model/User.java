package superservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
public class User {
  String username;
  String password;


  public static User create(String username, String password) {
    return User.builder()
        .username(username)
        .password(password)
        .build();
  }
}
