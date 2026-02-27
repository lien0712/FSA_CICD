package api.api;

import api.models.LoginRequest;
import api.specs.SpecFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthAPI {
    private static String token;

    public static Response login(String email, String password) {
        return RestAssured.given()
                .spec(SpecFactory.getBaseSpec())
                .body(new LoginRequest(email, password))
                .when()
                .post("/api/auth/login");
    }
}