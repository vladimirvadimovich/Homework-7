import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class RestAssuredTest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";


    @Test//аннотация
    public void firstCheckGetUsers() {
        Response response = given().baseUri(BASE_URL)
        .when()
        .get("/users")
        .then().assertThat()
        .statusCode(200)
        .body("", hasSize(10))
        .body("[0].id", equalTo(1))
        .body("[9].id", equalTo(10))
        .body("[0].email", equalTo("Sincere@april.biz"))
        .body("[9].email", equalTo("Rey.Padberg@karina.biz"))
        .body("[0].username", equalTo("Bret"))
        .body("[9].username", equalTo("Moriah.Stanton"))
        .extract().response();
        // Отправляю JSON-ответ в список карт (каждая карта — это один пользователь)
        JsonPath jsonPath = response.jsonPath();
        List<Map> users = jsonPath.getList("", Map.class);
        // Прохожу по списку и печатаю name и email
        for (Map<String, Object> user : users) {
            System.out.println("Name: " + user.get("name")
                    + " | Email: " + user.get("email"));
        }
    }

    @Test//аннотация
    public void secondCheckPostPosts() {
        String jsonBody = """
            {
                "name": "Владимир Грешилов"
            }
            """;
        //  Отправляю POST запрос
        Response response = RestAssured
        .given()
        .baseUri(BASE_URL)
        .basePath("/posts")
        .contentType(JSON)
        .body(jsonBody)
        .when()
        .post()
        .then().assertThat()
        .statusCode(201)
        .body("name", equalTo("Владимир Грешилов"))
        .extract().response();

        // Забираем из ответа ID и печатаем
        int id = response.jsonPath().getInt("id");
        System.out.println("Данные отправлены. ID записи: " + id);
        }



}



