package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostsDTO {
    private Integer userId;
    private Integer id;
    private String title;

    @JsonProperty("body")
    private String body;
}