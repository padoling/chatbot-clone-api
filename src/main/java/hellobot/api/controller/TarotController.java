package hellobot.api.controller;

import hellobot.api.service.TarotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tarot")
public class TarotController {

    private final TarotService tarotService;

    @PostMapping
    public ResponseEntity postTarot() {
        //TODO
        return null;
    }

    @GetMapping
    public ResponseEntity getTarotList() {
        //TODO
        return null;
    }
}
