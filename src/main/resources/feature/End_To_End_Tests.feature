@Regression
Feature: Validate End-To-End User Experience with All Service Integration
#  $$$$$$$ What is covered here as part of End-To-End Integration Testing $$$$$$$
#  1. Validate successful 200 response from all APIs and we are getting key value pairs in wkda object so that customer never see blank dropdown
#     in all possible combination parameters as part of integration testing
#  2. Validating the successful response from API when integrated and executed with random data from Excel sheet.

  @End2EndIntegration
  Scenario: GET operation for interdependency validation of Entire Flow
    Given User perform GET operation for "manufacturer"
    Then User validates interdependent data availability for all services

  @End2EndIntegration
  Scenario: Random Validation of Car models and Year of Manufacturer using Excel Input Sheet
    Given User choose random data from input sheet
    When User perform GET operation for "main-types" using random data from input sheet
    And User gets 200 status code
    Then User perform GET operation for "built-dates" using random data from input sheet
    And User gets 200 status code