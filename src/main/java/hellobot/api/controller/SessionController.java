package hellobot.api.controller;

import hellobot.api.dto.SessionDto;
import hellobot.api.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity startSession(@RequestBody SessionDto sessionDto) {
        return new ResponseEntity<>(sessionService.saveSession(sessionDto), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updateSession() {
        //TODO
        return null;
    }

    @DeleteMapping
    public ResponseEntity finishSession() {
        //TODO
        return null;
    }
}
