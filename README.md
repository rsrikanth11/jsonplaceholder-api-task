# API Test Automation for JSONPlaceholder

This repository contains an API test automation framework for the JSONPlaceholder API. The tests cover various scenarios such as retrieving user information, posts, comments, and validating email formats in the comments.

## API Base URL

The base URL for all API requests is: https://jsonplaceholder.typicode.com


## Prerequisites

1. **JDK 11+** (for Java development).
2. **Maven** (for managing dependencies).
3. **RestAssured** (for API testing).
4. **JUnit 5** (for test execution).
5. **IDE** (like IntelliJ IDEA or Eclipse) for running tests.

## Setup Instructions

1. **Clone the repository**:
    ```bash
    git clone https://github.com/rsrikanth11/jsonplaceholder-tests.git
    cd jsonplaceholder-tests
    ```

2. **Build the project**:
    Using Maven, run:
    ```bash
    mvn clean install
    ```

3. **Run the tests**:
    You can run the tests using Maven or directly through your IDE.

    To run using Maven:
    ```bash
    mvn test
    ```

4. **View test results**:
    After the tests complete, view the results directly in the terminal or in the generated `target` folder.

