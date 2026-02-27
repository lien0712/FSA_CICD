package api.api;

import api.models.CustomerRequest;
import api.specs.SpecFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CustomerAPI {

    public static Response createCustomer(CustomerRequest payload, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .body(payload)
                .when()
                .post("/api/customer");
    }

    public static Response deleteCustomer(String customerId, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .pathParam("customerId", customerId)
                .when()
                .delete("/api/customer/{customerId}");
    }
}