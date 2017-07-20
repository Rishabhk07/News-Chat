
package com.example.rishabhkhanna.peopleword.model;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class User {

    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("facebook_access_token")
    private String mFacebookAccessToken;
    @SerializedName("facebook_refresh_token")
    private Object mFacebookRefreshToken;
    @SerializedName("facebook_user_id")
    private String mFacebookUserId;
    @SerializedName("google_access_token")
    private Object mGoogleAccessToken;
    @SerializedName("google_refresh_token")
    private Object mGoogleRefreshToken;
    @SerializedName("google_ser_id")
    private Object mGoogleSerId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("via")
    private Object mVia;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFacebookAccessToken() {
        return mFacebookAccessToken;
    }

    public void setFacebookAccessToken(String facebookAccessToken) {
        mFacebookAccessToken = facebookAccessToken;
    }

    public Object getFacebookRefreshToken() {
        return mFacebookRefreshToken;
    }

    public void setFacebookRefreshToken(Object facebookRefreshToken) {
        mFacebookRefreshToken = facebookRefreshToken;
    }

    public String getFacebookUserId() {
        return mFacebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        mFacebookUserId = facebookUserId;
    }

    public Object getGoogleAccessToken() {
        return mGoogleAccessToken;
    }

    public void setGoogleAccessToken(Object googleAccessToken) {
        mGoogleAccessToken = googleAccessToken;
    }

    public Object getGoogleRefreshToken() {
        return mGoogleRefreshToken;
    }

    public void setGoogleRefreshToken(Object googleRefreshToken) {
        mGoogleRefreshToken = googleRefreshToken;
    }

    public Object getGoogleSerId() {
        return mGoogleSerId;
    }

    public void setGoogleSerId(Object googleSerId) {
        mGoogleSerId = googleSerId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Object getVia() {
        return mVia;
    }

    public void setVia(Object via) {
        mVia = via;
    }

}
