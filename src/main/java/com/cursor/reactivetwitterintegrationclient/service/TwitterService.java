package com.cursor.reactivetwitterintegrationclient.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterService {

    static final String CONSUMER_KEY = "";
    static final String CONSUMER_SECRET = "";
    static final String ACCESS_TOKEN = "";
    static final String ACCESS_TOKEN_SECRET = "";

    private static ConnectableFlux<Status> twitterStream;

    public static synchronized ConnectableFlux getTwitterStream() {
        if (twitterStream == null) {
            initTwitterStream();
        }
        return twitterStream;
    }

    private static void initTwitterStream() {
        Flux<Status> stream = Flux.create(emitter -> {
            StatusListener listener = new StatusListener() {

                @Override
                public void onException(Exception e) {
                    emitter.error(e);
                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice arg) {
                }

                @Override
                public void onScrubGeo(long userId, long upToStatusId) {
                }

                @Override
                public void onStallWarning(StallWarning warning) {
                }

                @Override
                public void onStatus(Status status) {
                    emitter.next(status);
                }

                @Override
                public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                    System.out.println(numberOfLimitedStatuses);
                }
            };

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(CONSUMER_KEY)
                    .setOAuthConsumerSecret(CONSUMER_SECRET)
                    .setOAuthAccessToken(ACCESS_TOKEN)
                    .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

            TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
            twitterStream.addListener(listener);
            twitterStream.sample();

        });
        twitterStream = stream.publish();
        twitterStream.connect();
    }

}
