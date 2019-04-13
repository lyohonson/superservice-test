package superservice;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;
import static org.assertj.core.api.Assertions.assertThat;
import static superservice.assertions.SaveDataAssert.assertThat;

import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import superservice.steps.Verifications;
import superservice.model.SaveDataRequest;
import superservice.model.SaveDataResponse;


@Feature("SaveData")
public class SaveDataTest extends TestBase {

  private static ValuesGenerator vGen = ValuesGenerator.getInstance();

  @BeforeClass
  public void login() {
    loginSteps().loginAsSuperUserAndSaveCookie();
  }


  @DataProvider(parallel = true)
  public Object[][] data() {
    return new Object[][]{
        {new SaveDataRequest(vGen.getCyrillicRandomString()), URLENC},
        {new SaveDataRequest(vGen.getCyrillicRandomString()), JSON},
        {new SaveDataRequest(vGen.getRandomString(100000)), URLENC},
        {new SaveDataRequest(vGen.getRandomString(100000)), JSON},
        {new SaveDataRequest(vGen.getRandomJson()), URLENC},
        {new SaveDataRequest(vGen.getRandomJson()), JSON},
        {new SaveDataRequest("w"), JSON},
        {new SaveDataRequest("e"), URLENC},
        {new SaveDataRequest(" "), JSON},
        {new SaveDataRequest(" "), URLENC},
        {new SaveDataRequest("  "), JSON},
        {new SaveDataRequest("  "), URLENC},
        {new SaveDataRequest("\n"), JSON},
        {new SaveDataRequest("\n"), URLENC},
        {new SaveDataRequest("腿"), JSON},
        {new SaveDataRequest("腿"), URLENC},
        {new SaveDataRequest("1234567890"), JSON},
        {new SaveDataRequest("1234567890"), URLENC},
        {new SaveDataRequest("!@#$%^&*()±~|\\:;<>-=+§`[]№{}<>«♣☺♂»"), JSON},
        {new SaveDataRequest("!@#$%^&*()~|\\:;<>-=+`[]{}<>"), URLENC},
        {new SaveDataRequest("±§№«♣☺♂»"), URLENC}

    };
  }


  @Test(dataProvider = "data")
  public void saveData(SaveDataRequest request, ContentType contentType)
      throws SQLException {

    SaveDataResponse response = saveDataSteps().saveData(request, contentType);

    assertThat(response).isSuccessful();

    String expectMd5Hex = DigestUtils.md5Hex(request.getPayload());

    Verifications
        .uploadsRowShouldHave(response.getId(), expectMd5Hex, getSuperTestUser().getUsername());
  }

  @DataProvider(parallel = true)
  public Object[][] provideRequestSpecs() {
    return new Object[][] {
        {rest()},
        {rest().header("Authorization", "Bearer "
            + vGen.getRandomString(10))},
        {rest().header("Authorization", "Bearer e")},
        {rest().header("Authorization", "Bearer ")},
        {rest().header("Authorization", "")}
    };
  }


  @Test(dataProvider = "provideRequestSpecs")
  public void failWhenSaveDataWithInvalidToken(RequestSpecification spec){
    saveDataSteps().saveDataCheckStatusCode(
        new SaveDataRequest(vGen.getRandomString(10)), spec, HttpStatus.SC_FORBIDDEN);
  }



  @DataProvider(parallel = true)
  private Object[][] provideNotValidData(){
    return new Object[][] {
        {new SaveDataRequest(null), JSON},
        {new SaveDataRequest(null), URLENC},
        {new SaveDataRequest(""), JSON},
        {new SaveDataRequest(""), URLENC}
    };
  }
  @Test(dataProvider = "provideNotValidData")
  public void failIfSaveNotValidData(SaveDataRequest saveDataRequest, ContentType contentType) {
    saveDataSteps().saveDataCheckStatusCode(saveDataRequest, contentType, 400);
  }

  @Test
  public void saveInvalidJsonInsteadSaveDataBody() {
    rest(baseSteps().getSuperTestToken()).contentType(JSON).body(vGen.getRandomJson())
        .when()
        .post(EndPoints.SAVE_DATA)
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  public void saveInvalidFields() {
    rest(baseSteps().getSuperTestToken()).contentType(URLENC)
        .param(vGen.getRandomString(5), vGen.nextRandomInt(100))
        .when()
        .post(EndPoints.SAVE_DATA)
        .then()
        .assertThat()
        .statusCode(400);
  }

  @DataProvider(parallel = true)
  private Object[][] provideContentType() {
    return new Object[][] {
        {JSON},
        {URLENC}
    };
  }


  @Test(dataProvider = "provideContentType")
  public void saveSameDataTwice(ContentType contentType) throws SQLException {
    String string = vGen.getRandomString(8);
    SaveDataRequest saveDataRequest = new SaveDataRequest(string);
    SaveDataResponse resp1 = saveDataSteps().saveData(saveDataRequest, contentType);
    assertThat(resp1).isSuccessful();

    SaveDataResponse resp2 = saveDataSteps().saveData(saveDataRequest, contentType);
    assertThat(resp2).isSuccessful();

    assertThat(resp1.getId())
        .as("Check responses have different ids")
        .isNotEqualTo(resp2.getId());

    String expectMd5Hex = DigestUtils.md5Hex(string);
    Verifications.uploadsRowShouldHave(resp1.getId(), expectMd5Hex, getSuperTestUser().getUsername());
    Verifications.uploadsRowShouldHave(resp2.getId(), expectMd5Hex, getSuperTestUser().getUsername());
  }

  @DataProvider(parallel = true)
  private Object[][] provideContentTypes() {
    byte[] decodedPayload = "payload=12345678".getBytes();
    byte[] decodedField = "field=12345678".getBytes();
    return new Object[][] {
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.TEXT)
          .body("payload=1234")},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.TEXT)
          .body("text=1234")},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.BINARY)
          .body(decodedPayload)},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.ANY)
          .body(decodedPayload)},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.BINARY)
          .body(decodedField)},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.ANY)
          .body(decodedField)},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.HTML)
          .body("payload=123456")},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.HTML)
          .body("field=123456")},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.XML)
          .body("payload=1234567890")},
        {rest(baseSteps().getSuperTestToken()).contentType(ContentType.XML)
          .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<root>\n"
            + "   <payload>2134567</payload>\n"
            + "</root>")},
    };
  }

  @Test(dataProvider = "provideContentTypes")
  public void saveWithNotValidContentType(RequestSpecification spec) {
    spec.post(EndPoints.SAVE_DATA)
        .then()
        .log().all()
        .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
  }



}
