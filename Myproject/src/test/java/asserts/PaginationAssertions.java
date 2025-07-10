package asserts;

import dto.PostsDTO;
import org.hamcrest.Matchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class PaginationAssertions {

    public static void hasSize(List<PostsDTO> actual, int expectedSize) {
        assertThat("Expected size " + expectedSize + " but was " + actual.size(),
                actual,
                Matchers.hasSize(expectedSize));
    }

    public static void notEmpty(List<PostsDTO> actual) {
        assertThat("Expected non-empty list of posts, but it was empty",
                actual,
                not(Matchers.hasSize(0)));
    }
}
