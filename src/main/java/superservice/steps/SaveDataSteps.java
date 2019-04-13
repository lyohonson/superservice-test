package superservice.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import superservice.EndPoints;
import superservice.model.SaveDataRequest;
import superservice.model.SaveDataResponse;

public class SaveDataSteps extends BaseSteps {


  @Step
  public SaveDataResponse saveData(SaveDataRequest requestBody, ContentType contentType) {
    switch (contentType) {
      case JSON:
        return rest(superTestToken).contentType(contentType)
            .body(requestBody)
            .when()
            .post(EndPoints.SAVE_DATA)
            .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract().as(SaveDataResponse.class);
      case URLENC:
        return rest(superTestToken).contentType(contentType)
            .param("payload", requestBody.getPayload())
            .when()
            .post(EndPoints.SAVE_DATA)
            .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract().as(SaveDataResponse.class);
      default:
        throw new IllegalArgumentException("Wrong contentType " +
            contentType.toString() + ". Expected  application/json or"
            + " application/x-www-form-urlencoded");
    }

  }

  @Step
  public void saveDataCheckStatusCode(SaveDataRequest requestBody, RequestSpecification spec,
      int statusCode) {
    spec.contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .post(EndPoints.SAVE_DATA)
        .then()
        .log().all()
        .assertThat()
        .statusCode(statusCode);
  }

  @Step
  public void saveDataCheckStatusCode(SaveDataRequest requestBody, ContentType contentType,
      int statusCode) {
    saveDataCheckStatusCode(requestBody, contentType, superTestToken, statusCode);
  }

  @Step
  public void saveDataCheckStatusCode(SaveDataRequest requestBody, ContentType contentType,
      String token, int statusCode) {

    switch (contentType) {
      case JSON:
        rest(token).contentType(contentType)
            .body(requestBody)
            .when()
            .post(EndPoints.SAVE_DATA)
            .then()
            .log().all()
            .assertThat()
            .statusCode(statusCode);
        break;

      case URLENC:
        rest(token).contentType(contentType)
            .param("payload", requestBody.getPayload())
            .when()
            .post(EndPoints.SAVE_DATA)
            .then()
            .log().all()
            .assertThat()
            .statusCode(statusCode);
        break;

      default:
        throw new IllegalArgumentException("Wrong contentType " +
            contentType.toString() + ". Expected  application/json or"
            + " application/x-www-form-urlencoded");

    }

  }


}
