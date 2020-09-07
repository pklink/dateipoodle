package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

@QuarkusTest
public class StaticResourcesUploadTest {

    @Test
    public void test_Ok() {
        RestAssured.given()
            .auth().basic("admin", "password")
            .when().get("/upload/")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void test_Unauthorized() {
        RestAssured.given()
            .when().get("/upload/")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

}
