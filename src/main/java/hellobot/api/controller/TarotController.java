package hellobot.api.controller;

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
