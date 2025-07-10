package asserts;


import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import test_data.DataUsers;

public class GetUserAsserts {
    public static void assertUsers(Response resp) {
        resp
                .then()
                .statusCode(200)
                .body("", hasSize(DataUsers.EXPECTED_SIZE))
                .body("[0].id",    equalTo(DataUsers.FIRST_ID))
                .body("[9].id",    equalTo(DataUsers.LAST_ID))
                .body("[0].email", equalTo(DataUsers.FIRST_EMAIL))
                .body("[9].email", equalTo(DataUsers.LAST_EMAIL))
                .body("[0].username", equalTo(DataUsers.FIRST_USERNAME))
                .body("[9].username", equalTo(DataUsers.LAST_USERNAME));
    }
}