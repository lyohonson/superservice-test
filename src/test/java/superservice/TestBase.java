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

  User getSuperTestUser(){
    return new User("supertest", "superpassword");
  }

  @BeforeSuite
  public void initialize() {
    loginSteps().ping();
  }

  RequestSpecification rest(){
    return baseSteps().rest();
  }

  RequestSpecification rest(String token){
    return baseSteps().rest(token);
  }


  SaveDataSteps saveDataSteps(){
    if(saveDataSteps == null){
      saveDataSteps = new SaveDataSteps();
    }
    return saveDataSteps;
  }
  private BaseSteps baseSteps(){
    if(baseSteps == null){
      baseSteps = new BaseSteps();
    }
    return baseSteps;
  }
  LoginSteps loginSteps(){
    if(loginSteps == null){
      loginSteps = new LoginSteps();
    }
    return loginSteps;
  }

}
