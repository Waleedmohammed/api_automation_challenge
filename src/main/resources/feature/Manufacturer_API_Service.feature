@Regression
Feature: Validate Car Manufacturer API Service
#  $$$$$$$ What is covered here as part of testing the manufacturer API $$$$$$$
#  1. Validate successful 200 response from GET method and
#     we are getting key value pairs in wkda object so that customer never see blank dropdown
#  2. Validate the wrapping json as per business requirement which includes page, pageSize, totalPageCount & Json object wkda
#  3. Validate whether wkda object is returning the unique list of car manufacturer names.
#  4. Validate not getting success response for any other HTTP method like POST for the developed GET service.
#  5. Validating that Endpoint should not accept any additional or invalid query parameters for the service.
#  6. Validate that Endpoint should not accept HTTPS protocol which is not part of requirement

  @Smoke
  Scenario: GET operation Success Status for Manufacturer Service and no blank wkda object
    Given User perform GET operation for "manufacturer"
    When User gets 200 status code
    Then User should not get blank wkda object

  @Smoke
  Scenario: Validate wrapping json for Manufacturer Service
    Given User perform GET operation on "manufacturer" for Schema Validation
    When User gets 200 status code
    Then User validates wrapping json for the service

  @Regression
  Scenario: Validate list of Unique Manufacturer
    Given User perform GET operation for "manufacturer"
    Then User validates unique values in response

  @NegativeScenario
  Scenario: POST operation Failure Status for Manufacturer Service
    Given User perform POST operation for "manufacturer" with request body
    Then User should not get 200 status code

  @NegativeScenario
  Scenario: Invalid query parameters for Manufacturer Service
    Given User perform GET operation for "manufacturer" with below parameters
      | Key  | Value |
      | test | test  |
      | test | test  |
      | test | test  |
    Then User should not get 200 status code

  @NegativeScenario
  Scenario: Protocol changed from HTTP to HTTPS for Manufacturer Service as per spec
    Given User perform GET operation for "manufacturer" with HTTPS protocol
    Then User should not get 200 status code