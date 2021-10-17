package testDB;

import libs.Database;
import libs.MySQL_Database;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestDB {
    private Database mySQLDataBase;
    private Logger logger = Logger.getLogger(getClass());

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        mySQLDataBase = MySQL_Database.getDataBase();
    }

    @After
    public void tearDown() throws SQLException {
        mySQLDataBase.quit();
    }

    @Test
    public void testDataFromDataBase() throws SQLException {
        List<Map<String, String>> dataFromSeleniumTable =
                mySQLDataBase.selectTableAsMap(
                        "select * from seleniumTable  where login='G2Tango'"
                );
        logger.info(dataFromSeleniumTable);
//        int numberOfRow = mySQLDataBase.changeTable(
//                "INSERT into seleniumTable VALUES(987, 'G2Tango', 'pass')");
//        logger.info("Rows = " + numberOfRow);
        dataFromSeleniumTable =
                mySQLDataBase.selectTableAsMap(
                        "select * from seleniumTable  where login='G2Tango'"
                );
        logger.info(dataFromSeleniumTable);

    }
}
