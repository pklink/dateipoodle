package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class StaticResourcesUploadTest {

    @Test
    public void testIs200() {
        RestAssured.given()
            .auth().basic("admin", "password")
            .when().get("/upload/")
            .then()
            .statusCode(200);
    }

    @Test
    public void testIs401() {
        RestAssured.given()
            .when().get("/upload/")
            .then()
            .statusCode(401);
    }

}
