import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static io.restassured.RestAssured.given;

public class PaginationExample {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final int LIMIT = 5;

    public static void main(String[] args) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Scanner scanner = new Scanner(System.in);
        int page = 1;
        String command = """
                """;

        do {
            // Выполняем запрос с параметрами _page и _limit
            String json = given()
                    .queryParam("_page", page)
                    .queryParam("_limit", LIMIT)
                    .when()
                    .get(BASE_URL)
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

            // Парсим в список карт
            JsonPath jp = new JsonPath(json);
            List<Map<String, Object>> posts = jp.getList("$");

            // Если список пуст — выходим
            if (posts.isEmpty()) {
                System.out.println("Постов больше нет. Возвращаемся на предыдущую страницу.");
                page = Math.max(1, page - 1);
                continue;
            }

            // Печатаем
            System.out.printf("=== Страница %d ===%n", page);
            for (Map<String, Object> post : posts) {
                System.out.printf("ID: %s, Title: %s%n", post.get("id"), post.get("title"));
            }

            // Предлагаем управление
            System.out.println("\n[N] Next  /  [P] Previous  /  [Q] Quit");
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "n":
                    page++;
                    break;
                case "p":
                    page = Math.max(1, page - 1);
                    break;
                case "q":
                    System.out.println("Выход...");
                    break;
                default:
                    System.out.println("Неизвестная команда, попробуйте again.");
            }

        } while (!command.equals("q"));

        scanner.close();
    }
}