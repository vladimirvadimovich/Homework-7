package asserts;

import dto.PostsDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateUserAsserts {
    public static void assertPostCreated(PostsDTO actual, PostsDTO expected) {
        assertThat(actual.getId(), notNullValue());
        assertThat(actual.getUserId(), equalTo(expected.getUserId()));
        assertThat(actual.getTitle(), equalTo(expected.getTitle()));
        assertThat(actual.getBody(), equalTo(expected.getBody()));
    }
}