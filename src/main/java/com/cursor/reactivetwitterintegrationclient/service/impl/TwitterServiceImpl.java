package com.cursor.reactivetwitterintegrationclient.service.impl;

import com.cursor.reactivetwitterintegrationclient.service.TwitterService;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

@Service
public class TwitterServiceImpl implements TwitterService {

    static final String CONSUMER_KEY = "you-key";
    static final String CONSUMER_SECRET = "secret";
    static final String ACCESS_TOKEN = "token";
    static final String ACCESS_TOKEN_SECRET = "token-secret";

    public static Twitter getTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }

    public String showHomeTimeline(Twitter twitter) {
        List<Status> statuses = null;
        String message = "";
        try {
            statuses = twitter.getHomeTimeline();
            message = "Showing home timeline." + "\n";
            for (Status status : statuses) {
                message.concat(status.getUser().getName() + ":" + status.getText());
                String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/"
                        + status.getId();
                message.concat("Above tweet URL : " + url);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return message;
    }

}
