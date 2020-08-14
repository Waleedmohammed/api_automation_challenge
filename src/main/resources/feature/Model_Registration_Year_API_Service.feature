@Regression
Feature: Validate Car Model Registration Year API Service
#  1. Validate successful 200 response from GET method and
#     we are getting key value pairs in wkda object so that customer never see blank dropdown
#  2. Validate the wrapping json as per business requirement which includes page, pageSize, totalPageCount & Json object wkda
#  3. Validate whether wkda object is returning the unique list of car Model Registration Year.
#  4. Validate not getting success response for any other HTTP method like POST for the developed GET service.
#  5. Validating that Endpoint should not accept any additional or invalid query parameters for the service.
#  6. Validate that Endpoint should not accept HTTPS protocol which is not part of requirement

  @Smoke
  Scenario Outline: GET operation Success Status for Built Dates Service and no blank wkda object
    Given User perform GET operation for "built-dates" with "<ManufacturerCode>,<MainType>"
    When User gets 200 status code
    Then User should not get blank wkda object

    Examples:
      | ManufacturerCode | MainType   |
      | 040              | Alfa Brera |
      | 365              | Daily      |
      | 960              | FLORIDA    |
      | 520              | TGE        |
      | 487              | LC-Serie   |

  @Smoke
  Scenario: Validate wrapping json for Built Dates Service
    Given User perform GET operation on "built-dates" for Schema Validation
    When User gets 200 status code
    Then User validates wrapping json for the service

  @Regression
  Scenario: Validate list of Unique Registration Year for all Car Models of Manufacturer
    Given User perform GET operation for "manufacturer"
    Then User see unique registration year of model types for different manufacturer

  @NegativeScenario
  Scenario: POST operation Failure Status for Manufacturer Service
    Given User perform POST operation for "built-dates" with request body
    Then User should not get 200 status code

  @NegativeScenario
  Scenario: Adding Valid parameters followed by Invalid query parameters for Built Dates Service
    Given User perform GET operation for "built-dates" with below parameters
      | Key          | Value |
      | manufacturer | 190   |
      | main-type    | LN    |
      | test         | test  |
    Then User should not get 200 status code

  @NegativeScenario
  Scenario: Protocol changed from HTTP to HTTPS for Built Dates Service as per spec
    Given User perform GET operation for "built-dates" with HTTPS protocol
    Then User should not get 200 status code
