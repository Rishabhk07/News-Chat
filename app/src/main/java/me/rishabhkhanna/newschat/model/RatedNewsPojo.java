
package me.rishabhkhanna.peopleword.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RatedNewsPojo {

    @SerializedName("automotives")
    private List<NewsJson> mAutomotives;

    @SerializedName("briefs")
    private List<NewsJson> mBriefs;

    @SerializedName("businesses")
    private List<NewsJson> mBusinesses;

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("crickets")
    private List<NewsJson> mCrickets;

    @SerializedName("education")
    private List<NewsJson> mEducation;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("entertainments")
    private List<NewsJson> mEntertainments;

    @SerializedName("environments")
    private List<NewsJson> mEnvironments;

    @SerializedName("events")
    private List<NewsJson> mEvents;

    @SerializedName("facebook_access_token")
    private String mFacebookAccessToken;

    @SerializedName("facebook_refresh_token")
    private String mFacebookRefreshToken;

    @SerializedName("facebook_user_id")
    private String mFacebookUserId;

    @SerializedName("fcm_token")
    private String mFcmToken;

    @SerializedName("good_governances")
    private List<NewsJson> mGoodGovernances;

    @SerializedName("google_access_token")
    private Object mGoogleAccessToken;

    @SerializedName("google_refresh_token")
    private Object mGoogleRefreshToken;

    @SerializedName("google_ser_id")
    private Object mGoogleSerId;

    @SerializedName("id")
    private Long mId;

    @SerializedName("india")
    private List<NewsJson> mIndia;

    @SerializedName("life_styles")
    private List<NewsJson> mLifeStyles;

    @SerializedName("name")
    private String mName;

//    @SerializedName("notification_for")
//    private NewsJson mNotificationFor;

    @SerializedName("sports")
    private List<NewsJson> mSports;

    @SerializedName("top_news")
    private List<NewsJson> mTopNews;

    @SerializedName("tvs")
    private List<NewsJson> mTvs;

    @SerializedName("updatedAt")
    private String mUpdatedAt;

//    @SerializedName("via")
//    private NewsJson mVia;

    @SerializedName("worlds")
    private List<NewsJson> mWorlds;




    public List<NewsJson> getAutomotives() {
        return mAutomotives;
    }

    public void setAutomotives(List<NewsJson> automotives) {
        mAutomotives = automotives;
    }

    public List<NewsJson> getBriefs() {
        return mBriefs;
    }

    public void setBriefs(List<NewsJson> briefs) {
        mBriefs = briefs;
    }

    public List<NewsJson> getBusinesses() {
        return mBusinesses;
    }

    public void setBusinesses(List<NewsJson> businesses) {
        mBusinesses = businesses;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public List<NewsJson> getCrickets() {
        return mCrickets;
    }

    public void setCrickets(List<NewsJson> crickets) {
        mCrickets = crickets;
    }

    public List<NewsJson> getEducation() {
        return mEducation;
    }

    public void setEducation(List<NewsJson> education) {
        mEducation = education;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<NewsJson> getEntertainments() {
        return mEntertainments;
    }

    public void setEntertainments(List<NewsJson> entertainments) {
        mEntertainments = entertainments;
    }

    public List<NewsJson> getEnvironments() {
        return mEnvironments;
    }

    public void setEnvironments(List<NewsJson> environments) {
        mEnvironments = environments;
    }

    public List<NewsJson> getEvents() {
        return mEvents;
    }

    public void setEvents(List<NewsJson> events) {
        mEvents = events;
    }

    public String getFacebookAccessToken() {
        return mFacebookAccessToken;
    }

    public void setFacebookAccessToken(String facebookAccessToken) {
        mFacebookAccessToken = facebookAccessToken;
    }

    public String getFacebookRefreshToken() {
        return mFacebookRefreshToken;
    }

    public void setFacebookRefreshToken(String facebookRefreshToken) {
        mFacebookRefreshToken = facebookRefreshToken;
    }

    public String getFacebookUserId() {
        return mFacebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        mFacebookUserId = facebookUserId;
    }

    public String getFcmToken() {
        return mFcmToken;
    }

    public void setFcmToken(String fcmToken) {
        mFcmToken = fcmToken;
    }

    public List<NewsJson> getGoodGovernances() {
        return mGoodGovernances;
    }

    public void setGoodGovernances(List<NewsJson> goodGovernances) {
        mGoodGovernances = goodGovernances;
    }

    public Object getGoogleAccessToken() {
        return mGoogleAccessToken;
    }

    public void setGoogleAccessToken(NewsJson googleAccessToken) {
        mGoogleAccessToken = googleAccessToken;
    }

    public Object getGoogleRefreshToken() {
        return mGoogleRefreshToken;
    }

    public void setGoogleRefreshToken(NewsJson googleRefreshToken) {
        mGoogleRefreshToken = googleRefreshToken;
    }

    public Object getGoogleSerId() {
        return mGoogleSerId;
    }

    public void setGoogleSerId(NewsJson googleSerId) {
        mGoogleSerId = googleSerId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<NewsJson> getIndia() {
        return mIndia;
    }

    public void setIndia(List<NewsJson> india) {
        mIndia = india;
    }

    public List<NewsJson> getLifeStyles() {
        return mLifeStyles;
    }

    public void setLifeStyles(List<NewsJson> lifeStyles) {
        mLifeStyles = lifeStyles;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<NewsJson> getSports() {
        return mSports;
    }

    public void setSports(List<NewsJson> sports) {
        mSports = sports;
    }

    public List<NewsJson> getTopNews() {
        return mTopNews;
    }

    public void setTopNews(List<NewsJson> topNews) {
        mTopNews = topNews;
    }


    public List<NewsJson> getTvs() {
        return mTvs;
    }

    public void setTvs(List<NewsJson> tvs) {
        mTvs = tvs;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }


    public List<NewsJson> getWorlds() {
        return mWorlds;
    }

    public void setWorlds(List<NewsJson> worlds) {
        mWorlds = worlds;
    }

}
