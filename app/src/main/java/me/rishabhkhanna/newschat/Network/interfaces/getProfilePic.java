package me.rishabhkhanna.newschat.Network.interfaces;

import me.rishabhkhanna.newschat.model.FBProfilePicture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rishabhkhanna on 08/08/17.
 */

public interface getProfilePic {
    @GET("{user_id}/picture")
    Call<FBProfilePicture> getProfilePhoto(
            @Path("user_id") String userId,
            @Query("picture") String picture,
            @Query("redirect") Boolean redirect
    );
}
