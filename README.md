### **Overview**
This project provides an API test automation framework for validating the functionality of the JSONPlaceholder API (https://jsonplaceholder.typicode.com/). The framework is built using **Java** with **RestAssured** and **JUnit 5**. It covers various test scenarios, including positive and negative test cases for user and post management.

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
   git clone https://github.com/your-repository/jsonplaceholder-tests.git
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

1. **Search User by Username** (`testSearchUserByUsername`)
   - Validates that the user "Delphine" can be found by username.

2. **Search Posts by User** (`testSearchPostsByUser`)
   - Validates that posts are returned for the user "Delphine".

3. **Validate Email Format in Comments** (`testValidateEmailFormatInComments`)
   - Ensures that emails in comments for posts made by the user "Delphine" follow a valid email format.

4. **Check No Posts for User** (`testNoPostsForUser`)
   - Verifies that there are no posts for a non-existent user.

5. **Comment with Invalid Email Format** (`testCommentWithInvalidEmail`)
   - Validates that all comments for a specific post have valid email addresses.

6. **Search User by Invalid Username** (`testSearchUserByInvalidUsername`)
   - Checks the response when searching for a user with a non-existent username.

7. **Get Comments for Non-Existent Post** (`testCommentsForNonExistentPost`)
   - Ensures that no comments are returned for a non-existent post.

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
After running the tests, the results will be displayed in the terminal (if running locally) or in the CircleCI dashboard. A successful run will show a message like:
```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
```

If any test fails, the detailed error messages will be shown, indicating which test failed and why.

### **Additional Notes**
- **Test Coverage**: This test suite provides coverage for key API endpoints for the user "Delphine" (user with ID 4), covering both positive and negative scenarios.

