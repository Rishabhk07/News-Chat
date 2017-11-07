
package me.rishabhkhanna.newschat.model;


import com.google.gson.annotations.SerializedName;

public class FBProfilePicture {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

}
