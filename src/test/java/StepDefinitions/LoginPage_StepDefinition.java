package StepDefinitions;

import static libs.DriverHelper.getWebDriver;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import libs.TestData;
import pages.LoginPage;


public class LoginPage_StepDefinition {
    final String DEFAULT_USER_WITH_VALID_CRED = "defaultUserWithValidCred";
    private LoginPage loginPage = new LoginPage(getWebDriver());

    @Given("^User opens 'Login' page$")
    public void UserOpensLoginPage(){
        loginPage.openLoginPage();
    }

    @When("^User enters '(.*)' login into 'Login' input on 'Login' page$")
    public void user_enters_Wrong_login_login_into_Login_input_on_Login_page(String userName) {
        loginPage.enterLoginInSignIn(userName);
    }

    @When("^User enters '(.*)' passWord into 'PassWord' input on 'Login' page$")
    public void user_enters_Wrong_pass_passWord_into_PassWord_input_on_Login_page(String password) {
        loginPage.enterPassWordInSignIn(password);
    }

    @When("^User click on 'SingIn' button on 'Login' page$")
    public void user_click_on_SingIn_button_on_Login_page() {
        loginPage.clickOnButtonSignIn();
    }

    @Then("^User sees alert message with text '(.*)'$")
    public void user_sees_alert_message_with_text_Invalid_username_password(String message) {
    loginPage.checkAlertMessageText(message);
    }

    @When("^User enters '(.*)' Login into 'Login' input on 'Login' page$")
    public void userEntersDefaultUserWithValidCredLoginIntoLoginInputOnLoginPage(String login) {
        if (DEFAULT_USER_WITH_VALID_CRED.equalsIgnoreCase(login)){
            login = TestData.VALID_LOGIN;
        }
        loginPage.enterLoginInSignIn(login);
    }

    @And("^User enters '(.*)' Password into 'PassWord' input on 'Login' page$")
    public void userEntersDefaultUserWithValidCredPasswordIntoPassWordInputOnLoginPage(String password) {
        if (DEFAULT_USER_WITH_VALID_CRED.equalsIgnoreCase(password)){
            password = TestData.VALID_PASSWORD;
        }
        loginPage.enterPassWordInSignIn(password);
    }
}
