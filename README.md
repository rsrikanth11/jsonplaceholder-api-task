### **Overview**
This project contains a suite of API tests designed to validate the behavior of the **JSONPlaceholder API**. The tests are built using **RestAssured**, **JUnit 5**, and **SLF4J** for logging.

## API Test Suite

The API test suite includes tests to verify:
- Searching users by username
- Fetching user posts and validating emails in comments
- Handling negative test cases for non-existing users and posts
- Edge test cases for empty and special characters in usernames

## Dependencies

This project uses the following libraries:

- **RestAssured** for API testing
- **JUnit 5** for writing and running tests
- **SLF4J** for logging
- **Apache Commons Validator** for email validation

### **Test Setup**
#### Prerequisites:
- **Java 11+** installed on your local machine.
- **Maven** for building the project.
- **JUnit 5** for running tests.
- **RestAssured** for API testing.
- **CircleCI** setup for continuous integration.

#### Steps to Set Up Locally:
1. Clone the repository:
   ```bash
   git clone https://github.com/rsrikanth11/jsonplaceholder-tests.git
   cd jsonplaceholder-tests
   ```

2. Install dependencies (assuming Maven is configured for the project):
   ```bash
   mvn clean install
   ```

3. Run tests locally:
   ```bash
   mvn test
   ```

   This will run all the tests in the project using JUnit.

### **Test Cases**
The test cases validate different workflows for the JSONPlaceholder API. They include:

Test Cases
1. testSearchUserByVariousUsernames
This test verifies that searching for a user by username returns the correct user. It checks for usernames such as "Delphine", "Bret", and "Samantha".

2. testSearchPostsByUser
This test verifies that fetching posts by a user ID returns valid posts. It also validates that emails in comments are in the correct format.

3. testSearchUserByInvalidUsername
This test checks that searching for a non-existent username returns no results (HTTP status 404).

4. testSearchUserByEmptyUsername
This test ensures that searching for a user by an empty string returns no results.

5. testSearchUserByUsernameWithSpecialChars
This test checks that searching for a username containing special characters returns no results.

6. testSearchPostsByNonExistentUser
This test ensures that searching for posts by a non-existent user ID returns no posts.


### **Test Execution**
#### Running the Tests:
You can run the tests using Maven or directly from your IDE (e.g., IntelliJ IDEA):
- **From terminal (Maven)**:
  ```bash
  mvn test
  ```

- **From IDE (e.g., IntelliJ IDEA)**:
  - Right-click on the test class (`ApiTests.java`) and select "Run" to execute the tests.

#### Continuous Integration with CircleCI:
Tests are also integrated into CircleCI for continuous integration. The CircleCI pipeline is configured to run all tests automatically on every commit or pull request.

- **CircleCI Job**: The job is configured to run the test suite using Maven.
- You can find the CircleCI configuration in `.circleci/config.yml`.

### **Test Results**
After running the tests, the results will be displayed in the terminal. A successful run will show a message like:
```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
```

If any test fails, the detailed error messages will be shown, indicating which test failed and why.

### Generating HTML Test Reports

To generate HTML reports for the tests, follow these steps:

1. **Run Tests**:
   After making any changes or ensuring everything is set up, execute the tests using the following command:

   ```bash
   mvn clean test
   ```

2. **Generate HTML Report**:
   The `maven-surefire-report-plugin` will automatically generate the test reports. To generate the HTML report, run the following command:

   ```bash
   mvn surefire-report:report
   ```

3. **Locate the Report**:
   Once the above command runs successfully, the HTML report will be available at the following location:

   ```
   target/site/index.html
   ```

   You can open this file in a browser to view the detailed test results.