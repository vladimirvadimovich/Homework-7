package test_data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PostsDTO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataLoader {
    private static final Map<Integer, PostsDTO> postsById;

    static {
        try (InputStream is = DataLoader.class
                .getResourceAsStream("/test_data/posts.json")) {
            List<Map<String, Object>> raw = new ObjectMapper()
                    .readValue(is, new TypeReference<List<Map<String, Object>>>(){});
            postsById = raw.stream()
                    .map(m -> {
                        PostsDTO p = new PostsDTO();
                        p.setId((Integer) m.get("id"));
                        p.setUserId((Integer) m.get("userId"));
                        p.setTitle((String) m.get("title"));
                        p.setBody((String) m.get("body"));
                        return p;
                    })
                    .collect(Collectors.toMap(PostsDTO::getId, p -> p));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PostsDTO getExpectedPost(int id) {
        return postsById.get(id);
    }
}
