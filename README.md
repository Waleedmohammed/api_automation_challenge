# Test Cases:

test scenarios and steps are available in feature files corresponding to each API.

* [Manufacturer_API_Service.feature](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/src/main/resources/feature/Manufacturer_API_Service.feature)
* [Model_API_Service.feature](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/src/main/resources/feature/Model_API_Service.feature)
* [Model_Registration_Year_API_Service.feature](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/src/main/resources/feature/Model_Registration_Year_API_Service.feature)
* [End_To_End_Tests.feature](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/src/main/resources/feature/End_To_End_Tests.feature)

Findings & Bugs Report: All findings & bugs details are mentioned in the [Test Summary Report.pdf](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/Test%20Summary%20Report.pdf)

# How to Execute:

Pre-Requisite: Kindly update BASE_URL & WA_KEY in [configuration.properties](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/src/main/resources/configuration.properties) file

1. To execute any of the feature files, open feature file, right click in the file and select 'Run As > Cucumber Feature'

2. To execute all the features, open [TestRunner.java](https://github.com/Waleedmohammed/api_automation_challenge/blob/master/src/test/java/runners/TestRunner.java), right click in the file and select 'Run As > JUnit Test'

3. To execute via command prompt, execute below command from project root directory(execution will take place using TestNG & Maven)

> `mvn test`

## Execution Reports:

- **_Cucumber Report:_** Report generate using cucumber will be available at: './api_automation_challenge/target/cucumber/cucumber-html-reports/overview-features.html'
