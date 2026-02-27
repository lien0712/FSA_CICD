package api.api;

import api.specs.SpecFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AssignmentCustomerAPI {

    public static Response assignDeviceToCustomer(String customerId, String deviceId, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .pathParam("customerId", customerId)
                .pathParam("deviceId", deviceId)
                .when()
                .post("/api/customer/{customerId}/device/{deviceId}");
    }

    public static Response unassignDeviceFromCustomer(String deviceId, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .pathParam("deviceId", deviceId)
                .when()
                .delete("/api/customer/device/{deviceId}");
    }
}