package test_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PostsDTO;


import java.io.InputStream;
import java.util.List;

public class DataProvider {
    // прежние поля
    private List<PostsDTO> users;
    private PostsDTO newPost;


    public List<PostsDTO> getUsers() {
        return users;
    }

    public PostsDTO getNewPost() {
        return newPost;

    }

   // статический загрузчик
    public static DataProvider load() {
        try (InputStream is = DataProvider.class
                .getResourceAsStream("/test_data/users_test_data.json")) {
            return new ObjectMapper()
                    .readValue(is, DataProvider.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load users_posts_data.json", e);
        }
    }
}


