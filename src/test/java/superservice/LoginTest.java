package superservice;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import superservice.model.AuthorizeResponse;
import superservice.model.User;

@Feature("Authorization")
public class LoginTest extends TestBase{

  @Test
  public void userAuthorize() {
    AuthorizeResponse response = loginSteps().login(getSuperTestUser());
    assertThat(response.getToken())
        .as("Check token value is not empty")
        .isNotNull().isNotEqualTo("");
//        .matches("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b");
  }

  @DataProvider(parallel = true)
  public Object[][] users() {
    return new Object[][]{
        {new User("", "superpassword")},
        {new User("supertest", "")},
        {new User("supertest", null)},
        {new User(null, "superpassword")},
        {new User("supertest", "1234567")},
        {new User("admin", "superpassword")},
        {new User("", "")},
        {new User(null, null)},
        {new User("SuperTest", "superpassword")},
        {new User("supertest", "SuperPassword")},
        {new User("superpassword", "supertest")},
        {new User("«♣☺♂»!@#$%^&*()±~|\\:;<>-=+§`[]№{}><","superpassword")},
        {new User("supertest","«♣☺♂»!@#$%^&*()±~|\\:;<>-=+§`[]№{}><")},
        {new User("         ", "superpassword")},
        {new User("supertest", "             ")},
        {new User("supertest ", "superpassword")},
        {new User("supertest", "superpassword ")},
        {new User(" supertest", "superpassword")},
        {new User("supertest", " superpassword")},
    };
  }

  @Test(dataProvider = "users")
  public void userCannotAuthorize(User user) {
    loginSteps().loginCheckStatusCode(user, HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  public void loginTwiceCheckTokensAreNotEqual() {
    User user = getSuperTestUser();
    AuthorizeResponse resp1 = loginSteps().login(user.getUsername(),user.getPassword());
    AuthorizeResponse resp2 = loginSteps().login(user.getUsername(),user.getPassword());

    assertThat(resp1.getToken())
        .as("Check tokens are not equal")
        .isNotEqualTo(resp2.getToken());
  }
}
