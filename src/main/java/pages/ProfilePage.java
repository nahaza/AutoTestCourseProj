package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import java.util.List;

public class ProfilePage extends ParentPage {
    String postTitleLocator = ".//*[text()='%s']";
    @FindBy(xpath = ".//*[contains(text(), 'successfully deleted')]")
    private TextBlock successPostDeleteElement;

    @FindBy(xpath = ".//*[@class='list-group']/a")
    private List<WebElement> postsList;


    public ProfilePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/profile/";
    }

    public ProfilePage checkIsRedirectToProfilePage(){
        checkUrlWithPattern();
        return this;
    }

    public ProfilePage checkIsPostWasAdded(String post_title) {
        List<WebElement> postList = webDriver.findElements(
                By.xpath(String.format(postTitleLocator, post_title))
        );
        Assert.assertEquals("Number of posts with title " + post_title
                , 1, postList.size());
        return this;
    }

    public ProfilePage deletePostWithTitleWhilePresent(String post_title) {
        List<WebElement> listOfPosts = webDriver.findElements(
                By.xpath(String.format(postTitleLocator, post_title))
        );
        int counter = 0;
        while (!listOfPosts.isEmpty() && counter<100) {
            clickOnElement(webDriver.findElement(By.xpath(
                    String.format(postTitleLocator, post_title)
            )), "Post with title");
            new PostPage(webDriver)
                    .clickOnDeleteButton()
            .checkIsSuccessDeletePostMessagePresent();
            listOfPosts = webDriver.findElements(
                    By.xpath(String.format(postTitleLocator, post_title))
            );
            counter++;
        }
        return this;
    }

    public ProfilePage checkIsSuccessDeletePostMessagePresent() {
        Assert.assertTrue("SuccessDeletePostMessage is not present", isElementPresent(successPostDeleteElement));
        return this;
    }

    public void checkNumberOfPosts(int expectedNumberOfPosts) {
        Assert.assertEquals("Number of posts", expectedNumberOfPosts, postsList.size());
    }
}
