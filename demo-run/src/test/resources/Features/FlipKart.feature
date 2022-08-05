Feature: FlipKart

  @Search
  Scenario: Search Product in FlipKart
    Given I navigate to Flipkart
    When I search for "Samsung Galaxy S10" and select "Mobiles" in Categories
    And I set the following filters and select Flipkart Assured
      | Brand   |
      | SAMSUNG |
    And I sort the Price from High to Low
    Then I capture all the results on the First Page



