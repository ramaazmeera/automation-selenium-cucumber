package automation.testing.framework.api.requestbuilder;

import org.json.JSONObject;
import org.json.simple.JSONArray;

public class ExampleRequest {

  private JSONObject createAccountRequest;

  private JSONObject accountVerifyConfirmRequest;

  private JSONObject accountCreatePasswordRequest;

  private JSONObject testRequest;

  private JSONArray testTest;

  public JSONObject getCreateAccountRequest() {
    return createAccountRequest;
  }

  public void setCreateAccountRequest(
      String emailAddress, String name, String dob, String mobile, String language) {
    createAccountRequest = new JSONObject();
    createAccountRequest.put("email", emailAddress);
    createAccountRequest.put("dob", dob);
    createAccountRequest.put("mobile_phone", mobile);
    createAccountRequest.put("name", name);
    createAccountRequest.put("language", language);
  }

  public JSONObject getAccountVerifyConfirmRequest() {
    return accountVerifyConfirmRequest;
  }

  public void setAccountVerifyConfirmRequest(
      String ref, String code, String source, boolean code1, boolean code2) {
    testRequest = new JSONObject();
    testRequest.put("code", code);
    testRequest.put("source", source);
    accountVerifyConfirmRequest = new JSONObject();
    accountVerifyConfirmRequest.put("ref", ref);
    accountVerifyConfirmRequest.put("test1", testRequest);
    accountVerifyConfirmRequest.put("test2", code1);
    accountVerifyConfirmRequest.put("test3", code2);
  }

  public JSONObject getAccountCreatePasswordRequest() {
    return accountCreatePasswordRequest;
  }

  public void setAccountCreatePasswordRequest(
      String ref, String password, String code, String source) {
    testRequest = new JSONObject();
    testRequest.put("code", code);
    testRequest.put("source", source);
    testTest = new JSONArray();
    testTest.add(testRequest);
    accountCreatePasswordRequest = new JSONObject();
    accountCreatePasswordRequest.put("ref", ref);
    accountCreatePasswordRequest.put("password", password);
    accountCreatePasswordRequest.put("test", testTest);
  }
}
