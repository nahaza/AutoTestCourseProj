package apiTests.apiePrivatBTests;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class ApiPVBBaseTest {
    protected Logger logger = Logger.getLogger(getClass());

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        logger.info("---------" + testName.getMethodName() + "was started----------");
    }

    @After
    public void tearDown() {
        logger.info("---------" + testName.getMethodName() + " was finished----------");
    }
}