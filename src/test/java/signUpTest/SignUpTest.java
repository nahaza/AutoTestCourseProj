package signUpTest;

import baseTest.BaseTest;
import categories.SmokeTestFilter;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
@Category(SmokeTestFilter.class)
public class SignUpTest extends BaseTest {


    @Test
    public void signUpFieldValidationAlerts(){
        loginPage.fillSignUpFormAndSubmit("tr", "test.com", "123");
        checkExpectedResult("Invalid username alert is not visible", loginPage.isUsernameSignUpAlertPresent(), true);
        checkExpectedResult("Invalid email alert is not visible", loginPage.isEmailSignUpAlertPresent(),true);
        checkExpectedResult("Invalid password alert is not visible", loginPage.isPasswordSignUpAlertPresent(),true);
    }


    @Test
    public void signUpFieldValidationAlertMessages(){
        loginPage.fillSignUpFormAndSubmit("tr", "test.com", "123456qwerty");
        loginPage.checkErrors("Username must be at least 3 characters.;You must provide a valid email address.;");
    }

    @Test
    @Parameters({
            "12,qqq,345,Username must be at least 3 characters.;You must provide a valid email address.;Password must be at least 12 characters.",
            "12,qqq,123456qwerty,Username must be at least 3 characters.;You must provide a valid email address."
    })
    @TestCaseName("registrationErrors: login={0}, email={1}, password={2}")
    public void registrationErrors(String login, String email, String password, String errors){
        loginPage.openLoginPage();
        loginPage.enterLoginRegistration(login)
                .enterEmailRegistration(email)
                .enterPasswordRegistration(password)
                .checkErrorsMessages(errors);
    }
}