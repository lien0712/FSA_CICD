package api.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected String token;

    @BeforeClass()
    public void setup() {
        RestAssured.baseURI = "https://demo.thingsboard.io/";
    }
}
