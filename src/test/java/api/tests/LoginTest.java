package api.tests;

import api.base.BaseTest;
import api.models.LoginRequest;
import api.api.AuthAPI;
import api.utils.DataProviderUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class LoginTest extends BaseTest {

    public static String token;

    @Test(description = "Positive Case: Login with valid credentials", dataProvider = "data", dataProviderClass = DataProviderUtils.class)
    public void testValidLogin(Map<String, String> data) {
        Response response = AuthAPI.login(data.get("username"),data.get("password"));

        response.then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test(description = "Negative Case: Login with invalid password", dataProvider = "data", dataProviderClass = DataProviderUtils.class)
    public void testInvalidLogin(Map<String, String> data) {
        Response response = AuthAPI.login(data.get("username"), "wrongpassword");
        response.then()
                .statusCode(401)
                .body("message", equalTo("Invalid username or password"));
    }
}