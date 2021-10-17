package apiTests;

import api.ApiHelper;
import api.AuthorDTO;
import api.EndPoints;
import api.PostDTO;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreatePostByApiTest {
    final String userName = "auto456";
    final String password = "123456qwerty";
    ApiHelper apiHelper = new ApiHelper();

    @Before
    public void deleteAllPosts() {
        apiHelper.deletePostsTillPresent(userName, password);
    }

    @Test
    public void createNewPostByApi() {
        String token = apiHelper.getToken(userName, password);
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "New Post from Api - tango");
        requestParams.put("body", "post body");
        requestParams.put("select1", "One Person");
        requestParams.put("token", token);

        String response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured())
                .body(requestParams.toMap())
                .log().all()
                .when()
                .post(EndPoints.CREATE_POST)
                .then()
                .statusCode(200)
                .extract().response().getBody().asString();

        Assert.assertEquals("Response answer", "\"Congrats.\"", response);

        PostDTO[] actualPostDTO = apiHelper.getAllPostsByUser(userName);
        Assert.assertEquals("Number of posts", 1, actualPostDTO.length);

        PostDTO[] expectedPostDTO = {
                new PostDTO(
                        requestParams.getString("title")
                        , requestParams.getString("body")
                        , requestParams.getString("select1")
                        , new AuthorDTO(userName)
                        , false
                )
        };

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualPostDTO[0]).isEqualToIgnoringGivenFields(expectedPostDTO[0]
                , "_id", "createdDate", "author");
        softAssertions.assertThat(actualPostDTO[0].getAuthor())
                .isEqualToIgnoringGivenFields(expectedPostDTO[0].getAuthor(), "avatar");
        softAssertions.assertAll();

    }
}
