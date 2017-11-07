package me.rishabhkhanna.newschat.Network;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import me.rishabhkhanna.newschat.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 07/08/17.
 */

public class ChatAPI {
    public static ChatAPI chatAPI = null;
    public Retrofit retrofit;

    public ChatAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.server_url)
                .addConverterFactory(
                        GsonConverterFactory.create()
                ).build();
    }

    public static ChatAPI getChatInstance(){
        if(chatAPI == null){
            chatAPI = new ChatAPI();
        }
        return chatAPI;
    }
    public static Socket getSocket(){
        try {
            return IO.socket(Constants.server_url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
