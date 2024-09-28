package blugin.com.ar;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

//@QuarkusTest
class UsuarioResourceTest {
    //@Test
    void testUsuarioEndpoint() {
        given()
          .when().get("/usaurios")
          .then()
             .statusCode(200);
             //.body(is("Nombre: sssss"));
    }

}