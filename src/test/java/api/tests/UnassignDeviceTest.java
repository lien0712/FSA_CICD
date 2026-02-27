package api.tests;

import api.api.AuthAPI;
import api.base.BaseTest;
import api.models.CustomerRequest;
import api.models.DeviceRequest;
import api.api.AssignmentCustomerAPI;
import api.api.CustomerAPI;
import api.api.DeviceAPI;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class UnassignDeviceTest extends BaseTest {

    private String validToken;
    private String assignedDeviceId;
    String targetCustomerId;

    @BeforeClass
    public void setupPrerequisites() {
        Response response = AuthAPI.login("loandtt1@yopmail.com", "Admin@123");
        validToken = response.jsonPath().getString("token");
        DeviceRequest devicePayload = new DeviceRequest("Unassign_Test_Device_" + System.currentTimeMillis(), "Label");
        assignedDeviceId = DeviceAPI.createDevice(devicePayload, validToken).jsonPath().getString("id.id");

        CustomerRequest customerPayload = new CustomerRequest("Unassign_Test_Customer_" + System.currentTimeMillis());
        String targetCustomerId = CustomerAPI.createCustomer(customerPayload, validToken).jsonPath().getString("id.id");

        AssignmentCustomerAPI.assignDeviceToCustomer(targetCustomerId, assignedDeviceId, validToken);
    }

    @Test(description = "Positive Case: Unassign a device successfully")
    public void testUnassignDeviceSuccess() {
        Response response = AssignmentCustomerAPI.unassignDeviceFromCustomer( assignedDeviceId, validToken);

        response.then()
                .statusCode(200)
                .body("id.id", equalTo(assignedDeviceId))
                .body("customerId.id", notNullValue());
        System.out.println(response.asPrettyString());
        System.out.println("Successfully unassigned Device " + assignedDeviceId);
    }

    @Test(description = "Negative Case: Try to unassign a fake device ID")
    public void testUnassignInvalidDevice() {
        String fakeDeviceId = UUID.randomUUID().toString();

        Response response = AssignmentCustomerAPI.unassignDeviceFromCustomer(fakeDeviceId, validToken);

        response.then()
                .statusCode(anyOf(is(404), is(400)))
                .body("message", notNullValue());
    }
}