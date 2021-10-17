package StepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import pages.ProfilePage;

import static libs.DriverHelper.getWebDriver;

public class ProfilePage_StepDefinition {
    private ProfilePage profilePage = new ProfilePage(getWebDriver());

     @Then("^User is redirected to Profile page$")
    public void userIsRedirectedToProfilePage() {
        profilePage.checkIsRedirectToProfilePage();
    }

    @And("^User sees (\\d+) posts in Post list on Profile page$")
    public void userSeesPostsInPostListOnProfilePage(int expectedNumberOfPosts) {
         profilePage.checkNumberOfPosts(expectedNumberOfPosts);
    }
}
