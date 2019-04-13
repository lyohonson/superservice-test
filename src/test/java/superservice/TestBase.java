package superservice;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import superservice.model.User;
import superservice.steps.BaseSteps;
import superservice.steps.LoginSteps;
import superservice.steps.SaveDataSteps;

class TestBase {


  private LoginSteps loginSteps;
  private BaseSteps baseSteps;
  private SaveDataSteps saveDataSteps;
//  private Superservice superservice;

  protected User getSuperTestUser(){
    return new User("supertest", "superpassword");
  }

  @BeforeSuite
  public void initialize() {
//    superservice = new Superservice();
//    Superservice.getThreadSuperservice().set(superservice);
    loginSteps().ping();
  }

  protected RequestSpecification rest(){
    return baseSteps().rest();
  }

  protected RequestSpecification rest(String token){
    return baseSteps().rest(token);
  }


  protected SaveDataSteps saveDataSteps(){
    if(saveDataSteps == null){
      saveDataSteps = new SaveDataSteps();
    }
    return saveDataSteps;
  }
  protected BaseSteps baseSteps(){
    if(baseSteps == null){
      baseSteps = new BaseSteps();
    }
    return baseSteps;
  }
  protected LoginSteps loginSteps(){
    if(loginSteps == null){
      loginSteps = new LoginSteps();
    }
    return loginSteps;
  }

}
