package com.example.rishabhkhanna.peopleword.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rishabhkhanna on 28/07/17.
 */
public class UserTable {
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("rating")
    private Long mRating;
    @SerializedName(value = "automotiveId",alternate = {"userbriefsId",
            "userautomotiveId","userbusinessId","usercricketId","usereducationId","userenvironmentId",
            "usergood_governancesId","userindiaId","userlife_styleId",
            "usersportsId","usertop_newsId","usertvId","userworldId"})
    private Long mTableId;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("userId")
    private Long mUserId;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getRating() {
        return mRating;
    }

    public void setRating(Long rating) {
        mRating = rating;
    }

    public Long getTopNewId() {
        return mTableId;
    }

    public void setTopNewId(Long topNewId) {
        mTableId = topNewId;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }
}
