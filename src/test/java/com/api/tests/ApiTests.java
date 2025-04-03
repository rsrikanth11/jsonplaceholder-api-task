package com.api.tests;

import com.api.utils.ApiUtils;
import com.api.utils.Constants;
import com.api.utils.Messages;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains API tests for verifying user-related operations using RestAssured.
 * It covers:
 * - Searching users by username
 * - Fetching user posts
 * - Validating email formats in comments
 * - Handling negative test cases for non-existing users/posts
 * 
 * API Under Test: https://jsonplaceholder.typicode.com
 */
public class ApiTests {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiTests.class);

    /**
     * Test to verify that searching for a user by username returns the correct user.
     * 
     * Steps:
     * - Send a GET request to retrieve user details by username "Delphine".
     * - Validate that the response status is 200.
     * - Ensure the user is found.
     * 
     * Expected Result:
     * - The response should contain the user with the specified username "Delphine".
     */
    @Test
    public void testSearchUserByDelphineUsername() {
        String username = "Delphine";
        Response response = ApiUtils.getUserByUsername(username);
        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));
        
        List<Map<String, Object>> users = response.jsonPath().getList("$");
        assertTrue(users.size() > 0, Messages.getMessage("user.not.found"));
        
        // Extract user ID dynamically
        Integer userId = (Integer) users.get(0).get("id");
        logger.info("Test 'testSearchUserByDelphineUsername' PASSED for username: {}", username);
        
        // Pass user ID to the next test
        testSearchPostsByUser(userId);
    }

    /**
     * Test to verify that searching for posts by a user returns valid posts.
     * 
     * Steps:
     * - Send a GET request to retrieve posts by user ID.
     * - Validate that the response status is 200.
     * - Ensure that posts are found.
     * 
     * Expected Result:
     * - The response should contain at least one post.
     */
    public void testSearchPostsByUser(Integer userId) {
        Response response = ApiUtils.getPostsByUserId(userId);
        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));
        
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        assertTrue(posts.size() > 0, Messages.getMessage("post.not.found"));
        
        logger.info("Test 'testSearchPostsByUser' PASSED for user ID: {}", userId);

        // For each post, fetch comments and validate emails
        for (Map<String, Object> post : posts) {
            Integer postId = (Integer) post.get("id");
            validateCommentsForPost(postId);
        }
    }

    /**
     * This is not a test method but a utility method to validate emails in comments for a specific post.
     * 
     * Steps:
     * - Send a GET request to fetch comments for the given post.
     * - Validate that the email addresses in comments are in the proper format.
     */
    private void validateCommentsForPost(Integer postId) {
        Response response = ApiUtils.getCommentsByPostId(postId);
        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));

        List<Map<String, Object>> comments = response.jsonPath().getList("$");
        assertTrue(comments.size() > 0, "No comments found for post ID: " + postId);

        for (Map<String, Object> comment : comments) {
            String email = (String) comment.get("email");
            assertTrue(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "Invalid email format: " + email);
        }

        logger.info("Comments validated for post ID: {}", postId);
    }

    /**
     * Test to verify that searching for a user by invalid username returns a 404.
     * 
     * Steps:
     * - Send a GET request to retrieve user details by a non-existent username.
     * - Validate that the response status is 404.
     * 
     * Expected Result:
     * - The response should return HTTP status 404 for a non-existent user.
     */
    @Test
    public void testSearchUserByInvalidUsername() {
        Response response = ApiUtils.getUserByUsername("NonExistentUser");

        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));

        List<Map<String, Object>> users = response.jsonPath().getList("$");
        assertTrue(users.isEmpty(), Messages.getMessage("user.not.found"));

        logger.info("Test 'testSearchUserByInvalidUsername' PASSED");
    }

    /**
     * Edge test case: Verify that searching for a user by an empty string as username returns no results.
     */
    @Test
    public void testSearchUserByEmptyUsername() {
        Response response = ApiUtils.getUserByUsername("");

        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));

        List<Map<String, Object>> users = response.jsonPath().getList("$");
        assertTrue(users.isEmpty(), "User found for empty username");

        logger.info("Test 'testSearchUserByEmptyUsername' PASSED");
    }

    /**
     * Edge test case: Verify that searching for a user by a username with special characters returns no results.
     */
    @Test
    public void testSearchUserByUsernameWithSpecialChars() {
        Response response = ApiUtils.getUserByUsername("Del#phine$");

        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));

        List<Map<String, Object>> users = response.jsonPath().getList("$");
        assertTrue(users.isEmpty(), "User found with special characters in username");

        logger.info("Test 'testSearchUserByUsernameWithSpecialChars' PASSED");
    }

    /**
     * Negative test case: Verify that fetching posts by a non-existent user ID returns no posts.
     */
    @Test
    public void testSearchPostsByNonExistentUser() {
        Integer nonExistentUserId = 9999; // Assuming this user ID doesn't exist
        Response response = ApiUtils.getPostsByUserId(nonExistentUserId);

        assertEquals(Constants.HTTP_OK, response.statusCode(), Messages.getMessage("expected.http.status", Constants.HTTP_OK));

        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        assertTrue(posts.isEmpty(), "Posts found for non-existent user ID");

        logger.info("Test 'testSearchPostsByNonExistentUser' PASSED");
    }
}
