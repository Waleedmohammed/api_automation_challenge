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

