package asserts;

import dto.PostsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindUserIdAssertions {
    public static void assertPostEquals(PostsDTO actual, PostsDTO expected) {
        assertEquals(expected.getId(), actual.getId(), "id не совпадает");
        assertEquals(expected.getUserId(), actual.getUserId(), "userId не совпадает");
        assertEquals(expected.getTitle(), actual.getTitle(), "title не совпадает");
        assertEquals(expected.getBody(), actual.getBody(), "body не совпадает");
    }
}
