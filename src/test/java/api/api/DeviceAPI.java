package api.api;

import api.models.DeviceRequest;
import api.specs.SpecFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeviceAPI {

    public static Response createDevice(DeviceRequest payload, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .body(payload)
                .when()
                .post("/api/device");
    }

    public static Response deleteDevice(String deviceId, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .pathParam("deviceId", deviceId)
                .when()
                .delete("/api/device/{deviceId}");
    }
}