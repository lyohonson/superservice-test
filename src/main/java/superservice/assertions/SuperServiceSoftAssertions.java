package superservice.assertions;

import org.assertj.core.api.SoftAssertions;
import superservice.model.SaveDataResponse;

public class SuperServiceSoftAssertions extends SoftAssertions {

  public SaveDataAssert assertThat(SaveDataResponse actual) {
    return proxy(SaveDataAssert.class, SaveDataResponse.class, actual);
  }

}
