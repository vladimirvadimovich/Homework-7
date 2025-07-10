package asserts;

import dto.PostsDTO;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FetchUserAsserts {
    private static final int EXPECTED_USERS_COUNT = 100;
    public static void assertUsersEqual(List<PostsDTO> actual, List<PostsDTO> expected) {
        assertThat(actual, hasSize(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            PostsDTO exp = expected.get(i);
            PostsDTO act = actual.get(i);
            assertThat(act.getUserId(), equalTo(exp.getUserId()));
            assertThat(act.getId(), equalTo(exp.getId()));
            assertThat(act.getTitle(), equalTo(exp.getTitle()));
            assertThat(act.getBody(), equalTo(exp.getBody()));
        }
    }
}




