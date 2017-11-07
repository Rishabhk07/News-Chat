
package me.rishabhkhanna.peopleword.model;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PhotoStory extends RealmObject{

    @SerializedName("ag")
    private String mAg;
    @SerializedName("au")
    private String mAu;
    @SerializedName("cap")
    private String mCap;
    @SerializedName("dl")
    private String mDl;
    @SerializedName("dm")
    private String mDm;
    @SerializedName("hl")
    private String mHl;
    @SerializedName("id")
    private String mId;
    @SerializedName("imageid")
    private String mImageid;
    @SerializedName("sec")
    private String mSec;
    @SerializedName("su")
    private String mSu;
    @SerializedName("tn")
    private String mTn;
    @SerializedName("upd")
    private String mUpd;
    @SerializedName("wu")
    private String mWu;

    public String getAg() {
        return mAg;
    }

    public void setAg(String ag) {
        mAg = ag;
    }

    public String getAu() {
        return mAu;
    }

    public void setAu(String au) {
        mAu = au;
    }

    public String getCap() {
        return mCap;
    }

    public void setCap(String cap) {
        mCap = cap;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImageid() {
        return mImageid;
    }

    public void setImageid(String imageid) {
        mImageid = imageid;
    }

    public String getSec() {
        return mSec;
    }

    public void setSec(String sec) {
        mSec = sec;
    }

    public String getSu() {
        return mSu;
    }

    public void setSu(String su) {
        mSu = su;
    }

    public String getTn() {
        return mTn;
    }

    public void setTn(String tn) {
        mTn = tn;
    }

    public String getUpd() {
        return mUpd;
    }

    public void setUpd(String upd) {
        mUpd = upd;
    }

    public String getWu() {
        return mWu;
    }

    public void setWu(String wu) {
        mWu = wu;
    }

}
