package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class ApiTests {

    // Base URL for the API
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // 1. Test to search for the user by username "Delphine"
    @Test
    public void testSearchUserByUsername() {
        Response response = RestAssured.get(BASE_URL + "/users?username=Delphine");

        assertEquals(200, response.statusCode(), "Expected HTTP status 200");
        List<Map<String, Object>> users = response.jsonPath().getList("$");
        assertTrue(users.size() > 0, "User should be found");
        System.out.println("✅ testSearchUserByUsername PASSED");
    }

    // 2. Test to search posts by the user
    @Test
    public void testSearchPostsByUser() {
        Response response = RestAssured.get(BASE_URL + "/posts?userId=4"); // UserId for "Delphine" is 4

        assertEquals(200, response.statusCode(), "Expected HTTP status 200");
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        assertTrue(posts.size() > 0, "Posts should be found");
        System.out.println("✅ testSearchPostsByUser PASSED");
    }

    // 3. Test to validate email format in comments
    @Test
    public void testValidateEmailFormatInComments() {
        // Get posts by the user with id 4
        Response postsResponse = RestAssured.get(BASE_URL + "/posts?userId=4");
        assertEquals(200, postsResponse.statusCode(), "Expected HTTP status 200");

        List<Map<String, Object>> posts = postsResponse.jsonPath().getList("$");
        // Get comments for each post and validate email format
        posts.forEach(post -> {
            int postId = (int) post.get("id");
            Response commentsResponse = RestAssured.get(BASE_URL + "/comments?postId=" + postId);
            assertEquals(200, commentsResponse.statusCode(), "Expected HTTP status 200");

            List<Map<String, Object>> comments = commentsResponse.jsonPath().getList("$");
            comments.forEach(comment -> {
                String email = (String) comment.get("email");
                assertTrue(isValidEmail(email), "Invalid email format: " + email);
            });
        });

        System.out.println("✅ testValidateEmailFormatInComments PASSED");
    }

    // 4. Test to check if no posts exist for a user
    @Test
    public void testNoPostsForUser() {
        Response response = RestAssured.get(BASE_URL + "/posts?userId=9999"); // Non-existing user
        assertEquals(200, response.statusCode(), "Expected HTTP status 200");
        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        assertTrue(posts.isEmpty(), "There should be no posts for this user");
        System.out.println("✅ testNoPostsForUser PASSED");
    }

    // 5. Test to check comment with invalid email format
    @Test
    public void testCommentWithInvalidEmail() {
        Response commentsResponse = RestAssured.get(BASE_URL + "/comments?postId=1");
        assertEquals(200, commentsResponse.statusCode(), "Expected HTTP status 200");

        List<Map<String, Object>> comments = commentsResponse.jsonPath().getList("$");
        comments.forEach(comment -> {
            String email = (String) comment.get("email");
            if (!isValidEmail(email)) {
                fail("Invalid email format found: " + email);
            }
        });
        System.out.println("✅ testCommentWithInvalidEmail PASSED");
    }

    // 6. Test to search for a user with an invalid username (negative scenario)
    @Test
    public void testSearchUserByInvalidUsername() {
        Response response = RestAssured.get(BASE_URL + "/users?username=NonExistentUser");

        assertEquals(404, response.statusCode(), "Expected HTTP status 404 for non-existent user");
        assertTrue(response.body().asString().isEmpty(), "The response body should be empty for invalid username");
        System.out.println("✅ testSearchUserByInvalidUsername PASSED");
    }

    // 7. Test to get comments for a non-existent post (negative scenario)
    @Test
    public void testCommentsForNonExistentPost() {
        int invalidPostId = 99999; // Assuming this post ID doesn't exist

        Response response = RestAssured.get(BASE_URL + "/posts/" + invalidPostId + "/comments");

        assertEquals(404, response.statusCode(), "Expected HTTP status 404 for non-existent post ID");
        System.out.println("✅ testCommentsForNonExistentPost PASSED");
    }

    // Utility method to check if the email format is valid
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
