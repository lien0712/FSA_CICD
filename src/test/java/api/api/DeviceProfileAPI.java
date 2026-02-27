package api.api;

import api.models.DeviceProfileRequest;
import api.specs.SpecFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeviceProfileAPI {

    public static Response createDeviceProfile(String name, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .body(new DeviceProfileRequest(name))
                .when()
                .post("/api/deviceProfile");
    }

    public static Response deleteDeviceProfile(String profileId, String token) {
        return RestAssured.given()
                .spec(SpecFactory.getAuthSpec(token))
                .pathParam("profileId", profileId)
                .when()
                .delete("/api/deviceProfile/{profileId}");
    }
}