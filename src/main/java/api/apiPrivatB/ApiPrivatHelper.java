package api.apiPrivatB;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;


import static io.restassured.RestAssured.given;

public class ApiPrivatHelper {
    Logger logger = Logger.getLogger(getClass());
    RequestSpecification requestSpecification =
            new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();


    public CurrencyDTO[] getApiCurrencyDTO(String typeOfExchangeRateApi) {
        String courseId = "";
        if (typeOfExchangeRateApi.equalsIgnoreCase("In the branch")) {
            courseId = "5";
        } else if (typeOfExchangeRateApi.equalsIgnoreCase("For cards")) {
            courseId = "11";
        } else {
            Assert.fail("No appropriate Endpoint");
        }

        CurrencyDTO[] actualApiCurrencyDTO = given()
                .spec(requestSpecification)
                .queryParam("json")
                .queryParam("exchange")
                .queryParam("coursid", courseId)
                .when()
                .get(EndPoints.privatBankApiExchangeRate)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .response().as(CurrencyDTO[].class);

        CurrencyDTO[] expectedCurrencyDTO = {
                new CurrencyDTO("USD", "UAH"),
                new CurrencyDTO("EUR", "UAH"),
                new CurrencyDTO("RUR", "UAH"),
                new CurrencyDTO("BTC", "USD")
        };

        SoftAssertions softAssertions = new SoftAssertions();
        Assert.assertEquals(expectedCurrencyDTO.length, actualApiCurrencyDTO.length);

        for (int i = 0; i < actualApiCurrencyDTO.length; i++) {
            softAssertions.assertThat(expectedCurrencyDTO[i])
                    .as(i + "incorrect currency")
                    .isEqualToIgnoringGivenFields(actualApiCurrencyDTO[i], "buy", "sale");
        }
        softAssertions.assertAll();
        logger.info("CurrencyDTO length from API: " + actualApiCurrencyDTO.length);
        return actualApiCurrencyDTO;
    }
}
