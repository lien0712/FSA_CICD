package api.tests;

import api.api.AuthAPI;
import api.base.BaseTest;
import api.models.DeviceRequest;
import api.models.LoginRequest;
import api.api.DeviceAPI;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class DeviceTest extends BaseTest {

    private String validToken;
    public static String deviceId;

    @BeforeClass
    public void setupAuth() {
        Response response = AuthAPI.login("loandtt1@yopmail.com", "Admin@123");
        validToken = response.jsonPath().getString("token");
    }

    @Test(description = "Positive Case: Create a new device successfully")
    public void testCreateDeviceSuccess() {
        String uniqueName = "Auto_Device_" + System.currentTimeMillis();
        DeviceRequest payload = new DeviceRequest(uniqueName, "Automated Test Device");

        Response response = DeviceAPI.createDevice(payload, validToken);

        response.then()
                .statusCode(200)
                .body("name", equalTo(uniqueName))
                .body("id.id", notNullValue());

        deviceId = response.jsonPath().getString("id.id");
    }

    @Test(description = "Negative Case: Try to create a device with a blank name")
    public void testCreateDeviceWithBlankName() {
        DeviceRequest invalidPayload = new DeviceRequest("", "Bad Device");

        Response response = DeviceAPI.createDevice(invalidPayload, validToken);

        response.then()
                .statusCode(400)
                .body("message", containsString("Device name should be specified"));
    }
}