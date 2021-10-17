@PosTest @FullRegression
Feature: Post feature

Background:
  Given User opens 'Home' page

  @R003
  @BeforeDeletingAllPostsForDefaultUser
  @AfterDeletingAllPostsForDefaultUser
  Scenario: R003 Check number of posts
    And Create 2 new posts via API for 'default' user and 'default' password
    When User clicks on 'Profile' button on 'Home' page
    Then User is redirected to Profile page
    And User sees 2 posts in Post list on Profile page