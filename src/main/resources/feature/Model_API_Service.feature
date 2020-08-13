Feature: Validate Car Model API Service
#  1. Validate successful 200 response from GET method and
#     we are getting key value pairs in wkda object so that customer never see blank dropdown
#  2. Validate the wrapping json as per business requirement which includes page, pageSize, totalPageCount & Json object wkda
#  3. Validating the successful response from API with random data from Excel sheet.
#  4. Validate whether wkda object is returning the unique list of car Model names for all different Manufacturer.
#  5. Validate not getting success response for any other HTTP method like POST for the developed GET service.
#  6. Validating that Endpoint should not accept any additional or invalid query parameters for the service.
#  7. Validate that Endpoint should not accept HTTPS protocol which is not part of requirement


  Scenario Outline: GET operation Success Status for Main Types Service and no blank wkda object
    Given User perform GET operation for "main-types" with "<ManufacturerCode>"
    When User gets 200 status code
    Then User should not get blank wkda object

    Examples:
      | ManufacturerCode |
      | 040              |
      | 365              |
      | 960              |
      | 520              |
      | 487              |

  @Smoke
  Scenario: Validate wrapping json for Main Types Service
    Given User perform GET operation on "main-types" for Schema Validation
    When User gets 200 status code
    Then User validates wrapping json for the service

  @Smoke
  Scenario: Random Validation of Car models and Year of Manufacturer using Excel Input Sheet
    Given User choose random data from input sheet
    When User perform GET operation for "main-types" using random data from input sheet
    And User gets 200 status code

  @Regression
  Scenario: Validate list of Unique Car models for different manufacturer
    Given User perform GET operation for "manufacturer"
    And User see unique model types for different manufacturer

  @NegativeScenario
  Scenario: POST operation Failure Status for Manufacturer Service
    Given User perform POST operation for "main-types" with request body
    Then User should not get 200 status code

  @NegativeScenario
  Scenario: Adding Valid parameters followed by Invalid query parameters for Main Types Service
    Given User perform GET operation for "main-types" with below parameters
      | Key          | Value |
      | manufacturer | 190   |
      | test         | test  |
      | test         | test  |
    Then User should not get 200 status code

  @NegativeScenario
  Scenario: Protocol changed from HTTP to HTTPS for Main Types Service as per spec
    Given User perform GET operation for "main-types" with HTTPS protocol
    Then User should not get 200 status code
