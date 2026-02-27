package api.tests;

import api.api.AuthAPI;
import api.base.BaseTest;
import api.models.CustomerRequest;
import api.models.LoginRequest;
import api.api.CustomerAPI;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class CustomerTest extends BaseTest {

    private String validToken;
    public static String customerId;

    @BeforeMethod
    public void setupAuth() {
        Response response = AuthAPI.login("loandtt1@yopmail.com", "Admin@123");
        validToken = response.jsonPath().getString("token");
    }

    @Test(description = "Positive Case: Create a new customer successfully")
    public void testCreateCustomerSuccess() {
        String uniqueTitle = "Auto_Customer_" + System.currentTimeMillis();
        CustomerRequest payload = new CustomerRequest(uniqueTitle);

        Response response = CustomerAPI.createCustomer(payload, validToken);

        response.then()
                .statusCode(200)
                .body("title", equalTo(uniqueTitle))
                .body("id.id", notNullValue());

        customerId = response.jsonPath().getString("id.id");
        System.out.println("Created Customer ID: " + customerId);
    }

    @Test(description = "Negative Case: Try to create a customer with a blank title")
    public void testCreateCustomerWithBlankTitle() {
        CustomerRequest invalidPayload = new CustomerRequest("");

        Response response = CustomerAPI.createCustomer(invalidPayload, validToken);

        response.then()
                .statusCode(400)
                .body("message", containsString("Customer title should be specified"));
    }
}