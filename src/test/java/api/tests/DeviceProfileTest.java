package api.tests;

import api.base.BaseTest;
import api.models.DeviceProfileRequest;
import api.models.LoginRequest;
import api.api.AuthAPI;
import api.api.DeviceProfileAPI;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class DeviceProfileTest extends BaseTest {

    private String validToken;
    public static String deviceProfileId;

    @BeforeClass()
    public void setupAuth() {
        Response response = AuthAPI.login("loandtt1@yopmail.com", "Admin@123");
        validToken = response.jsonPath().getString("token");
    }

    @Test(description = "Positive Case: Create a new device profile")
    public void testCreateDeviceProfileSuccess() {
        String uniqueName = "Device_Profile_" + System.currentTimeMillis();

        Response response = DeviceProfileAPI.createDeviceProfile(uniqueName, validToken);

        response.then()
                .statusCode(200)
                .body("name", equalTo(uniqueName))
                .body("id.id", notNullValue());

        deviceProfileId = response.jsonPath().getString("id.id");
        System.out.println("Created Device Profile ID: " + response);
    }

    @Test(description = "Negative Case: Create profile with existing name")
    public void testCreateDeviceProfileUnauthorized() {
        Response response = DeviceProfileAPI.createDeviceProfile("Test", validToken);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Device profile with such name already exists!"));
        System.out.println("Response: " + response.asPrettyString());

    }
}