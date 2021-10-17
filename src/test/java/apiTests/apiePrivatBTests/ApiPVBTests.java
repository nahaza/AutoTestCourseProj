package apiTests.apiePrivatBTests;

import api.apiPrivatB.CurrencyDTO;
import api.apiPrivatB.EndPoints;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiPVBTests extends ApiPVBBaseTest {

    @Test
    public void getAllCurrencyRate() {
        final String[] expectedCurrencyArray = {"USD", "EUR", "RUR", "BTC"};
        final String[] expectedBasicCurrencyArray = {"UAH", "USD"};
        CurrencyDTO[] responseBody = given()
                .contentType(ContentType.JSON)
                .queryParam("json")
                .queryParam("exchange")
                .queryParam("coursid", "5")
                .log().all()
                .when()
                .get(EndPoints.privatBankApiExchangeRate)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .response().as(CurrencyDTO[].class);
        SoftAssertions softAssertions = new SoftAssertions();
        Assert.assertEquals(responseBody.length, expectedCurrencyArray.length);

        CurrencyDTO[] expectedCurrencyDTO_1 = new CurrencyDTO[expectedCurrencyArray.length];
        String basicCurrency;
        for (int i = 0; i < expectedCurrencyArray.length; i++) {
            if (expectedCurrencyArray[i].equalsIgnoreCase("BTC")){
                basicCurrency = expectedBasicCurrencyArray[1];
            } else basicCurrency =expectedBasicCurrencyArray[0];
            expectedCurrencyDTO_1[i] = new CurrencyDTO(expectedCurrencyArray[i], basicCurrency);
            softAssertions.assertThat(expectedCurrencyDTO_1[i])
        .as(responseBody[i] + " has incorrect basicCurrency ")
                    .isEqualToIgnoringGivenFields(responseBody[i], "buy", "sale");
        }

        CurrencyDTO[] expectedCurrencyDTO_2 = {
                new CurrencyDTO("USD", "UAH"),
                new CurrencyDTO("EUR", "UAH"),
                new CurrencyDTO("RUR", "UAH"),
                new CurrencyDTO("BTC", "USD")
        };

        for (int i = 0; i < expectedCurrencyDTO_2.length; i++) {
            softAssertions.assertThat(expectedCurrencyDTO_2[i])
                    .as(responseBody[i] + " has incorrect basicCurrency ")
                    .isEqualToIgnoringGivenFields(responseBody[i], "buy", "sale");
        }
        softAssertions.assertAll();

        for (int i = 0; i < responseBody.length; i++) {
            System.out.println("Курс " + responseBody[i].getCcy() +
                    " к " + responseBody[i].getBase_ccy() +
                    " покупки " + responseBody[i].getBuy() +
                    " и продажи " + responseBody[i].getSale());
        }
    }


    @Test
    public void getAllCurrencyRateBySchema() {
        given()
                .contentType(ContentType.JSON)
                .log().all()
                .queryParam("json")
                .queryParam("exchange")
                .queryParam("coursid", "5")
                .when()
                .get(EndPoints.privatBankApiExchangeRate)
                .then()
                .statusCode(200)
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("responseCashExchangePVB.json"));
    }
}
