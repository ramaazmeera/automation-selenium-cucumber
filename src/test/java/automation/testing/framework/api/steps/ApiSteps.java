package automation.testing.framework.api.steps;

import automation.testing.framework.api.requestbuilder.ExampleRequest;
import automation.testing.framework.api.util.TestExtention;
import automation.testing.framework.database.DbConnection;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiSteps {

    private ExampleRequest exampleRequest;
    private TestExtention testExtention;
    private DbConnection databaseSteps;
    private String requestBody;
    private Response response;
    private String refId;
    private JsonPath jsonPathEvaluator;
    private boolean emailTotp;
    private boolean mobileTotp;

    public ApiSteps(
            ExampleRequest exampleRequest,
            TestExtention testExtention,
            DbConnection databaseSteps) {
      this.exampleRequest = exampleRequest;
      this.testExtention = testExtention;
      this.databaseSteps = databaseSteps;

    }

    @When("a JSON body created with (.*), (.*), (.*), (.*),(.*)")
    public void aJSONBodyCreatedWith(
            String emailAddress, String name, String dob, String mobile, String language) {
        exampleRequest.setCreateAccountRequest(emailAddress, name, dob, mobile, language);
        requestBody = exampleRequest.getCreateAccountRequest().toString();
    }

    @And("a POST request is submitted to account manager with (.*) service endpoint")
    public void aPOSTRequestIsSubmittedToAccountManagerWithServiceEndpoint(String endPoint) {
        response = testExtention.PostOpsWithBody(endPoint, requestBody);
        response.getStatusCode();
        System.out.println(response);
    }

    @Then("(.*) is returned")
    public void returned(String statusCode) {
        int code = Integer.parseInt(statusCode);
        response.then().statusCode(code);
    }

    @And("reponse body should return ref number")
    public void reponseBodyShouldHaveRefNumber() {
        jsonPathEvaluator = response.jsonPath();
        refId = jsonPathEvaluator.get("ref").toString();
    }

//    this exmaple to upload file

//    public void uploadAFile(String file) {
//        String filePath = env.valueOf(file);
//        String fullFilePath = System.getProperty("user.dir") + filePath;
//        uploadFile(uploadFilePageObject.getChooseFile(), fullFilePath);
//    }
}
