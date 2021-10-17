package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;

import static api.EndPoints.POST_BY_USER;
import static io.restassured.RestAssured.given;

public class ApiHelper {
    Logger logger = Logger.getLogger(getClass());
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public String getToken(String username, String password) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("password", password);
        ResponseBody responseBody = given()
                .spec(requestSpecification)
                .body(requestParams.toMap())
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().getBody();
        return responseBody.asString().replaceAll("\"", "");
    }

    public PostDTO[] getAllPostsByUser(String userName) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(POST_BY_USER, userName)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .response().as(PostDTO[].class);
    }

    public void deletePostsTillPresent(String userName, String password) {
        PostDTO[] listOfPosts = getAllPostsByUser(userName);
        for (int i = 0; i < listOfPosts.length; i++) {
            deletePostById(userName, password, listOfPosts[i].get_id());
            logger.info(String.format("Post with id %s and title %s was deleted"
                    , listOfPosts[i].get_id()
                    , listOfPosts[i].getTitle()));
        }
        Assert.assertEquals("Number of Posts ", 0, getAllPostsByUser(userName).length);
    }

    private void deletePostById(String userName, String password, String id) {
        String token = getToken(userName, password);

        JSONObject bodyRequest = new JSONObject();
        bodyRequest.put("token", token);

        given()
                .spec(requestSpecification)
                .body(bodyRequest.toMap())
                .when()
                .delete(EndPoints.DELETE_POST, id)
                .then().statusCode(200).log().all();
    }

    public void createPost(String title, String userName, String password) {
        String token = getToken(userName, password);
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("body", "post body");
        requestParams.put("select1", "One Person");
        requestParams.put("token", token);

            given()
                .contentType(ContentType.JSON)
                .body(requestParams.toMap())
                .log().all()
                .when()
                .post(EndPoints.CREATE_POST)
                .then()
                .statusCode(200);
    }
}
