## **API Behavior and Issues**

### **1. Inconsistent Responses for Non-Existent and Edge Case Usernames**
**Endpoint:** `GET https://jsonplaceholder.typicode.com/users?username={username}`

#### **What Should Happen:**
- When requesting a non-existent username, the API should return a `404 Not Found` status, indicating the user doesn't exist.
- If the username is empty, the API should return a `400 Bad Request` because the username is invalid.
- For usernames that are too long (e.g., over the allowed character limit), the API should respond with a `400 Bad Request`.
- If special characters are used in usernames (e.g., `!@#$%^&*()_+|`), the API should either return a `400 Bad Request` (if special characters aren’t allowed) or handle them properly if they are permitted.

#### **What Actually Happens:**
- For non-existent usernames, the API still responds with a `200 OK` status and an empty list (`[]`), instead of returning a `404 Not Found`.
- An empty username also gets a `200 OK` status with an empty list, when it should return a `400 Bad Request`.
- Overly long usernames also get a `200 OK` response with an empty list, rather than a `400 Bad Request`.
- Even usernames with special characters return a `200 OK` status and an empty list, even if they aren't valid inputs.

#### **Why This is a Problem:**
- This can be confusing because developers expect standard HTTP status codes like `404` for not found or `400` for invalid requests. Instead, a `200 OK` response is misleading because it suggests the request was successful, when it wasn’t.

#### **What Needs to Happen:**
- The API should return a `404 Not Found` for non-existent usernames and a `400 Bad Request` for empty or invalid usernames.
- It should also handle overly long usernames and special characters properly, responding with a `400 Bad Request` if necessary.

---

### **2. User and Post Data Inconsistencies**
**Example:**  
- If you query `GET /users?username=Delphine`, the API returns a user with `id=4`. However, if you then query `GET /posts?userId=4`, it returns posts associated with that user, without verifying if the user actually exists.

#### **Why This is a Problem:**
- The API doesn’t enforce foreign key constraints between users and posts, which means it could return posts for a non-existent user, leading to invalid relationships in the data.

#### **What Needs to Happen:**
- The API should ensure that there is valid data linking users and posts, ensuring that posts are only returned for valid users.

---

### **3. No Proper Error Handling for Invalid Inputs**
**Example:**  
- When querying with an invalid username (e.g., `GET /users?username=NonExistingUser`), the API returns an empty list (`[]`) with a `200 OK` status. Ideally, the API should return a `404 Not Found`.

#### **Why This is a Problem:**
- Developers might mistakenly assume that the username exists because the API returns a `200 OK`. A `404 Not Found` would be more appropriate to clearly indicate no matching data.

#### **What Needs to Happen:**
- The API should return a `404 Not Found` when no matching data is found, rather than an empty list with a `200 OK`.

---

### **4. Allows Creating Fake Data (But Doesn't Persist It)**
**Example:**  
- If you send a `POST` request to `/posts`, the API returns a successful response with an `id`, indicating the post was created. However, if you immediately try to `GET` the same post, it doesn’t exist because the data wasn’t actually stored.

#### **Why This is a Problem:**
- This can confuse users and developers because the data seems to be created but disappears immediately, making the API unreliable for testing data creation.

#### **What Needs to Happen:**
- The API should persist data if it’s created, or not allow data creation at all. If the data is created, it should be retrievable via `GET` requests.

---

### **5. No Email Format Validation in Comments**
**Example:**  
- The `/comments` endpoint contains user emails, but the API doesn’t check if the email addresses are in a valid format.

#### **Why This is a Problem:**
- Storing invalid email addresses can lead to data integrity issues and cause problems for applications that rely on the email format being correct.

#### **What Needs to Happen:**
- The API should validate email addresses before storing them in the `/comments` endpoint to ensure they are properly formatted.

---

### **6. Non-Existent Post Still Returns 200 OK**
**Example:**  
- When querying `GET /posts/99999/comments`, the API responds with a `200 OK` and an empty list (`[]`), rather than returning a `404 Not Found` for a non-existent post.

#### **Why This is a Problem:**
- This can be misleading, as a `200 OK` suggests the request was successful, when in reality, the post doesn’t exist.

#### **What Needs to Happen:**
- The API should return a `404 Not Found` when trying to access a post or comment that doesn’t exist.

---

## **Impact on API Tests**

When running API tests, especially against a real API, it’s important to account for these edge cases:

- **Non-existent data:** Tests should handle scenarios where no data is found and expect a `404 Not Found` instead of an empty list.
- **Invalid inputs:** Tests should anticipate `400 Bad Request` for invalid usernames, emails, or other parameters.
- **Data persistence:** Ensure that created data is stored correctly if expected by the test.
- **Proper status codes:** Make sure that the correct status codes (`400`, `404`, etc.) are returned based on the type of error or missing data.


