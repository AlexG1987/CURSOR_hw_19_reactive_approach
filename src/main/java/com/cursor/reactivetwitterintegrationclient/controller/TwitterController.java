package com.cursor.reactivetwitterintegrationclient.controller;

import com.cursor.reactivetwitterintegrationclient.service.TwitterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import twitter4j.Status;

@RestController
public class TwitterController {

    @GetMapping(path = "/feed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> feed() {
        ConnectableFlux<Status> flux = TwitterService.getTwitterStream();
        return flux.map(status -> status.getText());
    }

}
