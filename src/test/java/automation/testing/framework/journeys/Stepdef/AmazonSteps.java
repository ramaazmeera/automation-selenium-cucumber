package automation.testing.framework.journeys.Stepdef;

import automation.testing.framework.journeys.Pages.AmazonPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

import java.io.IOException;

public class AmazonSteps {

    private final AmazonPage amazonPage;

    public AmazonSteps(AmazonPage amazonPage){
        this.amazonPage = amazonPage;
    }

    @Given("I have connected to sns and sqs")
    public void iHaveConnectedToSnsAndSqs() {
        amazonPage.getConnection();
    }

    @When("I publish the message in sns topic")
    public void iPublishTheMessageInSnsTopic() {
        amazonPage.publishMessage();
    }

    @Then("I should see message transferred to sqs")
    public void iShouldSeeMessageTransferredToSqs()throws IOException {
        amazonPage.assertMessageQueue();
    }

    @And("verify message body contains below content")
    public void verifyMessageBodyContainsBelowContent(DataTable table)throws IOException {
        amazonPage.assertMessageContent(table);
    }
}
