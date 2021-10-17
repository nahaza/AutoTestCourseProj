// Exchange rates are rounded to 1 integer after dot
// Only "In the branch", "For cards" filter option are available on 12 october 2021
// Currency to be one of "USD", "EUR", "RUB" - on 12 october 2021

package pages;

import api.apiPrivatB.CurrencyDTO;
import libs.TestData;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PVBLandingPage {
    Logger logger = Logger.getLogger(getClass());
    SoftAssertions softAssertions = new SoftAssertions();
    WebDriver webDriver;
    WebDriverWait webDriverWait5;
    protected final String baseUrl = "https://en.privatbank.ua/";

    public PVBLandingPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait5 = new WebDriverWait(webDriver, 5);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = ".//article[@class='block_content courses']")
    protected WebElement exchangeRatesBlock;

    @FindBy(xpath = ".//article[@class='block_content courses']//button[@data-id='courses_type']")
    protected WebElement rateTypeButton;

    @FindBy(xpath = ".//article[@class='block_content courses']//ul[@class='dropdown-menu inner']")
    protected WebElement dropDownExchangeRatesOptionMenu;


    private String dropDownExchangeRatesOptionLocator =
            ".//article[@class='block_content courses']//ul[@class='dropdown-menu inner']//a[@role='option'][.//span[text()='%s']]";


    public final ArrayList<String> expectedCurrencyCodeNameListOnUI = new ArrayList<>();
    boolean makeExpectedCurrencyCodeNameOnUIList = Collections.addAll(expectedCurrencyCodeNameListOnUI, "USD", "EUR", "RUB");


    public static ArrayList<String> expectedFilterOptionOnExchangeRatesBlockOnUI = new ArrayList<>();
    boolean makeExpectedFilterOptionOnExchangeRatesBlock = Collections.addAll(expectedFilterOptionOnExchangeRatesBlockOnUI, "In the branch", "For cards");


    public void openPVBLandingPage() {
        try {
            webDriver.get(baseUrl);
            logger.info("Landing Page " + baseUrl + " was opened");
        } catch (Exception e) {
            logger.error("Can not work with LandingPage" + e);
            Assert.fail("Can not work with LandingPage");
        }
    }

    public void checkUrl() {
        Assert.assertEquals("Invalid page"
                , baseUrl
                , webDriver.getCurrentUrl()
        );
    }

    public void scrollToExchangeRatesBlock() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();"
                , exchangeRatesBlock);
        webDriverWait5.until(ExpectedConditions.visibilityOf(exchangeRatesBlock));
        logger.info("ExchangeRatesBlock is visible");
    }

    public void chooseFilterOption(String expectedFilterOptionOnExchangeRatesBlock) {
        String actualFilterOptionOnExchangeRatesBlock = rateTypeButton.getAttribute("title");
        logger.info("Filter option: " + actualFilterOptionOnExchangeRatesBlock);
        if (!actualFilterOptionOnExchangeRatesBlock.equalsIgnoreCase(expectedFilterOptionOnExchangeRatesBlock)) {
            try {
                webDriverWait5.until(ExpectedConditions.elementToBeClickable(rateTypeButton));
                rateTypeButton.click();
                webDriverWait5.until(ExpectedConditions.visibilityOf(dropDownExchangeRatesOptionMenu));
                webDriver.findElement(By.xpath(
                        String.format(dropDownExchangeRatesOptionLocator, expectedFilterOptionOnExchangeRatesBlock))).click();
                webDriverWait5.until(
                        ExpectedConditions.attributeToBe(rateTypeButton, "title", expectedFilterOptionOnExchangeRatesBlock));
                logger.info(rateTypeButton.getAttribute("title") + " Element was clicked");
                logger.info("Filter option: " + rateTypeButton.getAttribute("title"));
            } catch (Exception e) {
                logger.error("Impossible to choose exchange rates option " + e);
                Assert.fail("Impossible to choose exchange rates option " + e);
            }
        }
    }

    public String getLocatorOfExchangeRateRowsListOnUI(String filterOptionOnExchangeRatesBlock) {
        String exchangeRateTableType = null;
        if (filterOptionOnExchangeRatesBlock.equalsIgnoreCase("In the branch")) {
            exchangeRateTableType = "posts_course";
        } else if (filterOptionOnExchangeRatesBlock.equalsIgnoreCase("For cards")) {
            exchangeRateTableType = "cards_course";
        }
        return String.format(".//div[@data-cource_type='%s']//tbody//tr", exchangeRateTableType);
    }

    public String getLocatorOfRatesInExchangeRateRowsTableOnUI(
            String currency, String filterOptionOnExchangeRatesBlock, String rateType, Integer rowNumber) {
        String locator = null;
        String exchangeRateTableType = getLocatorOfExchangeRateRowsListOnUI(filterOptionOnExchangeRatesBlock);
        int numberOfColumn = 3;//to make default check by column 3 in Exchange rates table
        if (filterOptionOnExchangeRatesBlock.equalsIgnoreCase("In the branch")) {
            locator = String.format("%s[%s]//td[@id='%s_%s']"
                    , exchangeRateTableType, rowNumber + 1, currency, rateType);
        } else if (filterOptionOnExchangeRatesBlock.equalsIgnoreCase("For cards")) {
            if (rateType.equalsIgnoreCase("sell")) {
                numberOfColumn = 4;
            }
            locator = String.format("%s[%s]//td[%s]"
                    , exchangeRateTableType, rowNumber + 1, numberOfColumn);
        }
        return locator;
    }


    public String[] getRateTypeList(String filterOptionOnExchangeRatesBlock) {
        String[] rateTypeList = new String[0];
        if (filterOptionOnExchangeRatesBlock.equalsIgnoreCase("In the branch") ||
                filterOptionOnExchangeRatesBlock.equalsIgnoreCase("For cards")) {
            rateTypeList = new String[]{"buy", "sell"};
        } else {
            logger.error("No appropriate rateTypeList");
            Assert.fail("No appropriate rateTypeList");
        }
        return rateTypeList;
    }

    public double round(String value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String getAPICurrencyWithFixedRUBissue(String currencyCodeName) {
        if (currencyCodeName.equalsIgnoreCase("RUB")) {
            return "RUR";
        } else return currencyCodeName;
    }

    public void getAndCompareExchangeRatesFromUIandAPI(String currency, String filterOptionOnExchangeRatesBlock) {
        String[] currencyList = currency.split(",");
        for (int i = 0; i < currencyList.length; i++) {
            if (!expectedCurrencyCodeNameListOnUI.contains(currencyList[i])) {
                logger.error(currencyList[i] + " currency is not appropriate");
                Assert.fail(currencyList[i] + " currency is not appropriate");
            }
            if (!expectedFilterOptionOnExchangeRatesBlockOnUI.contains(filterOptionOnExchangeRatesBlock)) {
                logger.error(filterOptionOnExchangeRatesBlock + " filterOption is not appropriate");
                Assert.fail(filterOptionOnExchangeRatesBlock + " filterOption is not appropriate");
            }
            if (expectedCurrencyCodeNameListOnUI.contains(currencyList[i]) &&
                    expectedFilterOptionOnExchangeRatesBlockOnUI.contains(filterOptionOnExchangeRatesBlock)) {
                getAndCompareExchangeRatesFromUIandAPIWithFilterOption(currencyList[i], filterOptionOnExchangeRatesBlock);
            } else {
                logger.error("No method for filter option: " + filterOptionOnExchangeRatesBlock);
                Assert.fail("No method  for filter option: " + filterOptionOnExchangeRatesBlock);
            }
        }
    }

    public void getAndCompareExchangeRatesFromUIandAPIWithFilterOption(String currency, String filterOptionOnExchangeRatesBlock) {
        String[] rateTypeList = getRateTypeList(filterOptionOnExchangeRatesBlock);
        List<WebElement> listOfCurrencyRates =
                webDriver.findElements(By.xpath(getLocatorOfExchangeRateRowsListOnUI(filterOptionOnExchangeRatesBlock)));
        Assert.assertEquals("Number of Expected and Actual currency rows on UI are not equal"
                , expectedCurrencyCodeNameListOnUI.size(), listOfCurrencyRates.size());
        for (int i = 0; i < listOfCurrencyRates.size(); i++) {
            String currencyFromUI = webDriver.findElement(By.xpath(
                    String.format(getLocatorOfExchangeRateRowsListOnUI(filterOptionOnExchangeRatesBlock) + "[%s]//td[1]", i + 1))).getText();
            if (currencyFromUI.equalsIgnoreCase(currency)) {
                String baseCurrencyFromUI = webDriver.findElement(By.xpath(
                        String.format(getLocatorOfExchangeRateRowsListOnUI(filterOptionOnExchangeRatesBlock) + "[%s]//td[2]", i + 1))).getText();
                softAssertions.assertThat(baseCurrencyFromUI).isEqualTo(getBaseCurrencyFromAPI(currency));
                logger.info(currencyFromUI + ": baseCurrencyUI :" + baseCurrencyFromUI + ", baseCurrencyAPI: " + getBaseCurrencyFromAPI(currency));
                for (int j = 0; j < rateTypeList.length; j++) {
                    Double currencyRateBuiFrUI =
                            round(webDriver.findElement(By.xpath(
                                    getLocatorOfRatesInExchangeRateRowsTableOnUI(
                                            currency, filterOptionOnExchangeRatesBlock, rateTypeList[j], i))).getText(), 1);
                    softAssertions.assertThat(currencyRateBuiFrUI).isEqualTo(getExchangeRateFromAPI(currency, rateTypeList[j]));
                    logger.info(currencyFromUI + ": " + rateTypeList[j] + "RateUI :" + "fromUI: " + currencyRateBuiFrUI +
                            ", fromAPI: " + getExchangeRateFromAPI(currency, rateTypeList[j]));
                }
            }
        }
        softAssertions.assertAll();
    }

    public String getBaseCurrencyFromAPI(String currency) {
        String currencyConverted = getAPICurrencyWithFixedRUBissue(currency);//for getting RUB from API
        String baseCurrencyFromAPI = null;
        CurrencyDTO[] currencyFromApi = TestData.getApiCurrencyDTO();
        for (int i = 0; i < currencyFromApi.length; i++) {
            if (currencyFromApi[i].getCcy().equalsIgnoreCase(currencyConverted)) {
                baseCurrencyFromAPI = currencyFromApi[i].getBase_ccy();
            }
        }
        return baseCurrencyFromAPI;
    }

    public Double getExchangeRateFromAPI(String currency, String rateTypeToCompare) {
        String currencyConverted = getAPICurrencyWithFixedRUBissue(currency);//for getting RUB from API
        Double rateFromApi = null;
        CurrencyDTO[] currencyFromApi = TestData.getApiCurrencyDTO();
        for (int i = 0; i < currencyFromApi.length; i++) {
            try {
                if (currencyFromApi[i].getCcy().equalsIgnoreCase(currencyConverted)) {
                    if (rateTypeToCompare.equalsIgnoreCase("buy")) {
                        rateFromApi =
                                round(currencyFromApi[i].getBuy(), 1);
                    } else if (rateTypeToCompare.equalsIgnoreCase("sell")) {
                        rateFromApi =
                                round(currencyFromApi[i].getSale(), 1);
                    } else logger.error("Exchange rate type is not appropriate");
                }
            } catch (Exception e) {
                logger.error("No data for required currency and rateType");
            }
        }
        return rateFromApi;
    }

}
