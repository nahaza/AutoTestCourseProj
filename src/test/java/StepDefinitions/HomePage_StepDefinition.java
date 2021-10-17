package StepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HomePage;
import pages.LoginPage;

import static libs.DriverHelper.getWebDriver;

public class HomePage_StepDefinition {
    private HomePage homePage = new HomePage(getWebDriver());
    private LoginPage loginPage = new LoginPage(getWebDriver());

    @Then("^User is redirected to 'Home' page$")
    public void user_is_redirected_to_Home_page(){
        homePage.checkIsRedirectOnHomePage();
    }

    @Given("^User opens 'Home' page$")
    public void givenUserOpensHomePage() {
        loginPage.loginWithValidCred()
                .checkIsRedirectOnHomePage();
    }

    @When("^User clicks on 'Profile' button on 'Home' page$")
    public void userClicksOnProfileButtonOnHomePage() {
        homePage.clickOnButtonProfile();
    }
}
