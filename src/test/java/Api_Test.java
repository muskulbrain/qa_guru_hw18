import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class Api_Test {
@Test
    void singleUser() {
    given()
    .when()
            .get("https://reqres.in/api/users/2")

    .then()
            .log().body()
            .statusCode(200)
            .body("data.email",is("janet.weaver@reqres.in"))
    ;
}

@Test
    void createUser() {

    String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

    given()
            .log().uri()
            .body(body)
            .contentType(JSON)

    .when()
            .post("https://reqres.in/api/users")

    .then()
            .log().status()
            .log().body()
            .statusCode(201)
            .body("name",is("morpheus"))
            .body("job",is("leader"))
    ;
}
@Test
    void createdEmptyUser() {

    String body = "{  }";

    given()
            .log().all()
            .body(body)
            .contentType(JSON)

            .when()
            .post("https://reqres.in/api/users")

            .then()
            .log().status()
            .log().body()
            .statusCode(201)
             ;
}
    @Test
    void updateUser() {

        String body = "{ \"name\": \"Morpheus\", \"job\": \"Machine\", " +
                "\"email\": \"I'AmMachine@matrix.com\"}";

        given()
                .log().all()
                .body(body)
                .contentType(JSON)

                .when()
                .put("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("email", is("I'AmMachine@matrix.com"))
        ;
    }

    @Test
    void registerUser() {

        String body = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .log().all()
                .body(body)
                .contentType(JSON)

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
        ;
    }

    @Test
    void checkJSON() {

        given()
                .log().uri()

                .when()
                .get("https://reqres.in/api/unknown/3")

                .then()
                .log().status()
                .statusCode(200)
                .body("data.name", is("true red"))
                .body(matchesJsonSchemaInClasspath("schemes/test.json"));
        ;
    }

}
