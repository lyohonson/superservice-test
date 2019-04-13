package superservice.assertions;

import io.qameta.allure.Step;
import java.util.Objects;
import org.assertj.core.api.AbstractAssert;
import superservice.model.SaveDataResponse;
import superservice.model.SaveDataStatus;

public class SaveDataAssert extends AbstractAssert<SaveDataAssert, SaveDataResponse> {

  public SaveDataAssert(SaveDataResponse actual) {
    super(actual, SaveDataAssert.class);
  }

  public static SaveDataAssert assertThat(SaveDataResponse actual) {
    return new SaveDataAssert(actual);
  }


  @Step
  public SaveDataAssert hasNotNullId() {
    isNotNull();

    if (Objects.equals(actual.getId(), null)) {
      failWithMessage("Expected id to be not <%s> but was <%s>",
          null, actual.getId());
    }
    return this;
  }

  @Step
  public SaveDataAssert hasError(String error) {
    isNotNull();

    if (!Objects.equals(actual.getError(), error)) {
      failWithMessage("Expected error message to be <%s> but was <%s>",
          error, actual.getError());
    }
    return this;
  }

  @Step
  public SaveDataAssert hasStatus(String status) {
    isNotNull();

    if (!Objects.equals(actual.getStatus(), status)) {
      failWithMessage("Expected status to be <%s> but was <%s>",
          status, actual.getStatus(), actual.getError());
    }
    return this;
  }


  @Step
  public SaveDataAssert isSuccessful() {
    isNotNull();
    SuperServiceSoftAssertions soft = new SuperServiceSoftAssertions();
    soft.assertThat(actual).hasStatus(SaveDataStatus.OK.getValue());
    soft.assertThat(actual).hasError(null);
    soft.assertThat(actual).hasNotNullId();
    soft.assertAll();
    return this;
  }
}
