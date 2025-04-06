package com.api.tests;

import com.api.utils.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import io.restassured.response.Response;
import org.junit.jupiter.params.provider.Arguments;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTests {

    // Logger instance for logging test results
    private static final Logger logger = LoggerFactory.getLogger(ApiTests.class);

    // Test method for valid usernames
    @ParameterizedTest
    @MethodSource("loadValidUsernames")
    public void testSearchUserByValidUsernames(String username) {
        Response response = ApiUtils.getUserByUsername(username);
        assertEquals(200, response.statusCode(), "Expected HTTP Status 200");

        List<Map<String, Object>> users = response.jsonPath().getList("$");
        assertTrue(users.size() > 0, "User not found for username: " + username);

        // Log the successful test pass message
        logger.info("Test passed for valid username: " + username);
    }

    // Test method for negative usernames (non-existent users)
    @ParameterizedTest
    @MethodSource("loadInvalidUsernames")
    public void testSearchUserByInvalidUsernames(String username) {
        Response response = ApiUtils.getUserByUsername(username);

        // Expecting HTTP Status 200 OK, since the API responds this way even for non-existent users
        assertEquals(200, response.statusCode(), "Expected HTTP Status 200 for invalid user");

        List<Map<String, Object>> users = response.jsonPath().getList("$");
        // Assert that the list is empty, as the user doesn't exist
        assertTrue(users.isEmpty(), "Expected no users for non-existent username: " + username);

        // Log the successful test pass message
        logger.info("Test passed for invalid username: " + username);
    }

    // Test method for edge cases (empty, long, and special character usernames)
    @ParameterizedTest
    @MethodSource("loadEdgeUsernames")
    public void testSearchUserByEdgeUsernames(String username) {
        Response response = ApiUtils.getUserByUsername(username);

        if (username.isEmpty()) {
            // If the API accepts empty usernames, check for 200 OK status
            assertEquals(200, response.statusCode(), "Expected HTTP Status 200 for empty username");
        } else if (username.length() > 255) {
            // Check if the API tolerates long usernames (more than 255 characters)
            assertEquals(200, response.statusCode(), "Expected HTTP Status 200 for long username");
        } else {
            // For special characters or valid usernames, expect 200 OK if the API allows them
            assertEquals(200, response.statusCode(), "Expected HTTP Status 200 for edge case username");
        }

        // Log the successful test pass message
        logger.info("Test passed for edge case username: " + username);
    }

    // Loading valid usernames from the JSON file
    private static Stream<Arguments> loadValidUsernames() throws Exception {
        return loadUsernamesFromJson("valid_usernames");
    }

    // Loading invalid usernames from the JSON file
    private static Stream<Arguments> loadInvalidUsernames() throws Exception {
        return loadUsernamesFromJson("invalid_usernames");
    }

    // Loading edge usernames from the JSON file
    private static Stream<Arguments> loadEdgeUsernames() throws Exception {
        return loadUsernamesFromJson("edge_usernames");
    }

    // Helper method to load usernames from JSON
    private static Stream<Arguments> loadUsernamesFromJson(String key) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> data = mapper.readValue(Paths.get("src/test/resources/usernames.json").toFile(), Map.class);
        List<String> usernames = (List<String>) data.get(key);
        return usernames.stream().map(Arguments::of);
    }

    // Test for fetching posts by a non-existent user ID
    @Test
    public void testSearchPostsByNonExistentUser() {
        Integer nonExistentUserId = 9999; // Assuming this user ID doesn't exist
        Response response = ApiUtils.getPostsByUserId(nonExistentUserId);

        assertEquals(200, response.statusCode(), "Expected HTTP Status 200");

        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        assertTrue(posts.isEmpty(), "Posts found for non-existent user ID");

        logger.info("Test 'testSearchPostsByNonExistentUser' PASSED");
    }

    // Test to verify that searching for posts by a user returns valid posts.
    @Test
    public void testSearchPostsByUser() {
        Integer userId = 1; // You can parameterize this value or fetch dynamically
        Response response = ApiUtils.getPostsByUserId(userId);
        assertEquals(200, response.statusCode(), "Expected HTTP Status 200");

        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        assertTrue(posts.size() > 0, "No posts found for user ID: " + userId);

        // For each post, fetch comments and validate emails
        for (Map<String, Object> post : posts) {
            Integer postId = (Integer) post.get("id");
            validateCommentsForPost(postId);
        }
    }

    // Utility method to validate emails in comments for a specific post
    private void validateCommentsForPost(Integer postId) {
        Response response = ApiUtils.getCommentsByPostId(postId);
        assertEquals(200, response.statusCode(), "Expected HTTP Status 200");

        List<Map<String, Object>> comments = response.jsonPath().getList("$");
        assertTrue(comments.size() > 0, "No comments found for post ID: " + postId);

        for (Map<String, Object> comment : comments) {
            String email = (String) comment.get("email");
            assertTrue(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "Invalid email format: " + email);
        }

        logger.info("Comments validated for post ID: {}", postId);
    }
}
