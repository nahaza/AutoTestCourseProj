@CurrencyRateTest
Feature: PBANUA2X Exchange Rates


  @R004
  Scenario Outline: R004 Check displayed currency cash exchange rates
    Given Get exchange rates for '<filterOption>' via API
    When User opens 'Landing' landing page
    And User scrolls down to exchange rates block
    And User chooses '<filterOption>' filter option
    Then User sees '<currency>' rates for '<filterOption>' filter option equal to rates got via API



    Examples:
      | currency      | filterOption  |
      | EUR,USD,RUB   | In the branch |
      | EUR,USD,RUB   | For cards     |

