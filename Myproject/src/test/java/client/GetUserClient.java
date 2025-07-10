package client;

import dto.PostsDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class GetUserClient {
    private final String baseUri;

    public GetUserClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public Response getUsers() {
        return RestAssured
                .given()
                .baseUri(baseUri)
                .when()
                .get("/users");
    }

    public List<PostsDTO> fetchUsers() {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().body()
                .jsonPath().getList("", PostsDTO.class);
    }



    public PostsDTO createPost(PostsDTO post) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(JSON)
                .body(post)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract().as(PostsDTO.class);
    }

    public static Response getPostResponse(int id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/posts/{id}");
    }

    public static PostsDTO getPostConvert(int id) {
        return getPostResponse(id)
                .then().statusCode(200)
                .extract().as(PostsDTO.class);
    }

    public List<PostsDTO> getPagination(int page, int limit) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Response resp = given()
                .queryParam("_page", page)
                .queryParam("_limit", limit)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().response();

        return resp.jsonPath().getList("", PostsDTO.class);
    }
}
