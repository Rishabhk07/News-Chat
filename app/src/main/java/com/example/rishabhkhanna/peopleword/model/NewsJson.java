
package com.example.rishabhkhanna.peopleword.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class NewsJson {

    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("dl")
    private String mDl;
    @SerializedName("dm")
    private String mDm;
    @SerializedName("hl")
    private String mHl;
    @SerializedName("id")
    private Long mId;
    @SerializedName("imageid")
    private String mImageid;
    @SerializedName("key")
    private String mKey;
    @SerializedName("msid")
    private String mMsid;
    @SerializedName("photoStory")
    private List<PhotoStory> mPhotoStory;
    @SerializedName("st")
    private String mSt;
    @SerializedName("story")
    private String mStory;
    @SerializedName("su")
    private String mSu;
    @SerializedName("syn")
    private String mSyn;
    @SerializedName("tn")
    private String mTn;
    @SerializedName("uid")
    private String mUid;
    @SerializedName("updatedAt")
    private String mUpdatedAt;

    @SerializedName("users")
    private List<User> mUser;

    public List<User> getmUser() {
        return mUser;
    }

    public void setmUser(List<User> mUser) {
        this.mUser = mUser;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDl() {
        return mDl;
    }

    public void setDl(String dl) {
        mDl = dl;
    }

    public String getDm() {
        return mDm;
    }

    public void setDm(String dm) {
        mDm = dm;
    }

    public String getHl() {
        return mHl;
    }

    public void setHl(String hl) {
        mHl = hl;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImageid() {
        return mImageid;
    }

    public void setImageid(String imageid) {
        mImageid = imageid;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getMsid() {
        return mMsid;
    }

    public void setMsid(String msid) {
        mMsid = msid;
    }

    public List<PhotoStory> getPhotoStory() {
        return mPhotoStory;
    }

    public void setPhotoStory(List<PhotoStory> photoStory) {
        mPhotoStory = photoStory;
    }

    public String getSt() {
        return mSt;
    }

    public void setSt(String st) {
        mSt = st;
    }

    public String getStory() {
        return mStory;
    }

    public void setStory(String story) {
        mStory = story;
    }

    public String getSu() {
        return mSu;
    }

    public void setSu(String su) {
        mSu = su;
    }

    public String getSyn() {
        return mSyn;
    }

    public void setSyn(String syn) {
        mSyn = syn;
    }

    public String getTn() {
        return mTn;
    }

    public void setTn(String tn) {
        mTn = tn;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
