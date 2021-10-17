package signUpTest;

import baseTest.BaseTest;
import categories.SmokeTestFilter;
import libs.SpreadsheetData;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;


import static pages.ParentPage.*;

@RunWith(Parameterized.class)
@Category(SmokeTestFilter.class)
public class SignUpTestWithExcel extends BaseTest {
    private String login, email, password, errors;

    public SignUpTestWithExcel(String login, String email, String password, String errors) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.errors = errors;
    }

    @Parameterized.Parameters
    public static Collection testData()throws IOException{
        InputStream inputStream = new FileInputStream(configProperties.DATA_FILE_PATH()+ "testDataSuit.xls");
        return new SpreadsheetData(inputStream, "InvalidLogOn").getData();
    }

    @Test
    public void registrationErrors(){
        loginPage.openLoginPage();
        loginPage.enterLoginRegistration(login)
                .enterEmailRegistration(email)
                .enterPasswordRegistration(password)
                .checkErrorsMessages(errors);
    }
}