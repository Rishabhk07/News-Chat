package me.rishabhkhanna.peopleword.model;

/**
 * Created by rishabhkhanna on 26/07/17.
 */

public class AuthDetails {
    String AuthToken;
    String userId;

    public AuthDetails(String authToken, String userId) {
        AuthToken = authToken;
        this.userId = userId;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
