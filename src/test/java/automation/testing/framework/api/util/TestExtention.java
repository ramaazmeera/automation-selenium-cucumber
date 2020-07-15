package automation.testing.framework.api.util;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestExtention {

    public RequestSpecification Request;

    public TestExtention() {
        // Arrange
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://localhost:3000/");
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        Request = RestAssured.given().spec(requestSpec);
    }

    public Response PostOpsWithBody(String url, String body) {
        Request.body(body);
        return Request.when().post(url);
    }
}
