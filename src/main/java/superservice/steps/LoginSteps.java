package superservice.steps;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.apache.http.HttpStatus;
import superservice.EndPoints;
import superservice.model.AuthorizeResponse;
import superservice.model.User;

public class LoginSteps extends BaseSteps {


  @Step
  public void ping() {
    rest().when()
        .get(EndPoints.PING)
        .then()
        .log().all()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);
  }

  @Step
  public void loginAsSuperUserAndSaveCookie() {
    AuthorizeResponse response = login("supertest", "superpassword");
    superTestToken = response.getToken();
    setNewAuthorizationHeader(response.getToken());
  }


  public void setNewAuthorizationHeader(String token) {
    rest().headers(new Headers(new Header("Authorization", "Bearer " + token)));
  }

  @Step
  public AuthorizeResponse login(String username, String password) {
    return rest()
        .param("username", username)
        .param("password", password)
        .when()
        .post(EndPoints.AUTHORIZE)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .and()
        .extract().body().as(AuthorizeResponse.class);
  }

  @Step
  public AuthorizeResponse login(User user) {
    return login(user.getUsername(), user.getPassword());
  }

  @Step
  public void loginCheckStatusCode(User user, int statusCode) {
    rest()
        .param("username", user.getUsername())
        .param("password", user.getPassword())
        .when()
        .post(EndPoints.AUTHORIZE)
        .then()
        .log().all()
        .statusCode(statusCode);
  }
}
