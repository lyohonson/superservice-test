package superservice;

import static io.restassured.http.ContentType.JSON;
import static org.awaitility.Awaitility.await;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpStatus;
import org.awaitility.core.ConditionTimeoutException;
import org.testng.annotations.Test;
import superservice.model.AuthorizeResponse;
import superservice.model.SaveDataRequest;

@Feature("Authorization")
public class TokenLiveTest extends TestBase {
  private static ValuesGenerator vGen = ValuesGenerator.getInstance();

  @Description("Authorize twice and save data with token from 1st authorization")
  @Test
  public void failIfSaveDataWithOldToken() {
    AuthorizeResponse response = loginSteps().login(getSuperTestUser());
    loginSteps().login(getSuperTestUser());
    saveDataSteps().saveDataCheckStatusCode(new SaveDataRequest(vGen.getRandomString(10)), JSON,
            response.getToken(), HttpStatus.SC_FORBIDDEN);
  }

  @Description("Check token was invalid after 60 seconds")
  @Test
  public void failIfSaveDataSinceMinuteAfterLogin() {
    AuthorizeResponse response = loginSteps().login(getSuperTestUser());
    try {
      await().atLeast(59, TimeUnit.SECONDS)
          .and().atMost(61, TimeUnit.SECONDS)
          .pollInterval(1, TimeUnit.SECONDS)
          .untilAsserted(
              () ->
                rest(response.getToken())
                    .contentType(JSON)
                    .body(new SaveDataRequest(vGen.getRandomJson()))
                    .when().log().all()
                    .post(EndPoints.SAVE_DATA)
                    .then().log().all()
                    .assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
          );
    }catch (ConditionTimeoutException e) {
      if(e.getMessage().contains("which is earlier than expected minimum timeout")) {
        throw new AssertionError("Token expired earlier then 60 seconds");
      } else {
        throw new AssertionError("Token was valid after 60 seconds");
      }
    }

  }
}
