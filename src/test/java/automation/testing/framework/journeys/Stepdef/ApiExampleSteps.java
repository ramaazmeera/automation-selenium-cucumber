package automation.testing.framework.journeys.Stepdef;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class ApiExampleSteps {

    private static HttpClient httpClient;
    private static HttpGet httpGet;
    private static HttpResponse response;
    private static String responseString;
    private static int responseSize;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private static ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @When("a {string} request is submitted to the {string} {string}")
    public void aRequestIsSubmittedToThe(String requestType, String serviceName, String endPoint) throws IOException {

        switch (requestType) {
            case "GET":
                httpGet = new HttpGet(serviceName + endPoint);

                httpClient = HttpClientBuilder.create().build();

                response = httpClient.execute(httpGet);

                responseString = EntityUtils.toString(response.getEntity());
                List responseList = objectMapper.readValue(responseString, List.class);
                responseSize = responseList.size();
                int statusCode = response.getStatusLine().getStatusCode();

                System.out.println(responseString);
                System.out.println(statusCode);
                System.out.println(responseSize);
                break;
            default:
                fail("Method " + requestType + " not currently supported.");
                break;
        }

    }


    @Then("the response code must be {int}")
    public void theResponseCodeMustBe(int responseCode) throws IOException {


        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, is(responseCode));

    }



    @And("the response size must be {int}")
    public void theResponseSizeMustBe(int size) throws IOException {
        responseString = EntityUtils.toString(response.getEntity());
        List responseList = objectMapper.readValue(responseString, List.class);
        System.out.println(responseString);
        responseSize = responseList.size();
        System.out.println(responseSize);

        assertThat(responseSize, is(size));

    }

    @And("Response body contains below")
    public void responseBodyContainsBelow(DataTable table)  {

//        List<String> actual = response.stream().map(MiCaseEvents::getCaseEvent).collect(Collectors.toList());
        //
//        List<String> responselist = new ArrayList<>();
//        for (responselist res : response) {
//            String name = res.getname();
//            responselist.add(name);



        List<String> expceted = table.subTable(1, 0).asList();


//        List<List<String>> tableAsLists = table.cells();
//        // Subtract 1 from List size as this is a header row
//        int expectedSize = tableAsLists.size() - 1;


//        List<List<String>> raw = table.asLists();
//        for (int index = 1, size = raw.size(); index < size; index++) {
//            Customer actual = modelCases[index - 1].getCustomer();
//            assertThat(modelCases[index - 1].getBenefit().name(), is(raw.get(index).get(1)));
//            assertThat(actual.getForenames(), is(raw.get(index).get(0)));
//        }

    }
}
