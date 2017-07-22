package com.example.rishabhkhanna.peopleword.utils;

import com.example.rishabhkhanna.peopleword.model.Topic;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 14/07/17.
 */

public class UtilMethods {

    public static String getImageurl(String urlId){
        return "http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                urlId + "&width=600&height=500&resizemode=1";
    }

    public static ArrayList<Topic> getTopics(){
        ArrayList<Topic> selectedTopic = new ArrayList<>();
        selectedTopic.add(new Topic("Briefs","briefs"));
        selectedTopic.add(new Topic("Entertainment","entertainment"));
        selectedTopic.add(new Topic("Top News","top_news"));

        selectedTopic.add(new Topic("India","india"));
        selectedTopic.add(new Topic("Good Governance","good_gov"));
        selectedTopic.add(new Topic("TV","tv"));

        selectedTopic.add(new Topic("World","world"));

        selectedTopic.add(new Topic("Sports","sports"));

        selectedTopic.add(new Topic("Cricket","cricket"));
        selectedTopic.add(new Topic("Business","business"));
        selectedTopic.add(new Topic("Education","education"));

        selectedTopic.add(new Topic("Life Style","life_stye"));
        selectedTopic.add(new Topic("Automotive","automotive"));
        selectedTopic.add(new Topic("Environment","environment"));
        return selectedTopic;
    }
}
