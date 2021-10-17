package StepDefinitions;

import api.apiPrivatB.ApiPrivatHelper;
import cucumber.api.java.en.Given;
import libs.TestData;

public class PVB_API_StepDefinition {
    ApiPrivatHelper apiPrivatHelper = new ApiPrivatHelper();

    @Given("^Get exchange rates for '(.*)' via API$")
    public void getExchangeRatesForFilterOptionViaAPI(String typeOfExchangeRateApi) {
        new TestData().setApiCurrencyDTO(apiPrivatHelper.getApiCurrencyDTO(typeOfExchangeRateApi));
    }
}
