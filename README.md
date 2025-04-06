# JSONPlaceholder API Test Automation

## Overview

This repository contains automated tests for the **JSONPlaceholder API**. The tests are designed to validate various aspects of the API, ensuring that it behaves as expected under different conditions. The tests are written in **Java** and use **RestAssured** for API interaction and **JUnit 5** for testing.

### Key Features:
- **Parameterized Tests** for validating different username scenarios (valid, invalid, and edge cases).
- **Dynamic Data Loading** from JSON files.
- **Logging** of test results using **SLF4J**.
- **JUnit 5** for running the tests and asserting API responses.
- **Data-Driven Approach**: The test cases use parameters loaded from a JSON file, which keeps the test data external to the codebase.

## Setup

### Prerequisites:
- **Java 17** or above
- **Maven** for dependency management
- **SLF4J** and **Logback** for logging
- **RestAssured** for API testing
- **JUnit 5** for test execution

### Installation:
1. Clone the repository:
    ```bash
    git clone https://github.com/rsrikanth11/jsonplaceholder-api-task.git
    ```
2. Navigate to the project directory:
    ```bash
    cd jsonplaceholder-api-task
    ```
3. Install the required dependencies:
    ```bash
    mvn clean install
    ```

## Test Scenarios
1. Search User by Valid Usernames
This scenario tests the ability to search for users by valid usernames.

Input: A list of valid usernames such as Delphine, Bret, and Samantha.

Expected Outcome: For each valid username, the API returns a 200 OK status and the user information should be found in the response.

2. Search User by Invalid Usernames
This scenario tests the ability to search for non-existent usernames.

Input: A list of invalid usernames such as nonExistentUser1 and userNotFound123.

Expected Outcome: The API should return a 200 OK status, but the list of users should be empty (i.e., no users found).

3. Search User by Edge Case Usernames
This scenario tests the ability of the API to handle edge case usernames like empty strings, long strings, and special characters.

Input: Edge case usernames such as an empty string (""), a very long username (more than 255 characters), and a string with special characters (!@#$%^&*()_+|).

Expected Outcome: The API should return a 200 OK status for all edge cases and handle each scenario appropriately.

4. Search Posts by Non-Existent User ID
This scenario tests the ability to search for posts by a non-existent user ID.

Input: A user ID that does not exist in the system, such as 9999.

Expected Outcome: The API should return a 200 OK status, but no posts should be found for the non-existent user.

5. Search Posts by User ID
This scenario tests the ability to search for posts by a valid user ID.

Input: A valid user ID (e.g., 1).

Expected Outcome: The API should return a 200 OK status and a list of posts. For each post, comments are fetched and validated.

6. Validate Comments for Each Post
This scenario is a part of the previous test, where for each post returned by a valid user, the comments are fetched and validated.

Input: A list of posts for a given user.

Expected Outcome: The API should return a 200 OK status, and for each comment, it checks if the email format is valid (using regex).

## Test Execution

### Running Tests:
1. To run the tests via **Maven**, use the following command:
    ```bash
    mvn test
    ```

2. The tests will execute, and the results will be displayed in the console. Each test case will log its status using **SLF4J**.

### Example Output:
```plaintext
[INFO] Running com.api.tests.ApiTests
22:48:15.568 [main] INFO com.api.tests.ApiTests -- Test passed for invalid username: nonExistentUser1
22:48:15.627 [main] INFO com.api.tests.ApiTests -- Test passed for invalid username: userNotFound123
22:48:16.008 [main] INFO com.api.tests.ApiTests -- Comments validated for post ID: 1
22:48:16.326 [main] INFO com.api.tests.ApiTests -- Comments validated for post ID: 2
22:48:18.598 [main] INFO com.api.tests.ApiTests -- Comments validated for post ID: 10
22:48:18.652 [main] INFO com.api.tests.ApiTests -- Test passed for valid username: Delphine
22:48:19.199 [main] INFO com.api.tests.ApiTests -- Test passed for edge case username: !@#$%^&*()_+|
[INFO] BUILD SUCCESS
```

### Key Files:
- **ApiUtils.java**: Contains utility methods for making API requests.
- **Constants.java**: Holds constant values used throughout the project, like HTTP status codes and the base URL.
- **Config.java**: Loads configuration values, such as the base URL.
- **Messages.java**: Retrieves and formats localized messages for logging and validation.
- **ApiTests.java**: Contains the test cases for validating usernames, posts, and comments.
- **usernames.json**: Contains the test data for usernames (valid, invalid, and edge cases).

## Allure Report

This project is integrated with **Allure Report** to generate detailed test execution reports. Allure provides a comprehensive and visually appealing way to review test results, including pass/fail rates, durations, and additional metadata.

### Generating Allure Report

1. **Run the Tests**:
   First, execute the tests and store the results in the `allure-results` directory by running:

   ```bash
   mvn clean test -Dallure.results.directory=target/allure-results
This command will run all the test scenarios and generate the necessary result files under the target/allure-results directory.

Generate and Serve the Allure Report: After the tests have been executed, generate the Allure report and serve it using the following Maven command:

```bash
mvn allure:serve
```

This will automatically generate the report and open it in your default web browser. The Allure Report will show detailed information about each test scenario, including:

Passed, Failed, and Skipped Tests

Test Duration

Test Logs and Screenshots (if configured)

It provides an interactive and user-friendly interface for inspecting the test results.

## Additional Notes
The tests use JUnit 5 for test execution and RestAssured for API requests.

The logger is used to track the test execution and log the results for each scenario.

All API responses are validated for HTTP 200 OK status and the expected behavior based on the scenario.

## Conclusion

This project provides a solid foundation for testing the **JSONPlaceholder API** using parameterized tests, dynamic data, and proper logging. It is designed to be extendable, allowing to add more test cases and modify configurations easily.
