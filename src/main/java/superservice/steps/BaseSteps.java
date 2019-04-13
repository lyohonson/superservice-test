package superservice.steps;

import static io.restassured.RestAssured.config;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.HeaderConfig.headerConfig;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

public class BaseSteps {

  @Getter
  @Setter
  public static String superTestToken;

  public RequestSpecification rest() {
    return RestAssured.given()
        .baseUri("http://" + System.getProperty("serviceHost"))
        .port(Integer.parseInt(System.getProperty("servicePort")))
        .config(config().encoderConfig(encoderConfig()
            .defaultCharsetForContentType("UTF-8", ContentType.URLENC))
            .headerConfig(headerConfig()
                .overwriteHeadersWithName("Authorization",
                    "Accept", "Content-Type")))
        .filter(new AllureRestAssured())
        .log().all();
  }

  public RequestSpecification rest(String token) {
    return rest().headers(new Headers(new Header("Authorization", "Bearer " + token)));
  }
}
