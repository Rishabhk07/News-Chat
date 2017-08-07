
package com.example.rishabhkhanna.peopleword.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("is_silhouette")
    private Boolean mIsSilhouette;
    @SerializedName("url")
    private String mUrl;

    public Boolean getIsSilhouette() {
        return mIsSilhouette;
    }

    public void setIsSilhouette(Boolean isSilhouette) {
        mIsSilhouette = isSilhouette;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
