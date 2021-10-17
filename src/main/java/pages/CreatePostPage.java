package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;

import java.util.Arrays;
import java.util.List;

public class CreatePostPage extends ParentPage {

//    @FindBy(xpath = ".//input[@name='title']")
    @FindBy(name = "title")
    private TextInput inputTitle;

    @FindBy(id = "post-body")
    private TextInput inputBody;

    @FindBy(xpath = ".//button[text()='Save New Post']")
    private Button buttonSave;

    @FindBy(xpath = ".//select[@id='select1']")
    private Select dropDownSelectValue;

    private String dropdownOptionXPathLocator = ".//option[contains(text(), '%s')]";

    @FindBy(xpath = ".//input[@id='”UniquePost”']")
    private WebElement checkboxUniquePost;

    public CreatePostPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/create-post";
    }

    public CreatePostPage checkIsInputTitlePresent() {
        Assert.assertTrue("Input Title is not present", isElementPresent(inputTitle));
        return this;
    }

    public CreatePostPage enterTextIntoInputTitle(String post_title) {
        enterTextToElement(inputTitle, post_title);
        return this;
    }

    public CreatePostPage enterTextIntoInputBody(String post_body) {
        enterTextToElement(inputBody, post_body);
        return this;
    }

    public PostPage clickOnSaveButton() {
        clickOnElement(buttonSave);
        return new PostPage(webDriver);
    }

    public CreatePostPage selectTextInDropDownSelectValue(String text) {
        selectTextInDropDown(dropDownSelectValue, text);
        return this;
    }

    public CreatePostPage selectValueInDropDownSelectValue(String value) {
        selectValueInDropDown(dropDownSelectValue, value);
        return this;
    }

    public CreatePostPage selectTextInDropDownByClick(String text) {
        WebElement dropdownOptionToBeSelected = dropDownSelectValue.findElement(By.xpath(String.format(dropdownOptionXPathLocator, text)));
        selectTextInDropDownByClickOnOption(dropDownSelectValue, dropdownOptionToBeSelected, text);
        return this;
    }

    public CreatePostPage makeUniquePostCheckboxStatusToBe(String requiredUniquePostCheckBoxStatus) {
        final List<String> checkboxStatusPossible = Arrays.asList("check", "uncheck");
        if (checkboxStatusPossible.contains(requiredUniquePostCheckBoxStatus)) {
            if (checkboxUniquePost.isSelected()) {
                if (requiredUniquePostCheckBoxStatus.equalsIgnoreCase(checkboxStatusPossible.get(0))) {
                    logger.info("CheckboxUniquePost keeps being selected");
                } else {
                    clickOnElement(checkboxUniquePost);
                    webDriverWait10.until(ExpectedConditions.elementSelectionStateToBe(checkboxUniquePost, false));
                    logger.info("CheckboxUniquePost was deselected");
                }
            } else {
                if (requiredUniquePostCheckBoxStatus.equalsIgnoreCase(checkboxStatusPossible.get(0))) {
                    clickOnElement(checkboxUniquePost);
                    webDriverWait10.until(ExpectedConditions.elementSelectionStateToBe(checkboxUniquePost, true));
                    logger.info("CheckboxUniquePost was selected");
                } else {
                    logger.info("CheckboxUniquePost keeps being not selected");
                }
            }
        } else {
            logger.error("Check required uniquePostCheckBoxStatus. Only check, uncheck are possible");
            Assert.fail("Check required uniquePostCheckBoxStatus. Only check, uncheck are possible");
        }
        return this;
    }

    public CreatePostPage checkIsRedirectOnCreatePostPage() {
        checkUrl();
        return this;
    }
}
