
package com.example.rishabhkhanna.peopleword.model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class User {

    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("email")
    private String mEmail;
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


    @SerializedName(value = "userbriefs", alternate = {"userautomotive","userbusiness","usercricket","usereducation","userenvironment",
            "usergood_governances","userindia","userlife_style","usersports","usertop_news","usertv","userworld"})
    private UserTable mUserTable;

//    @SerializedName("userbriefs")
//    private ArrayList<UserBriefs> mUserBriefs;
//
//    @SerializedName("userautomotive")
//    private ArrayList<UserAutomotive> mUserAutomotive;
//
//    @SerializedName("userbusiness")
//    private ArrayList<UserBusiness> mUserBusiness;
//
//    @SerializedName("usercricket")
//    private ArrayList<UserCricket> mUserCricket;
//
//    @SerializedName("usereducation")
//    private ArrayList<UserEducation> mUserEducation;
//
//    @SerializedName("userenvironment")
//    private ArrayList<UserEnvironment> mUserEnvi;
//
//    @SerializedName("usergood_governances")
//    private ArrayList<UserGoodgov> mUserGoodgov;
//
//    @SerializedName("userindia")
//    private ArrayList<UserIndia> mUserIndia;
//    @SerializedName("userlife_style")
//    private ArrayList<UserLifestyle> mUserLifestyle;
//    @SerializedName("usersports")
//    private ArrayList<UserSports> mUserSports;
//    @SerializedName("usertop_news")
//    private ArrayList<UserTop> mUserTop;
//    @SerializedName("usertv")
//    private ArrayList<UserTv> mUserTv;
// @SerializedName("userworld")
//    private ArrayList<UserWorld> mUserWorld;


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
//
    public UserTable getmUserTable() {
        return mUserTable;
    }

    public void setmUserTable(UserTable mUserTable) {
        this.mUserTable = mUserTable;
    }


}
