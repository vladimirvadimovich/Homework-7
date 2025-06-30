import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Базовый URI для всех запросов
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


        Scanner scanner = new Scanner(System.in);
        int postId;

        // Цикл ввода корректного ID
        while (true) {
            System.out.print("Введите ID поста (1-100): ");
            String line = scanner.nextLine();
            try {
                postId = Integer.parseInt(line);
                if (postId < 1 || postId > 100) {
                    System.out.println("Ошибка: ID должен быть в диапазоне 1–100.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введено не целое число. Попробуйте ещё раз.");
            }
        }
        scanner.close();

        // Выполняем GET /posts/{id} и десериализуем в Pojo
        Pojo pojo = RestAssured
                .given()
                .pathParam("id", postId)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(Pojo.class);

        // 4. Выводим результат
        System.out.println("Заголовок: " + pojo.getTitle());
        System.out.println("Текст поста: " + pojo.getBody());
    }
}