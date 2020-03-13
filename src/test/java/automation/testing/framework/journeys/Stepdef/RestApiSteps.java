package automation.testing.framework.journeys.Stepdef;

import automation.testing.framework.utils.UtilsRestApi;
import org.jetbrains.annotations.NotNull;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.internal.util.IOUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.CoreMatchers.*;

import io.cucumber.datatable.DataTable;

public class RestApiSteps {
    private UtilsRestApi utilsRestApi = new UtilsRestApi();
    private Response response;
    private RequestSpecification request;
    private String url = "http://localhost:8080/cucumber/rest/assured";

    /**
     * Converts a datatable of headers in the feature file to a List of Lists
     * This implementation takes the values in the first row to be used as headers
     *
     * @param headers Datatable of headers from the feature file auto-converted to List<List<String>>
     */
    @Given("the following list of strings")
    public void iHaveTheFollowingHeaders(List<List<String>> headers) {
        for (List<String> columns : headers) {
            request = given().log().all().headers(columns.get(0), columns.get(1));
        }
    }
    /**
     * Converts a datatable of headers in the feature file to a MAP
     * This implementation requires a title row to be present
     *
     * @param headers Datatable of headers from the feature file auto-converted to List<Map<String, String>>
     */
    @Given("the following headers")
    public void iHaveTheFollowingHeader(List<Map<String, String>> headers) {
        for (Map<String, String> columns : headers) {
            request = given().log().all().headers(columns.get("Header"), columns.get("Value"));
        }
    }

    /**
     * Takes a JSON file to create a request
     *
     * @param jsonFile is a JSON file contained in the specified directory
     */
    @Given("a JSON body {string}")
    public void i_have_a_JSON_body(String jsonFile) throws IOException {
        String filePath = "src/test/resources/PostData/";
        String jsonData = filePath + jsonFile;
        request = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(utilsRestApi.readFile(jsonData));

    }
    /**
     * Takes a set of Key/Values to form a JSON body for the request
     *
     * @param jsonTable is a set of key vale pairs defined in a Cucumber datatable
     */
    @Given("a JSON body with the following:")
    public void a_JSON_body_with_the_following(DataTable jsonTable) {
        List<List<String>> rows = jsonTable.asLists(String.class);
        JSONObject requestBody = new JSONObject();
        for (List<String> columns : rows) {
            requestBody.put(columns.get(0), columns.get(1));
        }
        request = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());
    }
    /**
     * Makes the HTTP POST call to the URL specified
     *
     * @return response from HTTP call
     */
    @When("a POST request is made")
    public Response i_POST_my_request() {
        return response = request.when().post(url);
    }

    /**
     * Makes the HTTP GET call to the URL specified
     *
     * @return response from HTTP call
     */
    @When("a GET request is made")
    public Response aGETRequestIsMade() {
        return response = request.when().get(url);
    }

    /**
     * Verify that the status code from the HTTP call matches the required value
     *
     * @param statusCode Status code to validate against
     */
    @Then("status code {int} is returned")
    public void statusCodeIsReturned(int statusCode) {
        response.then().statusCode(statusCode);
    }

    /**
     * Verify that the response returned is not empty
     */
    @And("^a response body should be returned$")
    public void i_should_have_a_response_body() {
        response.then().assertThat().body(is(notNullValue()));
        response.then().assertThat().body(is(not("")));
    }

    /**
     * Verifies that the JSON in the response matches the JSON in the file specified
     *
     * @param jsonFilename Filename of the expected JSON response
     * @throws Exception Test could throw exceptions; just throw them back.
     */
    @Then("^the response should match the json (.+)$")
    public void the_response_should_match_the_json(String jsonFilename) throws Exception {
        String user_dir = "src/test/resources/wiremock/__files/responses/";
        InputStream is = new FileInputStream(user_dir + jsonFilename);
        String expectedResponse = new String(IOUtils.toByteArray(is));
        String actualResponse = response.body().asString();
        assertThatJson(actualResponse).isEqualTo(expectedResponse);
    }
    /**
     * Takes a named parameter key and a value for that parameter
     *
     * @param parameterKey name of the parameter to be passed in the request
     * @param parameterVal value of the parameter to be passed in the request
     */
    @Given("a parameter {string} with a value {string}")
    public void aParameterNameWithAValueRama(String parameterKey, String parameterVal) {
        request = given().param(parameterKey, parameterVal);
    }

    /**
     * Verify that the status code from the HTTP call matches either of the two specified values
     *
     * @param status1 Status code to validate against
     * @param status2 Status code to validate against
     */
    @Then("I should get status code {int} or a status code of {int}")
    public void i_should_get_status_code_or_a_status_code_of(Integer status1, Integer status2) {
        response.then().statusCode(anyOf(is(status1),is(status2)));
    }

    /**
     * Verify that a response body is returned from the HTTP call and that that body is not null
     */
    @Then("I should receive a response body")
    public void i_should_receive_a_positive_response() {
        response.then().assertThat().body(is(notNullValue()));
        response.then().assertThat().body(is(not("")));
    }

    /**
     * Verify that an empty response body is returned from the HTTP call
     */
    @Then("I should receive an empty response body")
    public void i_should_receive_an_empty_response_body() {
        response.then().assertThat().body(is(""));
    }

    /**
     * Verify that the values returned in the JSON body are as expected on a field per step basis
     *
     * @param field name of JSON field to validate against
     * @param value value of JSON field to validate against
     */
    @Then("the field {string} should equal value {string}")
    public void field_should_equal_value(String field, String value) {
        JsonPath jsonPath = new JsonPath(response.body().asInputStream()).setRootPath("");
        String actualValue = jsonPath.getString(field);
        Assert.assertEquals(value,actualValue);
    }
    /**
     * Verify that the values returned in the JSON body are as expected on a field per step basis
     *
     * @param jsonTable name of datatable containing fields and values against which to validate against
     */
    @Then("the following values should be returned")
    public void i_should_have_the_following_values(@NotNull DataTable jsonTable) {
        JsonPath jsonPath = new JsonPath(response.body().asInputStream()).setRootPath("");
        List<Map<String, String>> list = jsonTable.asMaps(String.class, String.class);
        for (Map<String, String> stringStringMap : list) {
            String field = stringStringMap.get("field");
            String actualValue = jsonPath.getString(field);
            String value = stringStringMap.get("value");
            Assert.assertEquals(value, actualValue);
        }
    }

}
