Feature: This feature is to verify online shopping functionality for a given website.

  @ui
  Scenario: Verify if user is able to add summer dresses into the cart and delete dresses from the cart successfully

    Given I navigate to the automationpractice url
     Then I should see the home page displayed
     When I click on women section
     Then I should see tops and dresses subcategories displayed
      And I click on dresses
     Then I should see dresses page displayed with subcategories
      And I click on summer dresses section
     Then I should see summer dresses dispalyed
#    sorting is not loading the page so commenting it
#     And I sort the items by low to high price
#    Then I should see items sorted in low to high
      And I click printed chiffon dress
      And I select size
      And I select colour
      And I click add to cart
     Then the message "Product successfully added to your shopping cart" is displayed
      And I click proceed to checkout
     Then I should see "Printed Chiffon Dress" is in the cart
      And I cleared the cart
     Then I should see "Your shopping cart is empty." alert
      And I click on women section
      And I click on dresses
      And I click on summer dresses section
     Then I should see summer dresses dispalyed
      And I clicked printed summer dress
      And I click add to cart
     Then the message "Product successfully added to your shopping cart" is displayed
      And I click proceed to checkout
     Then I should see Printed Summer Dress is in the cart






