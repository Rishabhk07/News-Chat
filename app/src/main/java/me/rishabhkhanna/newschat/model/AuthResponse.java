
package me.rishabhkhanna.newschat.model;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AuthResponse {

    @SerializedName("success")
    private Boolean mSuccess;
    @SerializedName("user")
    private User mUser;

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

}
