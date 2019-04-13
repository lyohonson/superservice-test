package superservice.model;

import lombok.Getter;

public enum SaveDataStatus {
  OK("OK"),
  ERROR("ERROR");

  @Getter
  private final String value;

  SaveDataStatus(String value) {
    this.value = value;
  }
}
