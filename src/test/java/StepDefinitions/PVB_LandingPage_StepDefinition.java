package StepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.PVBLandingPage;

import static libs.DriverHelper.getWebDriver;

public class PVB_LandingPage_StepDefinition {
    private PVBLandingPage pvbLandingPage = new PVBLandingPage(getWebDriver());


    @When("^User opens 'Landing' landing page$")
    public void userOpensLandingPage() {
       pvbLandingPage.openPVBLandingPage();
       pvbLandingPage.checkUrl();
    }

    @And("^User scrolls down to exchange rates block$")
    public void userScrollsDownToExchangeRatesBlock() {
        pvbLandingPage.scrollToExchangeRatesBlock();

    }

    @And("^User chooses '(.*)' filter option$")
    public void userChoosesFilterOption(String filterOptionOnExchangeRatesBlock) {
        pvbLandingPage.chooseFilterOption(filterOptionOnExchangeRatesBlock);

    }


    @Then("User sees '(.*)' rates for '(.*)' filter option equal to rates got via API")
    public void userSeesCurrencyForFilterOptionFilterOptionEqualToRatesGotViaAPI(String currency, String filterOptionOnExchangeRatesBlock) {
        pvbLandingPage.getAndCompareExchangeRatesFromUIandAPI(currency, filterOptionOnExchangeRatesBlock);
    }
}
