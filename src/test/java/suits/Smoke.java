package suits;


import apiTests.ApiTests;
import loginTest.LoginTestWithPageObject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import signUpTest.SignUpTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTestWithPageObject.class,
        SignUpTest.class,
        ApiTests.class
})
public class Smoke {
}
