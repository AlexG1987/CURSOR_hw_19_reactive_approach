package com.cursor.reactivetwitterintegrationclient.controller;

import com.cursor.reactivetwitterintegrationclient.service.impl.TwitterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class TwitterController {

    private final TwitterServiceImpl twitterServiceImpl;

    @GetMapping("/")
    public Flux<String> getTwitterTimeLine() {
        Flux<String> tweets = Flux.just(twitterServiceImpl.showHomeTimeline(TwitterServiceImpl.getTwitterInstance()));
        tweets.subscribe();
        return tweets;
    }

}
