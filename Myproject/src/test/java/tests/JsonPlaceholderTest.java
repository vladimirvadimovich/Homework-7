package tests;

import asserts.*;
import com.fasterxml.jackson.core.type.TypeReference;
import configs.ConfigLoader;
import client.GetUserClient;
import dto.UsersDTO;
import dto.PostsDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import test_data.DataProvider;
import test_data.DataUsers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test_data.DataLoader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonPlaceholderTest {

    private static GetUserClient client;
    private static DataProvider data;
    private static int page;
    private static int limit;

    @BeforeAll
    static void setup() {
        var cfg = ConfigLoader.load();
        client = new GetUserClient(cfg.getBaseUri());
        data = DataProvider.load();
    }



    @BeforeAll
    static void readConfig() throws Exception {
        // читаем config.json из папки resources/configs
        String baseUri;
        try (InputStream is = JsonPlaceholderTest.class.getClassLoader()
                .getResourceAsStream("configs/config.json")) {
            if (is == null) {
                throw new IllegalStateException("Не найден файл configs/config.json в classpath");
            }
            JsonNode cfg = new ObjectMapper().readTree(is);
            // именно по ключу "base_uri"
            baseUri = cfg.get("base_uri").asText();
        }

        // после получения baseUri создаём клиент
        client = new GetUserClient(baseUri);
    }

    @Test
    void firstCheckGetUsers () {
        Response response = client.getUsers();

        // Проверяем статус, размер списка и первые/последние поля
        GetUserAsserts.assertUsers(response);

        // Преобразуем в DTO и проверим размер
        List<UsersDTO> users = response.jsonPath().getList("", UsersDTO.class);
        assertEquals(DataUsers.EXPECTED_SIZE, users.size(),
                "Длина списка пользователей не совпадает");

        // Допустим, выведем первого и последнего пользователя
        System.out.println("First user: " + users.get(0));
        System.out.println("Last user:  " + users.get(users.size() - 1));
    }

    @Test
    void secondCheckFetchUsers () {
        List<PostsDTO> actualUsers  = client.fetchUsers();
        List<PostsDTO> expectedUsers = data.getUsers();
        FetchUserAsserts.assertUsersEqual(actualUsers, expectedUsers);
    }

    @Test
    void thirdCheckPostPosts() {
        PostsDTO newPost     = data.getNewPost();
        PostsDTO createdPost = client.createPost(newPost);
        CreateUserAsserts.assertPostCreated(createdPost, newPost);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2 /*, …*/})
    void getPostById_returnsExpectedPost(int id) {
        // подготовка ожидаемых данных
        PostsDTO expected = DataLoader.getExpectedPost(id);
        // вызов API
        PostsDTO actual = GetUserClient.getPostConvert(id);
        // проверки
        FindUserIdAssertions.assertPostEquals(actual, expected);
    }

    @Test
    void testPaginationFirstPage() throws Exception {
        // Инициализируем клиент
        GetUserClient client = new GetUserClient("/posts");

        // Читаем параметры page и limit из JSON (если нужно)
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Integer> params;
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("test_data/pagination_params.json")) {
            params = mapper.readValue(is, new TypeReference<Map<String, Integer>>() {});
        }
        int page = params.get("page");
        int limit = params.get("limit");

        // Вызываем метод и делаем ассерты
        List<PostsDTO> posts = client.getPagination(page, limit);
        PaginationAssertions.notEmpty(posts);
        PaginationAssertions.hasSize(posts, limit);
    }

}

