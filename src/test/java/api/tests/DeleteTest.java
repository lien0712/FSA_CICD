package api.tests;

import api.api.AuthAPI;
import api.base.BaseTest;
import api.models.CustomerRequest;
import api.models.DeviceProfileRequest;
import api.models.DeviceRequest;
import api.api.CustomerAPI;
import api.api.DeviceProfileAPI;
import api.api.DeviceAPI;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class DeleteTest extends BaseTest {

    private String validToken;
    private String deviceIdToDelete;
    private String customerIdToDelete;
    private String profileIdToDelete;

    @BeforeClass
    public void setupPrerequisites () {
        Response response = AuthAPI.login("loandtt1@yopmail.com", "Admin@123");
        System.out.println(response.asPrettyString());
        validToken = response.jsonPath().getString("token");

        // 2. Create items specifically to test deleting them
        DeviceRequest device = new DeviceRequest("Delete_Test_Device_" + System.currentTimeMillis(), "Label");
        deviceIdToDelete = DeviceAPI.createDevice(device, validToken).jsonPath().getString("id.id");

        CustomerRequest customer = new CustomerRequest("Delete_Test_Customer_" + System.currentTimeMillis());
        customerIdToDelete = CustomerAPI.createCustomer(customer, validToken).jsonPath().getString("id.id");

        String profile = "Delete_Test_Profile_" + System.currentTimeMillis();
        profileIdToDelete = DeviceProfileAPI.createDeviceProfile(profile, validToken).jsonPath().getString("id.id");
    }

//    @Test(description = "Positive Case: Delete all created items successfully")
    public void testDeleteItemsSuccess() {
        // 1. Delete Device
        Response deviceRes = DeviceAPI.deleteDevice(deviceIdToDelete, validToken);
        deviceRes.then().statusCode(200);

        // 2. Delete Customer
        Response customerRes = CustomerAPI.deleteCustomer(customerIdToDelete, validToken);
        customerRes.then().statusCode(200);

        // 3. Delete Device Profile
        Response profileRes = DeviceProfileAPI.deleteDeviceProfile(profileIdToDelete, validToken);
        profileRes.then().statusCode(200);

        System.out.println("Successfully deleted Device, Customer, and Device Profile.");
    }

    @Test(description = "Negative Case: Try to delete using a fake ID")
    public void testDeleteInvalidId() {
        // 1. Create a fake UUID
        String fakeId = UUID.randomUUID().toString();

        // 2. Send Request to delete a non-existent device
        Response response = DeviceAPI.deleteDevice(fakeId, validToken);

        // 3. Assertions (Expect 404 Not Found or 400 Bad Request)
        response.then()
                .statusCode(anyOf(is(404), is(400)))
                .body("message", notNullValue()); // Verify it returns an error message
//                .time(lessThan(30000L));
    }
}