package api.tests;

import api.api.AuthAPI;
import api.base.BaseTest;
import api.models.CustomerRequest;
import api.models.DeviceRequest;
import api.models.LoginRequest;
import api.api.AssignmentCustomerAPI;
import api.api.CustomerAPI;
import api.api.DeviceAPI;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class AssignCustomerTest extends BaseTest {

    private String validToken;
    public static String assignedDeviceId;
    public static String targetCustomerId;

    @BeforeMethod
    public void setupPrerequisites() {
        Response response = AuthAPI.login("loandtt1@yopmail.com", "Admin@123");
        validToken = response.jsonPath().getString("token");

        DeviceRequest devicePayload = new DeviceRequest("Assign_Test_Device_" + System.currentTimeMillis(), "Test Label");
        assignedDeviceId = DeviceAPI.createDevice(devicePayload, validToken).jsonPath().getString("id.id");

        CustomerRequest customerPayload = new CustomerRequest("Assign_Test_Customer_" + System.currentTimeMillis());
        targetCustomerId = CustomerAPI.createCustomer(customerPayload, validToken).jsonPath().getString("id.id");
        System.out.println("targetCustomerId: " + targetCustomerId);
    }

    @Test(description = "Positive Case: Assign a device to a customer successfully")
    public void testAssignDeviceSuccess() {
        System.out.println("assignedDeviceId: " + assignedDeviceId);
        Response response = AssignmentCustomerAPI.assignDeviceToCustomer(targetCustomerId, assignedDeviceId, validToken);
        System.out.println(response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("customerId.id", equalTo(targetCustomerId))
                .body("id.id", equalTo(assignedDeviceId));
    }

    @Test(description = "Negative Case: Try to assign to a fake customer ID")
    public void testAssignToInvalidCustomer() {
        String fakeCustomerId = UUID.randomUUID().toString();

        Response response = AssignmentCustomerAPI.assignDeviceToCustomer(fakeCustomerId, assignedDeviceId, validToken);

        response.then()
                .statusCode(anyOf(is(404), is(400)))
                .body("message", notNullValue());
    }
}