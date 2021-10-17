package postsTest;

import baseTest.BaseTest;
import libs.Util;
import org.junit.After;
import org.junit.Test;

public class CreatePostTest extends BaseTest {
    final String POST_TITLE = "Tango title of Post" + Util.getDateAndTimeFormatted();
    String postVisibility = "Частное сообщение";
    String postVisibilityValue = "One Person";

    @Test
    public void createPost() {
        loginPage
                .loginWithValidCred()
                .checkIsRedirectOnHomePage()
//                .checkIsButtonSignOutVisible()
                .clickOnButtonCreatePost()
                .checkIsRedirectOnCreatePostPage()
                .checkIsInputTitlePresent()
                .enterTextIntoInputTitle(POST_TITLE)
                .enterTextIntoInputBody("Body text")
                .makeUniquePostCheckboxStatusToBe("check")
                .selectTextInDropDownByClick(postVisibility)
//                .selectTextInDropDownSelectValue("Частное сообщение")
//                .selectValueInDropDownSelectValue("One Person")
                .clickOnSaveButton()
                .checkIsRedirectToPostPage()
                .checkCreatedPostVisibility(postVisibilityValue)
                .checkIsSuccessMessagePresent()
                .checkTextInSuccessMessage("New post successfully created.")
                .clickOnButtonProfile()
                .checkIsRedirectToProfilePage()
                .checkIsPostWasAdded(POST_TITLE)
        ;
    }

    @After
    public void deletePost() {
        homePage
                .openHomePage()
                .checkIsRedirectOnHomePage()
                .clickOnButtonProfile()
                .checkIsRedirectToProfilePage()
                .deletePostWithTitleWhilePresent(POST_TITLE)
        ;
    }
}
