package com.api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Utility class for making API requests.
 */
public class ApiUtils {

    private static final String BASE_URL = Config.get("base.url");

    /**
     * Get user details by username.
     * 
     * @param username The username to search for.
     * @return The response containing user details.
     */
    public static Response getUserByUsername(String username) {
        return RestAssured.get(BASE_URL + "/users?username=" + username);
    }

    /**
     * Get posts by user ID.
     * 
     * @param userId The user ID to search for posts.
     * @return The response containing posts.
     */
    public static Response getPostsByUserId(int userId) {
        return RestAssured.get(BASE_URL + "/posts?userId=" + userId);
    }

    /**
     * Get comments by post ID.
     * 
     * @param postId The post ID to search for comments.
     * @return The response containing comments.
     */
    public static Response getCommentsByPostId(int postId) {
        return RestAssured.get(BASE_URL + "/comments?postId=" + postId);
    }
}
